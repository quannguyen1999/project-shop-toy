package com.springframework.projectshoptoy.oath;

import java.util.Optional;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springframework.projectshoptoy.domain.Account;
import com.springframework.projectshoptoy.exception.NotFoundException;
import com.springframework.projectshoptoy.repositories.AccountRepository;

import lombok.RequiredArgsConstructor;
import static com.springframework.projectshoptoy.security.ApplicaitonUserRole.*;
//kiểm tra username và password sẽ xảy ra ở đây
@RequiredArgsConstructor
@Service
public class ApplicationUserService implements UserDetailsService{
	private final AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		ApplicationUser applicationUser=null;
		if(username==null) {
			throw new UsernameNotFoundException("Username can not null");
		}
		Optional<Account> account=accountRepository.findById(username);
		if(account.isPresent()==false) {
			throw new UsernameNotFoundException("Username is not exists");
		}
		if(account.get().isAccType()==true) {
			applicationUser=new ApplicationUser(username,
					account.get().getPassword(),
					ADMIN.getGrantedAuthorities(),
					true, 
					true, 
					true,
					true);
		}else {
			applicationUser=new ApplicationUser(username,
					account.get().getPassword(),
					EMP.getGrantedAuthorities(),
					true, 
					true, 
					true,
					true);
		}
		return applicationUser;
	}
	
	
}
