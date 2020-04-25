package com.springframework.projectshoptoy.security;
import com.google.common.collect.Sets;
import static com.springframework.projectshoptoy.security.ApplicationUserPermission.*;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
public enum ApplicaitonUserRole {
	ADMIN(Sets.newHashSet(ADMIN_READ,ADMIN_WRITE)),
	CLIENT(Sets.newHashSet(CLIENT_READ,CLIENT_WRITE)),
	EMP(Sets.newHashSet(EMP_READ,EMP_WRITE));
	
	private final Set<ApplicationUserPermission> permission;

	private ApplicaitonUserRole(Set<ApplicationUserPermission> permission) {
		this.permission = permission;
	}

	public Set<ApplicationUserPermission> getPermission() {
		return permission;
	}	
	
	public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
		Set<SimpleGrantedAuthority> permissions=getPermission().stream()
				.map(permission-> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toSet());
		permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
		return permissions;
	}
}
