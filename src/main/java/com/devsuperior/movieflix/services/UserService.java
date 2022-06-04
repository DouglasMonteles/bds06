package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devsuperior.movieflix.dtos.UserDTO;
import com.devsuperior.movieflix.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	public UserDTO getUserLogged() {
		var user = authService.authenticated();
		return new UserDTO(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = this.userRepository.findByEmail(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found. Username: " + username);
		}
		
		
		return user;
	}
	
}
