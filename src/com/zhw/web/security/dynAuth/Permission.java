package com.zhw.web.security.dynAuth;

public class Permission {
	private String permissionUrl;
	private String permissionvalue;
	public Permission(String permissionUrl, String permissionvalue) {
		super();
		this.permissionUrl = permissionUrl;
		this.permissionvalue = permissionvalue;
	}
	public String getPermissionUrl() {
		return permissionUrl;
	}
	public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl;
	}
	public String getPermissionvalue() {
		return permissionvalue;
	}
	public void setPermissionvalue(String permissionvalue) {
		this.permissionvalue = permissionvalue;
	}
	
	
}
