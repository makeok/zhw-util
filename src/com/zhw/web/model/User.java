package com.zhw.web.model;

import java.util.Date;

/**
 * 用户模型
 * 
 * @author StarZou
 * @since 2014年7月5日 下午12:07:20
 **/
public class User {
	public static final String state_off = "0"; // 正常,未登录
	public static final String state_on = "1"; // 登陆中
	public static final String state_un = "2"; // 注销
	public static final String state_ex = "3"; // 账号异常
	
	public static final String type_CUSTOMER = "1"; // 账号异常
	public static final String type_COMPANY = "2"; // 账号异常
	public static final String type_SERVER = "3"; // 账号异常
	
    private Long id;

    private String username;
    
    private String name_zh;

    private String password;

    private String state;

//    @JSONField(format="yyyyMMddHHmmss")
    private Date create_time;
    
    private String type; // 1,客户，2：经销商，3：server
    
    private String shortName;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }







	public User(Long id, String username, String name_zh, String password,
			String state, Date create_time, String type, String shortName) {
		super();
		this.id = id;
		this.username = username;
		this.name_zh = name_zh;
		this.password = password;
		this.state = state;
		this.create_time = create_time;
		this.type = type;
		this.shortName = shortName;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }



    public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName_zh() {
		return name_zh;
	}

	public void setName_zh(String name_zh) {
		this.name_zh = name_zh;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", state=" + state + ", create_time=" + create_time + "]";
    }
}
