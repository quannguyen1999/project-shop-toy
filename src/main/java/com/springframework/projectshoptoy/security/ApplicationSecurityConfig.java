package com.springframework.projectshoptoy.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springframework.projectshoptoy.jwt.JwtConfig;
import com.springframework.projectshoptoy.jwt.JwtTokenverifier;
import com.springframework.projectshoptoy.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.springframework.projectshoptoy.oath.ApplicationUserService;

import lombok.RequiredArgsConstructor;

import static com.springframework.projectshoptoy.security.ApplicaitonUserRole.*;

import javax.crypto.SecretKey;
//để add spring quét (may be)
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{
	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
	private final JwtConfig jwtConfig;
	private final SecretKey secretKey;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//			tắt sessionid
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			//nơi để xác minh username và gửi token
			.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(),jwtConfig,secretKey))
			//nơi để xác minh token
			.addFilterAfter(new JwtTokenverifier(secretKey,jwtConfig),JwtUsernameAndPasswordAuthenticationFilter.class)
			.authorizeRequests() 
			.antMatchers("/swagger-ui.html").permitAll() // cho phép truy cập
			.antMatchers("/api/**").hasAnyRole(ADMIN.name(),EMP.name())
			.anyRequest()	//còn lại bấy cứ request nào
			.authenticated() //đều phải xác thực
			.and() //và 
			.httpBasic();//hiện form để điền
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
}
