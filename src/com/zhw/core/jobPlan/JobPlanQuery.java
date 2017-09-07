package com.zhw.core.jobPlan;
/**
 * @功能描述:quartz定时计划调度配置表查询对象
 * @开发人员:黄浩
 * @创建日期:2014-03-18
 */
public class JobPlanQuery extends JobPlan
{
	private Integer pageSize;//每页记录数
	private Integer pageNum;//每页条数
	private String orderField;//排序字段
	private String orderDirection;//排序方式  asc;desc
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getOrderDirection() {
		return orderDirection;
	}
	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
}
