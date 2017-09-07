package com.zhw.web.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="commonDao")
@Transactional
public class CommonDao{
	@Autowired
	public static DataSource dataSource;

	public static synchronized DataSource getDataSource() {
		return dataSource;		
	}
	
	
	public static void setDataSource(DataSource dataSource) {
		CommonDao.dataSource = dataSource;
	}
	
	public static int[] insertMany(String sql,List inList)throws Exception{
		return CommonBaseDao.insertMany(dataSource, sql, inList);
	}
	
	public static int insert(String sql,Object obj)throws Exception{	
		return CommonBaseDao.insert(dataSource, sql, obj);
	}
	
	public static int update(String sql,Object obj)throws Exception{	
		return CommonBaseDao.update(dataSource, sql, obj);
	}
	public static int[] updateMany(String sql,List inList)throws Exception{	
		return CommonBaseDao.updateMany(dataSource, sql, inList);
	}

	
	public static <T> List<T> query(String sql, List paramList, Class<T> cls)throws Exception{
		return CommonBaseDao.query(dataSource, sql, paramList,cls);
	}
	
	public static List<Map<String, Object>> query(String sql, List paramList)throws Exception{
		return CommonBaseDao.query(dataSource, sql, paramList);
	}
	
	public static String queryOne(String sql, List paramList)throws Exception{
		return CommonBaseDao.queryOne(dataSource, sql, paramList);
	}
	
	public static long queryNum(String sql, List paramList)throws Exception{
		return CommonBaseDao.queryNum(dataSource, sql, paramList);
	}




}
