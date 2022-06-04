package com.devsuperior.movieflix.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JwtTokenStore tokenStore;

	private final String[] PUBLIC_URIS = {
		"/oauth/token",
		"/h2-console/**",
	};
	
	private final String[] PUBLIC_GET_URIS = {
			
	};
	
	private final String[] ADMIN_URIS = {
			
	};
	
	private final String[] PROFILES_GET_URIS = {
		"/genres",
		"/movies",
		"/movies/{id}",
	};
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// H2 database config
		if (List.of(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http.authorizeRequests()
			.antMatchers(PUBLIC_URIS).permitAll()
			.antMatchers(HttpMethod.GET, PUBLIC_GET_URIS).permitAll()
			.antMatchers(HttpMethod.GET, PROFILES_GET_URIS).hasAnyRole("VISITOR", "MEMBER")
			.antMatchers(ADMIN_URIS).hasAnyRole("ADMIN")
			.anyRequest().authenticated();
	}
	
}
