package com.bitmonlab.osiris.api.security.dropwizard;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.bitmonlab.osiris.commons.model.security.Constants;

public class PassthruAuthProvider implements AuthenticationProvider {

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
				
		String username = String.valueOf(authentication.getPrincipal());
		String password = String.valueOf(authentication.getCredentials());

		if (username == null || username.isEmpty() ||
				password == null || password.isEmpty()) {
			
			throw new AuthenticationCredentialsNotFoundException(Constants.REQUIRED_CREDENTIALS);
		}
		
		Authentication authToken = new PreAuthenticatedAuthenticationToken(username, password);
		
		return authToken;
	}

	public boolean supports(Class<?> authentication) {
		
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
