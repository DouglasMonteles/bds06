package com.devsuperior.movieflix.components;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.devsuperior.movieflix.repositories.UserRepository;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, 
			OAuth2Authentication authentication) {
		var email = authentication.getName();
		var user = userRepository.findByEmail(email);
		
		if (user == null) {
			LOGGER.error("User not found. Username: " + email);
			throw new UsernameNotFoundException("User not found. Username: " + email);
		}
		
		var tokenAdditionalInformation = new HashMap<String, Object>();
		
		tokenAdditionalInformation.put("id", user.getId());
		tokenAdditionalInformation.put("name", user.getName());
		
		var token = (DefaultOAuth2AccessToken) accessToken;
		
		token.setAdditionalInformation(tokenAdditionalInformation);
		
		return accessToken;
	}
	
}
