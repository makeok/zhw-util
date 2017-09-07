package com.zhw.web.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

//import com.alibaba.druid.pool.DataSource;
import com.zhw.core.util.MyStringUtil;

public class CommonBaseDao{
	
	public static int[] insertMany(DataSource dataSource, String sql,List inList)throws Exception{
		if(sql == null){
			return null;
		}
		SqlParameterSource[] spsBatch=SqlParameterSourceUtils.createBatch(inList.toArray());
		NamedParameterJdbcTemplate npJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
		return npJdbcTemplate.batchUpdate(sql, spsBatch);
	}
	
	public static int insert(DataSource dataSource, String sql,Object obj)throws Exception{	
		if(sql == null){
			return 0;
		}
		if(obj==null){
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			return jdbcTemplate .update(sql);
		}else{
			SqlParameterSource sps=new BeanPropertySqlParameterSource(obj);
			NamedParameterJdbcTemplate npJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
			return npJdbcTemplate.update(sql, sps);
		}
	}
	
	public static int update(DataSource dataSource, String sql,Object obj)throws Exception{	
		if(sql == null){
			return 0;
		}
		if(obj==null){
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			return jdbcTemplate.update(sql);
		}else{
			SqlParameterSource sps=new BeanPropertySqlParameterSource(obj);
			NamedParameterJdbcTemplate npJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
			return npJdbcTemplate.update(sql, sps);	
		}
	}
	public static int[] updateMany(DataSource dataSource, String sql,List inList)throws Exception{	
		if(sql == null){
			return null;
		}
		if(inList==null){
			return null;
		}else{
			SqlParameterSource[] spsBatch=SqlParameterSourceUtils.createBatch(inList.toArray());
			NamedParameterJdbcTemplate npJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
			return npJdbcTemplate.batchUpdate(sql, spsBatch);
		}
	}

	
	public static <T> List<T> query(DataSource dataSource, String sql, List paramList, Class<T> cls)throws Exception{
		if(sql == null){
			return null;
		}
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		if(paramList == null){			
			return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(cls));
		}
		else{
			return jdbcTemplate.query(sql,paramList.toArray(),BeanPropertyRowMapper.newInstance(cls));
		}
	}
	
	public static List<Map<String, Object>> query(DataSource dataSource, String sql, List paramList)throws Exception{
		if(sql == null){
			return null;
		}
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		if(paramList == null){
			return jdbcTemplate.queryForList(sql);
		}
		else{
			return jdbcTemplate.queryForList(sql, paramList.toArray());
		}
	}
	
	public static String queryOne(DataSource dataSource, String sql, List paramList)throws Exception{
		if(sql == null){
			return null;
		}
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		if(paramList == null){
			return jdbcTemplate.queryForObject(sql, String.class);
		}
		else{
			return jdbcTemplate.queryForObject(sql, paramList.toArray(),String.class);
		}
	}
	
	public static long queryNum(DataSource dataSource, String sql, List paramList)throws Exception{
		if(sql == null){
			return 0;
		}
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String out = "";
		if(paramList == null){
			out = jdbcTemplate.queryForObject(sql, String.class);
		}
		else{
			out = jdbcTemplate.queryForObject(sql, paramList.toArray(),String.class);
		}
		if(MyStringUtil.isEmpty(out)){
			return 0;
		}
		return Long.valueOf(out);
	}


	public static boolean testValid(DataSource dataSource, DataSource newds, String testsql) {
		if(newds == null || MyStringUtil.isEmpty(testsql)){
			return false;
		}
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(newds);
			jdbcTemplate.queryForList(testsql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean testValid(DataSource newds, String testsql) {
		if(newds == null || MyStringUtil.isEmpty(testsql)){
			return false;
		}
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(newds);
			jdbcTemplate.queryForList(testsql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
}
