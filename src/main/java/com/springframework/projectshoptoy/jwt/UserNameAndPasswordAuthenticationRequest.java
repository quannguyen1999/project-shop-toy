package com.springframework.projectshoptoy.jwt;
public class UserNameAndPasswordAuthenticationRequest {
	private String username;
	private String password;
	public UserNameAndPasswordAuthenticationRequest() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}