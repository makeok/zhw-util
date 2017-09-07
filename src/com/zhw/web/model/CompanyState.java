package com.zhw.web.model;

import java.util.Date;

public class CompanyState {
	private String shortName;
	private String companyName;
	private Long productNum;
	private Long customerNum;
	private Double money;
	private Double totalMoney;
	private String status;
	private Date starttime;
	private Date updatetime;
		
	public CompanyState() {
		super();
	}

	public CompanyState(String shortName, String companyName,
			Long productNum, Long customerNum, Double money, Double totalMoney,
			String status, Date starttime, Date updatetime) {
		super();
		this.shortName = shortName;
		this.companyName = companyName;
		this.productNum = productNum;
		this.customerNum = customerNum;
		this.money = money;
		this.totalMoney = totalMoney;
		this.status = status;
		this.starttime = starttime;
		this.updatetime = updatetime;
	}





	public String getShortName() {
		return shortName;
	}


	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getProductNum() {
		return productNum;
	}


	public void setProductNum(Long productNum) {
		this.productNum = productNum;
	}


	public Long getCustomerNum() {
		return customerNum;
	}


	public void setCustomerNum(Long customerNum) {
		this.customerNum = customerNum;
	}


	public Double getMoney() {
		return money;
	}


	public void setMoney(Double money) {
		this.money = money;
	}


	public Double getTotalMoney() {
		return totalMoney;
	}


	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}


	public String getStatus() {
		return status;
	}





	public void setStatus(String status) {
		this.status = status;
	}





	public Date getStarttime() {
		return starttime;
	}


	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}


	public Date getUpdatetime() {
		return updatetime;
	}


	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}


	
}
