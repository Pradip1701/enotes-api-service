package com.becoder.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.becoder.entity.User;
import com.becoder.entity.Role;


public class MyUserDetails implements UserDetails{

	private User userdtls;
	
	
	
	public MyUserDetails(User userdtls) {
		super();
		this.userdtls = userdtls;
	}

	
	public User getUserdtls() {
		return userdtls;
	}


	public void setUserdtls(User userdtls) {
		this.userdtls = userdtls;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<SimpleGrantedAuthority> auth=new ArrayList();
		
		userdtls.getRole().forEach(r->{
			      auth.add(new SimpleGrantedAuthority("ROLE_"+r.getName()));
				});
		return auth;
	}

	@Override
	public String getPassword() {
		
		return userdtls.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userdtls.getEmail();
	}

}
