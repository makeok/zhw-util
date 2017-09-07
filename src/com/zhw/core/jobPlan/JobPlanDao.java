package com.zhw.core.jobPlan;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value="jobPlanDao")
public class JobPlanDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 
	 * @功能描述: 通过JobPlanQuery对象，查询quartz定时计划调度配置表
	 * @param jobPlan
	 * @param pageSize
	 * @param pageNum
	 * @异常类型:
	 */
	public List<JobPlan> findList(JobPlanQuery query) throws Exception
	{
		StringBuilder hql=new StringBuilder();
		hql.append("select * from qrtz_jobplan ett where 1=1");
		List<Object> param=new ArrayList<Object>();
		if(query!=null)
		{
			if(!StringUtils.isEmpty(StringUtils.trimToEmpty(query.getId())))
			{
				hql.append(" and ett.id=?");
				param.add(query.getId().trim());
			}
			if(!StringUtils.isEmpty(StringUtils.trimToEmpty(query.getJobname())))
			{
				hql.append(" and ett.jobname=?");
				param.add(query.getJobname().trim());
			}
			if(!StringUtils.isEmpty(StringUtils.trimToEmpty(query.getJobgroup())))
			{
				hql.append(" and ett.jobgroup=?");
				param.add(query.getJobgroup().trim());
			}
			if(!StringUtils.isEmpty(StringUtils.trimToEmpty(query.getClassname())))
			{
				hql.append(" and ett.classname=?");
				param.add(query.getClassname().trim());
			}
			if(!StringUtils.isEmpty(StringUtils.trimToEmpty(query.getJobparameter())))
			{
				hql.append(" and ett.jobparameter=?");
				param.add(query.getJobparameter().trim());
			}
			if(!StringUtils.isEmpty(StringUtils.trimToEmpty(query.getTriggerpolicy())))
			{
				hql.append(" and ett.triggerpolicy=?");
				param.add(query.getTriggerpolicy().trim());
			}
			if(query.getTriggepriority()!=null)
			{
				hql.append(" and ett.triggepriority=?");
				param.add(query.getTriggepriority());
			}
			if(!StringUtils.isEmpty(StringUtils.trimToEmpty(query.getDayexcluded())))
			{
				hql.append(" and ett.dayexcluded=?");
				param.add(query.getDayexcluded().trim());
			}
			if(query.getRetrycount()!=null)
			{
				hql.append(" and ett.retrycount=?");
				param.add(query.getRetrycount());
			}
			if(query.getInvalid()!=null)
			{
				hql.append(" and ett.invalid=?");
				param.add(query.getInvalid());
			}
			if(query.getTreatasdeadtimeout()!=null)
			{
				hql.append(" and ett.treatasdeadtimeout=?");
				param.add(query.getTreatasdeadtimeout());
			}
			if(!StringUtils.isEmpty(StringUtils.trimToEmpty(query.getTreatasdeadtimeoutunit())))
			{
				hql.append(" and ett.treatasdeadtimeoutunit=?");
				param.add(query.getTreatasdeadtimeoutunit().trim());
			}
			if(!StringUtils.isEmpty(StringUtils.trimToEmpty(query.getClustername())))
			{
				hql.append(" and ett.clustername=?");
				param.add(query.getClustername().trim());
			}
			if(!StringUtils.isEmpty(StringUtils.trimToEmpty(query.getInsertUser())))
			{
				hql.append(" and ett.insertUser=?");
				param.add(query.getInsertUser().trim());
			}
			if(query.getInsertDatetime()!=null)
			{
				hql.append(" and ett.insertDatetime=?");
				param.add(query.getInsertDatetime());
			}
			if(!StringUtils.isEmpty(StringUtils.trimToEmpty(query.getUpdateUser())))
			{
				hql.append(" and ett.updateUser=?");
				param.add(query.getUpdateUser().trim());
			}
			if(query.getUpdateDatetime()!=null)
			{
				hql.append(" and ett.updateDatetime=?");
				param.add(query.getUpdateDatetime());
			}
			if(query.getOrderField()!=null&&!query.getOrderField().equals(""))
			{
				hql.append(" order by "+query.getOrderField()+",id "+query.getOrderDirection());
			}else
			{
				hql.append(" order by id desc");
			}
		}else{
			hql.append(" order by id desc");
		}
		List<JobPlan> ret = null;
//		Map<String, Object> m = jdbcTemplate.queryForMap(hql.toString(), param.toArray());
//		Properties p = jdbcTemplate.getDataSource().getConnection().getClientInfo();
		if(param.size()>0){
			ret = jdbcTemplate.query(hql.toString(), param.toArray(), BeanPropertyRowMapper.newInstance(JobPlan.class));
		}else{
			ret = jdbcTemplate.query(hql.toString(),BeanPropertyRowMapper.newInstance(JobPlan.class));	
		}
		return ret;
	}
}
