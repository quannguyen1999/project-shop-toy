package com.springframework.projectshoptoy.jwt;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;

@Configuration
public class JwsSecretKey {
	private final JwtConfig jwtConfig;

	@Autowired
	public JwsSecretKey(JwtConfig jwtConfig) {
		super();
		this.jwtConfig = jwtConfig;
	}
	
	@Bean
	public SecretKey secretKey() {
		return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
	}
}
