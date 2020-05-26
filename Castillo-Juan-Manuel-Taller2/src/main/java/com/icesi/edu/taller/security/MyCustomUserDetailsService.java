package com.icesi.edu.taller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.icesi.edu.taller.model.TsscAdmin;
import com.icesi.edu.taller.repository.AdminRepository;


//ci-201-mvc-tutorial-val-steps-sec-ini

@Service
public class MyCustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Por admin
		
		TsscAdmin admin = adminRepository.findByUser(username);
		
		if (admin != null) {
			
			User.UserBuilder builder = User.withUsername(username).password(admin.getPassword()).roles(admin.getSuperAdmin());
			
			return builder.build();
			
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
	}
	
	//
}