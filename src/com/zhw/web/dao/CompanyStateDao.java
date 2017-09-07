package com.zhw.web.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhw.core.util.SqlstrUtil;
import com.zhw.web.model.CompanyState;

@Repository
public class CompanyStateDao{
	String tblName = "company_state";
	String cols = "shortName,companyName,productNum,customerNum,money,totalMoney,status,starttime,updatetime";
	String orderbystr = " order by companyName";
	String primkey = "shortName";
	String updateFields = "productNum,customerNum,money,totalMoney,status,starttime,updatetime";
	
	public int insert(CompanyState cm) throws Exception{
		String sql="insert into "+tblName+"("+cols+")values(" +SqlstrUtil.addMaohao(cols)+")";
		return CommonDao.insert(sql, cm);
	}
	
	public int[] insertMany(List<CompanyState> list) throws Exception{
		String sql="insert into "+tblName+"("+cols+")values(" +SqlstrUtil.addMaohao(cols)+")";
		return CommonDao.insertMany(sql, list);
	}
	
	public int updateAll(CompanyState ua) throws Exception {
		String sql="UPDATE "+tblName+" SET "
				+SqlstrUtil.jdbcUpdate(updateFields)
				+" WHERE "+SqlstrUtil.jdbcWhere(primkey);
		return CommonDao.update(sql, ua);
	}
	
	public int del(CompanyState ua) throws Exception {
		String sql="DELETE FROM "+tblName+" WHERE "+SqlstrUtil.jdbcWhere(primkey);
		return CommonDao.update(sql, ua);	
	}

	public List<CompanyState> selectByAll(CompanyState cm) throws Exception{
		if(cm == null){
			return null;
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("select * from "+tblName+" t where 1=1 ");
		List<Object> paramList = new ArrayList<Object>();
		
		SqlstrUtil.jdbcSelectmany("t",cols,cm,paramList,sql,"=");
		sql.append(orderbystr);
		return CommonDao.query(sql.toString(), paramList, CompanyState.class);
	}

}
