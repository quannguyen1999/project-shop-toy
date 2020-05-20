package com.springframework.projectshoptoy.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.google.common.net.HttpHeaders;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
	private String secretKey;
	private String tokenPrefix;
	private String tokenExpirationAfterDays;
	public String getAuthorizationHeader() {
		return HttpHeaders.AUTHORIZATION;
	}
}
