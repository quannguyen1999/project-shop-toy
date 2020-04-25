package com.springframework.projectshoptoy.jwt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//nơi đây để client gửi credentials (username và password)
@Slf4j
@RequiredArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	//applicationManager dùng để xác minh userName và password
	private final AuthenticationManager authenticationManager;

	//nơi này để xác minh
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			//chèn username và password của request vào class userNameAndPassword ...request
			UserNameAndPasswordAuthenticationRequest authenticationRequest=new ObjectMapper()
					.readValue(request.getInputStream(), UserNameAndPasswordAuthenticationRequest.class);

			//khi đã có được thông tin request ta lấy nó ra
			Authentication authentication=new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), 
					authenticationRequest.getPassword());

			//xác minh thông qua ApplicationUserService
			Authentication authenticate=authenticationManager.authenticate(authentication);
				
			log.debug("Xác minh thành công");
			
			return authenticate;
		} catch (Exception e) {
			throw new RuntimeException(e);
			// TODO: handle exception
		}
	}

	//nếu thành công thì công việc này được thực hiện tiếp theo
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		//password key trong token
		String key="securesecuresecuresecuresecuresecuresecuresecure";
		
		//tạo token
		String token=Jwts.builder()
				//tên userName
				.setSubject(authResult.getName())
				//thẩm quyền của userName có
				.claim("authorities", authResult.getAuthorities())
				//ngày kích hoạt token
				.setIssuedAt(new Date())
				//ngày hết hạn token
				.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
				//hash key 
				.signWith(Keys.hmacShaKeyFor(key.getBytes()))
				.compact();

		response.addHeader("Authorization","Bearer "+token);

		//remember delte this
		//super.successfulAuthentication(request, response, chain, authResult);
	}
}
