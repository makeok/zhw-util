package com.zhw.core.jobPlan;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class JobPlan
{
	private String id;//id
	private String jobname;//作业名称
	private String jobgroup;//作业组
	private String classname;//作业类名
	private String jobparameter;//作业类参数
	private String triggerpolicy;//作业调度策略
	private Long triggepriority;//调度优先级
	private String dayexcluded;//作业不调度的日期列表
	private Long retrycount;//失败重试次数
	private Long invalid;//是否有效
	private Long treatasdeadtimeout;//处理时限
	private String treatasdeadtimeoutunit;//处理时限单位
	private String clustername;//执行作业群集名称
	private String insertUser;//创建者
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date insertDatetime;//创建时间
	private String updateUser;//修改者
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updateDatetime;//修改时间

	public JobPlan()
	{
	}
	public JobPlan(String id)
	{
		this.id=id;
	}
	public JobPlan(
		String id
,		String jobname
,		String jobgroup
,		String classname
,		String jobparameter
,		String triggerpolicy
,		Long triggepriority
,		String dayexcluded
,		Long retrycount
,		Long invalid
,		Long treatasdeadtimeout
,		String treatasdeadtimeoutunit
,		String clustername
,		String insertUser
,		Date insertDatetime
,		String updateUser
,		Date updateDatetime
	)
	{
			this.id=id;
			this.jobname=jobname;
			this.jobgroup=jobgroup;
			this.classname=classname;
			this.jobparameter=jobparameter;
			this.triggerpolicy=triggerpolicy;
			this.triggepriority=triggepriority;
			this.dayexcluded=dayexcluded;
			this.retrycount=retrycount;
			this.invalid=invalid;
			this.treatasdeadtimeout=treatasdeadtimeout;
			this.treatasdeadtimeoutunit=treatasdeadtimeoutunit;
			this.clustername=clustername;
			this.insertUser=insertUser;
			this.insertDatetime=insertDatetime;
			this.updateUser=updateUser;
			this.updateDatetime=updateDatetime;
	}
	/**
		设置id
	*/
	public void setId(String id)
	{
		this.id=id;
	}
	/**
		获取id
	*/
	public String  getId()
	{
		return this.id;
	}
	/**
		设置作业名称
	*/
	public void setJobname(String jobname)
	{
		this.jobname=jobname;
	}
	/**
		获取作业名称
	*/
	public String  getJobname()
	{
		return this.jobname;
	}
	/**
		设置作业组
	*/
	public void setJobgroup(String jobgroup)
	{
		this.jobgroup=jobgroup;
	}
	/**
		获取作业组
	*/
	public String  getJobgroup()
	{
		return this.jobgroup;
	}
	/**
		设置作业类名
	*/
	public void setClassname(String classname)
	{
		this.classname=classname;
	}
	/**
		获取作业类名
	*/
	public String  getClassname()
	{
		return this.classname;
	}
	/**
		设置作业类参数
	*/
	public void setJobparameter(String jobparameter)
	{
		this.jobparameter=jobparameter;
	}
	/**
		获取作业类参数
	*/
	public String  getJobparameter()
	{
		return this.jobparameter;
	}
	/**
		设置作业调度策略
	*/
	public void setTriggerpolicy(String triggerpolicy)
	{
		this.triggerpolicy=triggerpolicy;
	}
	/**
		获取作业调度策略
	*/
	public String  getTriggerpolicy()
	{
		return this.triggerpolicy;
	}
	/**
		设置调度优先级
	*/
	public void setTriggepriority(Long triggepriority)
	{
		this.triggepriority=triggepriority;
	}
	/**
		获取调度优先级
	*/
	public Long  getTriggepriority()
	{
		return this.triggepriority;
	}
	/**
		设置作业不调度的日期列表
	*/
	public void setDayexcluded(String dayexcluded)
	{
		this.dayexcluded=dayexcluded;
	}
	/**
		获取作业不调度的日期列表
	*/
	public String  getDayexcluded()
	{
		return this.dayexcluded;
	}
	/**
		设置失败重试次数
	*/
	public void setRetrycount(Long retrycount)
	{
		this.retrycount=retrycount;
	}
	/**
		获取失败重试次数
	*/
	public Long  getRetrycount()
	{
		return this.retrycount;
	}
	/**
		设置是否有效
	*/
	public void setInvalid(Long invalid)
	{
		this.invalid=invalid;
	}
	/**
		获取是否有效
	*/
	public Long  getInvalid()
	{
		return this.invalid;
	}
	/**
		设置处理时限
	*/
	public void setTreatasdeadtimeout(Long treatasdeadtimeout)
	{
		this.treatasdeadtimeout=treatasdeadtimeout;
	}
	/**
		获取处理时限
	*/
	public Long  getTreatasdeadtimeout()
	{
		return this.treatasdeadtimeout;
	}
	/**
		设置处理时限单位
	*/
	public void setTreatasdeadtimeoutunit(String treatasdeadtimeoutunit)
	{
		this.treatasdeadtimeoutunit=treatasdeadtimeoutunit;
	}
	/**
		获取处理时限单位
	*/
	public String  getTreatasdeadtimeoutunit()
	{
		return this.treatasdeadtimeoutunit;
	}
	/**
		设置执行作业群集名称
	*/
	public void setClustername(String clustername)
	{
		this.clustername=clustername;
	}
	/**
		获取执行作业群集名称
	*/
	public String  getClustername()
	{
		return this.clustername;
	}
	/**
		设置创建者
	*/
	public void setInsertUser(String insertUser)
	{
		this.insertUser=insertUser;
	}
	/**
		获取创建者
	*/
	public String  getInsertUser()
	{
		return this.insertUser;
	}
	/**
		设置创建时间
	*/
	public void setInsertDatetime(Date insertDatetime)
	{
		this.insertDatetime=insertDatetime;
	}
	/**
		获取创建时间
	*/
	public Date  getInsertDatetime()
	{
		return this.insertDatetime;
	}
	/**
		设置修改者
	*/
	public void setUpdateUser(String updateUser)
	{
		this.updateUser=updateUser;
	}
	/**
		获取修改者
	*/
	public String  getUpdateUser()
	{
		return this.updateUser;
	}
	/**
		设置修改时间
	*/
	public void setUpdateDatetime(Date updateDatetime)
	{
		this.updateDatetime=updateDatetime;
	}
	/**
		获取修改时间
	*/
	public Date  getUpdateDatetime()
	{
		return this.updateDatetime;
	}

  public static String getTriggerName(String jobName) {
    return jobName + "Trigger";
  }

  public static String getTriggerGroup(String jobGroup) {
    return jobGroup + "TriggerGroup";
  }

  public static String getCalendarName(String jobName) {
    return jobName + "Calendar";
  }}
