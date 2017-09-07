package com.zhw.core.util;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SqlstrUtil {
	
	/**
	 * 防止sql注入：替换危险字符
	 * @param sql
	 * @return
	 */
	public static String replace(String sql){
		if (sql!=null) {
		     sql=sql.replaceAll(";","");
		     sql=sql.replaceAll("&","");
		     sql=sql.replaceAll("<","");
		     sql=sql.replaceAll(">","");
		     sql=sql.replaceAll("'","");
		     sql=sql.replaceAll("--","");
		     sql=sql.replaceAll("/","");
		     sql=sql.replaceAll("%","");
		}
	    return sql;
	}
	/** 
	 * @Method getSqlInFiledStr 
	 * @Description List<String> list，以regex分隔
	 * @param col
	 * @param regex
	 * @return
	 */
	public static String getSqlInFiledStr(List<String> list) {
		StringBuilder sb = new StringBuilder();
		int i=0;
		if(list!=null){
			for (String id:list) {
				if(id!=null){
					if(i!=0)
						sb.append(",");
					sb.append("'"+id+"'");
					i++;
				}
			}
		}
		return sb.toString();
	}
	public static String getSqlInFiledStr(String... list) {
		StringBuilder sb = new StringBuilder();
		int i=0;
		if(list!=null){
			for (String id:list) {
				if(id!=null){
					if(i!=0)
						sb.append(",");
					sb.append("'"+id+"'");
					i++;
				}
			}
		}
		return sb.toString();
	}
	/** 
	 * @Method getSqlInFiled 
	 * @Description 输入1，2，3 输出'1','2','3'
	 * @param col
	 * @param regex
	 * @return
	 */
	public static String getSqlInFiled(List<?> list) {
		StringBuilder sb = new StringBuilder();
		int i=0;
		if(list!=null){
			for (Object id:list) {
				if(id!=null){
					if(i!=0)
						sb.append(",");
					sb.append("'"+id+"'");
					i++;
				}
			}
		}
		return sb.toString();
	}
	
	/** 
	 * @Method getSqlWhereFiled 
	 * @Description 输入col,value 输出col='value'
	 * @return
	 */
	public static String getSqlWhereFiled(String col, String value) {
		if(value!=null && !value.trim().isEmpty()){
			return " and "+col+"='"+value+"' ";
		}
		return "";
	}
	
	/** 
	 * @Method collect2String 
	 * @Description String集合转String，以regex分隔
	 * @param col
	 * @param regex
	 * @return
	 */
	public static String sqlCollect2String(Collection<String> col) {
		Iterator it = col.iterator();
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			String str = (String) it.next();
			sb.append(",");
			sb.append("'"+str+"'");
		}
		return sb.toString().substring(1);
	}
	
	/** 
	 * @Method collect2String 
	 * @Description String集合转String，以regex分隔
	 * @param col
	 * @param regex
	 * @return
	 */
	public static String collect2String(Collection<String> col, String regex) {
		Iterator it = col.iterator();
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			String str = (String) it.next();
			sb.append(regex);
			sb.append(str);
		}
		return sb.toString().substring(1);
	}
	
	/** 
	 * @Method collect2String 
	 * @Description String集合转String，以''分隔
	 * @param col1,col2转化为'col1','col2'
	 * @param regex
	 * @return
	 */
	public static String sqlAddComa2Quote(String...args) {
		if(args != null){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<args.length;i++){				
				if(i>0){
					sb.append(",");
				}
				sb.append("'"+args[i]+"'");
			}
			return sb.toString();
		}else{
			return "";
		}
	}
	/** 
	 * @Method collect2String 
	 * @Description String集合转String，以''分隔
	 * @param col1,col2转化为:col1,:col2
	 * @param regex
	 * @return
	 */
	public static String sqlAddComaColon(String...args) {
		if(args != null){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<args.length;i++){				
				if(i>0){
					sb.append(",");
				}
				sb.append(":"+args[i]);
			}
			return sb.toString();
		}else{
			return "";
		}
	}
	/** 
	 * @Method collect2String 
	 * @Description String集合转String，以''分隔
	 * @param col1,col2转化为:col1,:col2
	 * @param regex
	 * @return
	 */
	public static String addMaohao(String in) {
		if(!MyStringUtil.isEmpty(in)){
			in = MyStringUtil.trim(in, ' ');
			String [] args = in.split(",", -1);
			if(args!=null){
				StringBuffer sb = new StringBuffer();
				for(int i=0;i<args.length;i++){				
					if(i>0){
						sb.append(",");
					}
					sb.append(":"+args[i]);
				}
				return sb.toString();
			}
		}
		return "";
	}
	/** 
	 * @Method collect2String 
	 * @Description String集合转String，以''分隔
	 * @param col1,col2转化为col1,:col2
	 * @param regex
	 * @return
	 */
	public static String sqlAddComa(String...args) {
		if(args != null){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<args.length;i++){				
				if(i>0){
					sb.append(",");
				}
				sb.append(args[i]);
			}
			return sb.toString();
		}else{
			return "";
		}
	}
	/** 
	 * @Method getSqlWhereFiled 
	 * @Description String集合转String，以regex分隔
	 * @param col,judge,value转化为 and col='value'
	 * @return
	 */
	public static void getSqlWhereFiled(String col,String judge, Object value,List<Object> paramList,StringBuilder sql) {
		if(value != null){
			sql.append(" and "+col+judge+"? ");
			paramList.add(value);	
		}
	}
	public static String getSqlLikeFiled(String col, String value) {
		if(value!=null && !value.trim().isEmpty()){
			return " and "+col+" like '%"+value+"%' ";
		}
		return "";
	}
	public static void getSqlLikeFiled(String col,String value,StringBuilder sql) {
		sql.append(" and "+col+" like '%"+value+"%' ");
	}
	public static String getSqlLimit(Long limit, Long offset) {
		StringBuffer sql = new StringBuffer();
		if(limit!=null && offset!=null){
			sql.append(" limit "+limit+","+offset);
		}else if(limit!=null && offset==null){
			sql.append(" limit "+limit+","+10);
		}
		else if(limit==null && offset!=null){
			sql.append(" limit "+0+","+offset);
		}
		return sql.toString();
	}

	
	//jdbc update
	public static String jdbcUpdate(String in){
		if(!MyStringUtil.isEmpty(in)){
			in = MyStringUtil.trim(in, ' ');
			String [] args = in.split(",", -1);
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<args.length;i++){				
				if(i>0){
					sb.append(",");
				}
				sb.append(args[i]+"=:"+args[i]);
			}
			return sb.toString();
		}else{
			return "";
		}
	}
	public static String jdbcUpdate2(String ... args){
		if(args != null){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<args.length;i++){				
				if(i>0){
					sb.append(",");
				}
				sb.append(args[i]+"=:"+args[i]);
			}
			return sb.toString();
		}else{
			return "";
		}
	}
	//jdbc insert
	public static String jdbcWhere(String in){
		if(!MyStringUtil.isEmpty(in)){
			in = MyStringUtil.trim(in, ' ');
			String [] args = in.split(",", -1);
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<args.length;i++){				
				if(i>0){
					sb.append(" and ");
				}
				sb.append(args[i]+"=:"+args[i]);
			}
			return sb.toString();
		}else{
			return "";
		}
	}
	//jdbc insert
	public static String jdbcWhere2(String ... args){
		if(args != null){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<args.length;i++){				
				if(i>0){
					sb.append(" and ");
				}
				sb.append(args[i]+"=:"+args[i]);
			}
			return sb.toString();
		}else{
			return "";
		}
	}
	//jdbc insert
	public static String jdbcSelect(String ... args){
		if(args != null){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<args.length;i++){				
				if(i>0){
					sb.append(",");
				}
				sb.append(args[i]+"=:"+args[i]);
			}
			return sb.toString();
		}else{
			return "";
		}
	}
	public static void jdbcSelect(String tbl, String col,Object obj, List<Object> paramList,StringBuilder sql,String judge) {
		if(obj==null || sql ==null || paramList==null || MyStringUtil.isEmptys(col,judge)){
			return;
		}
		try{
			String tblfi = "";
			if(!MyStringUtil.isEmpty(tbl)) tblfi = tbl+".";
			
			String methodName = "get"+MyStringUtil.upperFirstChar(col);
			Method m = obj.getClass().getMethod(methodName);
			Object geted = m.invoke(obj);
			if(geted instanceof String){
				if (!MyStringUtil.isEmpty((String) geted)) {
					sql.append(" and "+tblfi+col+judge+"?");		
					paramList.add(geted);
				}
			}else{
				if (null != geted) {
					sql.append(" and "+tblfi+col+judge+"?");		
					paramList.add(geted);
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void jdbcSelectmany(String tbl, String cols,Object obj, List<Object> paramList,StringBuilder sql,String judge) {
		if(!MyStringUtil.isEmpty(cols)){
			cols = MyStringUtil.trim(cols, ' ');
			String [] colall = cols.split(",",-1);
			for(String col:colall){
				jdbcSelect(tbl,col,obj,paramList,sql,judge);
			}
		}
	}
}
