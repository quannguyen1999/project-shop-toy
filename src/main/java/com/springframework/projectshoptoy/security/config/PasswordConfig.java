package com.springframework.projectshoptoy.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//Mã hóa password có độ dài là 10
//@Configuration để làm cấu hình thay vì dùng xml
//@Bean để thêm bean vào spring container
@Configuration
public class PasswordConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}
