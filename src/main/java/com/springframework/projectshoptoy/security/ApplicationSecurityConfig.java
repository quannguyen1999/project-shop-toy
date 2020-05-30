package com.springframework.projectshoptoy.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springframework.projectshoptoy.controller.AccountController;
import com.springframework.projectshoptoy.controller.CategoryController;
import com.springframework.projectshoptoy.controller.CustomerController;
import com.springframework.projectshoptoy.controller.ProductController;
import com.springframework.projectshoptoy.controller.SupplierController;
import com.springframework.projectshoptoy.security.jwt.JwtConfig;
import com.springframework.projectshoptoy.security.jwt.JwtTokenverifier;
import com.springframework.projectshoptoy.security.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.springframework.projectshoptoy.security.oath.ApplicationUserService;

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
	//những trang cho phép truy cập
	private static final String[] AUTH_WHITELIST = {
			"/swagger-resources/**",
			"/swagger-ui.html",
			"/v2/api-docs",
			"/webjars/**"
	};
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		//tắt sessionid
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		//nơi để xác minh username và gửi token
		.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(),jwtConfig,secretKey))
		//nơi để xác minh token
		.addFilterAfter(new JwtTokenverifier(secretKey,jwtConfig),JwtUsernameAndPasswordAuthenticationFilter.class)
		.authorizeRequests() 
		.antMatchers(CategoryController.BASE_URL+"/**").permitAll()
		.antMatchers(SupplierController.BASE_URL+"/**").permitAll()
		.antMatchers(ProductController.BASE_URL+"/**").permitAll()
		.antMatchers(AccountController.BASE_URL+"/**").permitAll()
		.antMatchers(HttpMethod.POST,AccountController.BASE_URL).permitAll()
		.antMatchers(AUTH_WHITELIST).permitAll() // cho phép truy cập
		.antMatchers("/api/**").hasAnyRole(ADMIN.name(),EMP.name())
		.anyRequest()	//còn lại bấy cứ request nào
		.authenticated(); //đều phải xác thực
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
