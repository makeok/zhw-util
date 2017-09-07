package com.zhw.web.listener;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.zhw.core.jobPlan.QuartzDispatcher;

@Component("beanFinishedListener") 
public class BeanFinishedListener implements 
ApplicationListener<ContextRefreshedEvent> {//ContextRefreshedEvent为初始化完毕事件，spring还有很多事件可以利用 

	//	@Autowired 
	//	private IRoleDao roleDao; 


	/** 
	* 当一个ApplicationContext被初始化或刷新触发 
	*/ 
	@Override 
	public void onApplicationEvent(ContextRefreshedEvent event) { 
		//	roleDao.getUserList();//spring容器初始化完毕加载用户列表到内存 
		System.out.println(event.getApplicationContext().getDisplayName()+"容器初始化完毕================================================");
		if(event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")){
			//初始化事件
			System.out.println("初始化事件================================================");;
			try {

				//QuartzDispatcher.instance().scheduleJobPlans();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} 

} 
