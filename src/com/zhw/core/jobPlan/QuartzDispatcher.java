package com.zhw.core.jobPlan;


import static org.quartz.impl.matchers.GroupMatcher.groupEquals;

import java.beans.Beans;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

import com.zhw.core.log.Syslog;
import com.zhw.core.net.LocalhostUtil;
import com.zhw.core.util.MyPropertyUtil;
import com.zhw.core.util.MyStringUtil;
import com.zhw.web.util.BeanUtil;


/**
 * @功能描述: Quartz派遣器
 */
@Component
public class QuartzDispatcher {
  private static final Log logger = LogFactory.getLog(QuartzDispatcher.class);
	
  private JobPlanService jobPlanService = BeanUtil.getBean("jobPlanService");
//  @Autowired()
//  private JobPlanService jobPlanService;
  
//  @Autowired
//  public static StdSchedulerFactory schedulerFactory;
  public static final String CLASS_NAME = "className";  
  public static String QUARTZ_PARAMETER_KEY = "QUARTZ_PARAMETER_KEY";
  private Scheduler scheduler;
  public static int jobNum = 0;
  
  private QuartzDispatcher() {
  }

  private static QuartzDispatcher instance;

  public static QuartzDispatcher getInstance() {
	return instance;
  }

  public static void setInstance(QuartzDispatcher instance) {
  	QuartzDispatcher.instance = instance;
  }

  /**
   * 工厂方法，每次都只初始化一个实例。
   * 
   * @return
   */
  public synchronized static QuartzDispatcher instance() {
    if (instance == null) {
      try {
    	  Syslog.info("作业计划调度器启动开始");
    	  instance = new QuartzDispatcher();
    	  instance.initScheduler();
    	  Syslog.info("作业计划调度器启动成功完成");
      }
      catch (SchedulerException ex) {
    	  logger.error("作业计划调度器服务启动失败：", ex);
      }
    }
    return instance;
  }

  /**
   * 初始化Quartz，并启动。后面可使用启动后的对象进行作业调度或执行计划。
   * 
   * @throws org.quartz.SchedulerException
   */
  public void initScheduler() throws SchedulerException {
    StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();

    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("quartz.properties");
    if (is != null) {
      schedulerFactory.initialize(is);
      logger.info("Found quartz.properties file. Using it for Quartz config.");
    }else {
      schedulerFactory.initialize();
      logger.info("No quartz.properties file. Using in-memory job store.");
    }

//    schedulerFactory.initialize();

    scheduler = schedulerFactory.getScheduler();
    scheduler.start();
  }

  /**
   * 使用Quartz调度，按最新规则重新调度已调度作业。对已经存在的Schedule会重新定义<br>
   * 一个job对应一个唯一的trigger、calendar<br>
   * 作业的调度必须使用CronExpress<br>
   * 
   * @param jobPlan
   * @throws java.lang.Exception
   */
  public void scheduleJobPlan(JobPlan jobPlan) throws Exception {

    // 作业名称
    String jobName = jobPlan.getJobname();
    Syslog.info("制定作业调度计划开始: " + jobName);
    // 作业组名称
    String jobGroup = jobPlan.getJobgroup();
    Syslog.info("作业组：" + jobGroup);
    // 执行的作业类名称，此类需要重载 process 接口
    String className = jobPlan.getClassname();
    Syslog.info("clasName = " + className);
    // 作业执行参数，把启动参数加进去
    String jobParameter = jobPlan.getJobparameter();
    Properties prop = MyPropertyUtil.parseArgs(jobParameter);
    Syslog.info("jobParameter = " + jobParameter);
    // 把作业要启动的类名加到参数里
    prop.setProperty(CLASS_NAME, className);
    // 执行此作业的群组，加到参数里面
    String clusterName = jobPlan.getClustername();

    // 创建新的作业对象，将参数对象信息加入到启动对象参数里
    JobDetailImpl job = new JobDetailImpl();
    job.setKey(new JobKey(jobName));
    job.setName(jobName);
    job.setGroup(jobGroup);
    job.setJobClass(QuartzJob.class);
    job.getJobDataMap().put(QUARTZ_PARAMETER_KEY, prop.toString());

    // 开始根据表达式创建作业触发器
    CronTriggerImpl trigger = null;
    // 触发器名称，和jobname同样即可
    String triggerName = JobPlan.getTriggerName(jobName);
    Syslog.info("jobName = " + jobName);
    // 触发器组，和jobGroup同样即可
    String triggerGroup = JobPlan.getTriggerGroup(jobGroup);
    Syslog.info("jobGroup = " + jobGroup);
    // 获得克隆表达式，此表达式不能为空，否则推出执行
    String cronExpression = MyStringUtil.checkNull(jobPlan.getTriggerpolicy());
    Syslog.info("cronExpression = " + cronExpression);

    if (!cronExpression.equals(MyStringUtil.EMPTY)) {
    	trigger = new CronTriggerImpl();
    	trigger.setName(triggerName);
    	trigger.setGroup(triggerGroup);
    	trigger.setJobName(jobName);
    	trigger.setJobGroup(jobGroup);
    	trigger.setCronExpression(cronExpression);
    }else{
      return;
    }
    // 此作业执行的优先级
    trigger.setPriority(jobPlan.getTriggepriority().intValue());
    // 从scheduler中查找作业，对已经存在的Schedulejob会重新定义
    JobDetail dupeJ = scheduler.getJobDetail(job.getKey());
    if (dupeJ != null) {
      scheduler.rescheduleJob(trigger.getKey(),trigger);
    }else {
      scheduler.scheduleJob(job, trigger);
    }
    Syslog.info("make job : " + className);
    logger.info("计划制定完成:"+jobName+",className:"+className);
    jobNum++;
  }

  /**
   * @功能描述: 从数据库表中初始化所有作业调度计划。此过程在重新制定计划之前，会首先将当前计划的所有作业清除
   */
  public void scheduleJobPlans() throws Exception {
    try {
      try {
        clearJobs();
      }
      catch (Exception e) {
        logger.error("清理作业出错：", e);
      }

      List<JobPlan> values = null;
      try {
        JobPlanQuery jpq=new JobPlanQuery();
        jpq.setClustername("platform");
        jpq.setInvalid(0L);
        values = jobPlanService.findList(jpq);
        
      }catch (Exception exception) {
        logger.info("获取作业列表出错", exception);
        return;
      }

      String localIP = LocalhostUtil.getLocalHostIP();

      for (Object jobPlan : values) {

        JobPlan j = (JobPlan) jobPlan;
        boolean isLoad = false;
        if (j.getClustername().trim().equals("platform")) {
          isLoad = true;
        }else {
          isLoad = j.getClustername().equalsIgnoreCase(localIP) ? true : false;
        }
        if(j.getInvalid() == 0 && isLoad) {
          scheduleJobPlan((JobPlan) jobPlan);
        }
      }

    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }

  /**
   * 停止作业调度器
   * 
   * @throws org.quartz.SchedulerException
   */
  public void destroy() throws SchedulerException {
    scheduler.shutdown();
    instance = null;
  }

  /**
   * 停止作业
   * 
   * @param triggerName
   * @throws org.quartz.SchedulerException
   */
  public void cancel(TriggerKey triggerName) throws SchedulerException {
    scheduler.unscheduleJob(triggerName);
  }

  /**
   * 暂停作业
   * 
   * @param triggerName
   * @throws org.quartz.SchedulerException
   */
  public void pause(TriggerKey triggerName) throws SchedulerException {
    scheduler.pauseTrigger(triggerName);
  }

  /**
   * 恢复暂停的作业
   * 
   * @param triggerName
   * @throws org.quartz.SchedulerException
   */
  public void resume(TriggerKey triggerName) throws SchedulerException {
    scheduler.resumeTrigger(triggerName);
  }

  /**
   * 清楚当前quartz的所有作业。通过枚举的方式，将其全部清除掉，后面可对其进行重新定义。
   * 
   * @throws org.quartz.SchedulerException
   */
  public void clearJobs() throws SchedulerException {
	  for(String group: scheduler.getJobGroupNames()) {
		// enumerate each job in group
		GroupMatcher<JobKey> gm=groupEquals(group);
		Set<JobKey> jk = scheduler.getJobKeys(gm);
		for(JobKey jobKey : jk){
				scheduler.deleteJob(jobKey);
			}
		}
  }

  /**
   * 删除quartz某个具体的作业
   * 
   * @param jobPlan 要删除的作业
   * @throws org.quartz.SchedulerException
   */
  public void deleteJob(JobPlan jobPlan) throws SchedulerException {

	  List<String> jobGroups = scheduler.getJobGroupNames();

    // 遍历所有的作业组
    for (int i = 0; i < jobGroups.size(); i++) {
      String jobGroup = jobGroups.get(i);
      if (jobGroup.equalsIgnoreCase(jobPlan.getJobgroup())) {
    	  GroupMatcher<JobKey> gm=groupEquals(jobGroup);
    	  Set<JobKey> jbKeys=scheduler.getJobKeys(gm);
    	  Iterator<JobKey> it=jbKeys.iterator();
        while(it.hasNext()) {
        	JobKey jbkey=it.next();
          if (jbkey.toString().equalsIgnoreCase(jobPlan.getJobname())) {
            scheduler.deleteJob(jbkey);
            Syslog.info("删除作业：" + jbkey);
          }
        }
      }
    }
  }

  /**
   * 作业开始执行
   * 
   * @throws org.quartz.SchedulerException
   */
  public void startJobs() throws SchedulerException {
    if (scheduler == null) {
      return;
    }
    if (!scheduler.isStarted()) {
      scheduler.start();
    }
  }

  /**
   * 获得Quartz对象，对其进行处理
   * 
   * @return
   */
  public Scheduler getScheduler() {
    return scheduler;
  }

  /**
   * @功能描述: Quartz执行作业类。quartz在作业初始化完成后，定时启动作业类。Quartz启动的作业类
   *        实际是固定的，每次都是启动QuartzJob这个是下了Job接口的类。我们将我们所需运行的
   *        实际类名称启动参数的方式传递过来，然后再下面通过作业类的名称，在实例化我们实际的 作业类。
   */
  @PersistJobDataAfterExecution
  @DisallowConcurrentExecution
  public static class QuartzJob implements Job {

    public QuartzJob() {
    }

    /**
     * 实现接口JOB中的方法execute
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {

      String className = context.getJobDetail().getJobClass().getSimpleName();

      try {

        Syslog.info("--------------------------------作业开始执行........" + className);
        Syslog.info("--------------------------------开始读取参数........");
        
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String param = dataMap.get(QUARTZ_PARAMETER_KEY).toString();
        Properties prop = MyPropertyUtil.parseArgs(param.substring(1, param.length() - 1));
        className = MyStringUtil.checkNull(prop.getProperty(CLASS_NAME));
        String jobName = context.getJobDetail().getKey().toString();
        Long fireTime = context.getFireTime().getTime();
        fireTime = fireTime - fireTime%(60*1000);//设置秒秒数为0
        prop.setProperty("jobruntime", String.valueOf(fireTime));
        prop.setProperty("jobname", jobName);
        
        Syslog.info("jobName = " + jobName);
        //logger.info("QuartzJob : className = " + className);
        //logger.info("Param = " + prop.toString());

        if (className != null) {

          logger.info("开始调度作业 " + jobName);

          try {
            // 根据作业类的名称，实例化我们实际的类
            Object obj = Beans.instantiate(Thread.currentThread().getContextClassLoader(), className);
            // 判断此实例是否实现了JobProcessor接口，如果有，则开始调用，
            // 否则退出
            if (obj instanceof IJobProcessor) {
              IJobProcessor processor = (IJobProcessor) obj;
              processor.process(prop);
              logger.info("作业执行成功完成：" + jobName);
            }
          }
          catch (Exception e) {
            logger.error("未成功执行作业类：" + className+"\n"+e.toString());
            //logger.error("QuartzJob " + e.toString(), e);
          }
        }
        
        Syslog.info("--------------------------------作业执行完成........" + className);
      }
      catch (Exception ex) {
        logger.error("作业执行失败" + className + " :" + ex);
        throw new JobExecutionException(ex.getMessage());
      }
    }
  }
}
