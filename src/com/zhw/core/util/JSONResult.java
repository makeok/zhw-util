package com.zhw.core.util;



/**
 * JSONResult : Response JSONResult for RESTful,封装返回JSON格式的数据
 *
 * @author StarZou
 * @since 2014年5月26日 上午10:51:46
 */

public class JSONResult{

    /**
     * 数据
     */
    private Object data;
    private String msg;
    private Boolean success;
    private Integer total;
    private String errorCode;
    



	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public Boolean getSuccess() {
		return success;
	}


	public void setSuccess(Boolean success) {
		this.success = success;
	}


	public Object getData() {
        return data;
    }
    

    public void setData(Object data) {
        this.data = data;
    }    
    
    public Integer getTotal() {
		return total;
	}


	public void setTotal(Integer total) {
		this.total = total;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public JSONResult() {
        super();
    }

    /**
     * 自定义返回的结果
     *
     * @param data
     * @param message
     * @param success
     */
    public JSONResult(Object data, String msg, Boolean success) {
        this.data = data;
        this.setMsg(msg);
        this.setSuccess(success);
    }

    /**
     * 成功返回数据和消息
     *
     * @param data
     * @param message
     */
    public JSONResult(Object data, String msg) {
        this.data = data;
        this.setMsg(msg);
        this.setSuccess(true);
    }

    /**
     * 成功返回数据
     *
     * @param data
     */
    public JSONResult(Object data) {
        this.data = data;
        this.setSuccess(true);
    }


	public JSONResult(String msg, Boolean suc) {
        this.setSuccess(suc);
        this.setMsg(msg);
	}


	public JSONResult(Object data2, String msg, Boolean suc, Integer total2) {
        this.setSuccess(suc);
        this.setMsg(msg);
        this.setData(data2);
        this.setTotal(total2);
	}


	public JSONResult(Object data, String msg, Boolean success, Integer total,
			String errorCode) {
		super();
		this.data = data;
		this.msg = msg;
		this.success = success;
		this.total = total;
		this.errorCode = errorCode;
	}


	@Override
	public String toString() {
		return JsonUtil.toJSONDef(this);
	}
	
	public static String getJsonRet(String msg,Boolean suc){
		JSONResult jr = new JSONResult(msg,suc);
		return jr.toString();
	}
	public static String getJsonRet(Object data,String msg,Boolean suc){
		JSONResult jr = new JSONResult(data,msg,suc);
		return jr.toString();
	}
	public static String getJsonRet(Object data,String msg,Boolean suc,Integer total){
		JSONResult jr = new JSONResult(data,msg,suc,total);
		return jr.toString();
	}
	public static String getJsonRet(Object data,String msg,Boolean suc,Integer total,String erorCode){
		JSONResult jr = new JSONResult(data,msg,suc,total,erorCode);
		return jr.toString();
	}
}