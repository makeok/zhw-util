package com.zhw.core.jobPlan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JobPlanService{
	@Autowired
	private JobPlanDao jobPlanDao;

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<JobPlan> findList(JobPlanQuery query) throws Exception
	{
		return this.jobPlanDao.findList(query);
	}
}
