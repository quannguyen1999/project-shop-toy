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

import com.springframework.projectshoptoy.jwt.JwtTokenverifier;
import com.springframework.projectshoptoy.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.springframework.projectshoptoy.oath.ApplicationUserService;
import static com.springframework.projectshoptoy.security.ApplicaitonUserRole.*;
//để add spring quét (may be)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{
	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
	
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//			tắt sessionid
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			//nơi để xác minh username và gửi token
			.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
			//nơi để xác minh token
			.addFilterAfter(new JwtTokenverifier(),JwtUsernameAndPasswordAuthenticationFilter.class)
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
