package com.springframework.projectshoptoy.security;

//nơi đây để tạo authorities
public enum ApplicationUserPermission {
	ADMIN_READ("admin:read"),
	ADMIN_WRITE("admin:write"),
	EMP_READ("EMP:read"),
	EMP_WRITE("EMP:write"),
	CLIENT_READ("client:read"),
	CLIENT_WRITE("client:write");
	
	private final String permission;

	private ApplicationUserPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
	
	
}
