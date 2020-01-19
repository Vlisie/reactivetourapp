package com.example.springtourapp;

import java.util.*;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.reactive.*;
import org.springframework.security.config.web.server.*;
import org.springframework.security.web.server.*;
import org.springframework.security.web.server.csrf.*;
import org.springframework.web.cors.*;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 16-1-20.
 */

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http
			.csrf()
				.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
				.and()
			.authorizeExchange()
				.pathMatchers("/ws/**").permitAll()
				.anyExchange().authenticated()
				.and()
			.oauth2Login()
				.and()
			.oauth2ResourceServer()
				.jwt().and().and().build();
	}
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
		configuration.setAllowedMethods(Collections.singletonList("GET"));
		configuration.setAllowedHeaders(Collections.singletonList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
