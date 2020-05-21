package com.springframework.projectshoptoy.security.jwt;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;
//cáu hình jwt key
@Configuration
public class JwsSecretKey {
	//tìm bean jwtConfig và tiêm vào qua @Autowired
	private final JwtConfig jwtConfig;

	@Autowired
	public JwsSecretKey(JwtConfig jwtConfig) {
		super();
		this.jwtConfig = jwtConfig;
	}
	
	//thêm bean secretKey vào spring container
	@Bean
	public SecretKey secretKey() {
		return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
	}
}
