package com.springframework.projectshoptoy.security.oath;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springframework.projectshoptoy.api.domain.Account;
import com.springframework.projectshoptoy.dao.MyEntityManager;

import static com.springframework.projectshoptoy.security.ApplicaitonUserRole.*;
//kiểm tra username và password sẽ xảy ra ở đây
@Service
public class ApplicationUserService implements UserDetailsService{
	@Autowired
	private MyEntityManager myEntityManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		ApplicationUser applicationUser=null;
		if(username==null) {
			throw new UsernameNotFoundException("Username can not null");
		}
		Account account=(Account) myEntityManager.findById(new Account(), username).get();
		if(account==null) {
			throw new UsernameNotFoundException("Username is not exists");
		}
		if(account.isAccType()==true) {
			applicationUser=new ApplicationUser(username,
					account.getPassword(),
					ADMIN.getGrantedAuthorities(),
					true, 
					true, 
					true,
					true);
		}else {
			applicationUser=new ApplicationUser(username,
					account.getPassword(),
					EMP.getGrantedAuthorities(),
					true, 
					true, 
					true,
					true);
		}
		return applicationUser;
	}
	
	
}
