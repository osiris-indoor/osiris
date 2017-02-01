package com.bitmonlab.osiris.api.security.dropwizard;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

import com.bitmonlab.osiris.api.core.map.exceptions.QueryException;
import com.bitmonlab.osiris.commons.model.security.BasicAuth;
import com.bitmonlab.osiris.commons.model.security.Constants;
import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;



@Named
@Component
public class DropwizardAuthenticator implements Authenticator<SpringSecurityCredentials, BasicAuth> {
	
	
	@Inject
	private AuthRepository authRepository;
	
	
	public Optional<BasicAuth> authenticate(SpringSecurityCredentials credentials) 
			throws AuthenticationException {

		checkNotNull(credentials);

		BasicAuth principal = null;
		try {
			principal = getPrincipal(credentials);
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			throw new AuthenticationException(new AuthException(Constants.APP_ID_NOTFOUND));	
		}

		return Optional.of(principal);
	}

	private BasicAuth getPrincipal(SpringSecurityCredentials credentials)
			throws AuthenticationException, QueryException {
		BasicAuth principal = null;

		
		BasicAuth credentialBD = authRepository.searchUserName(credentials.getApi_key(), credentials.getUsername());
		
		
		
		try {
			if(credentials.getUsername().equals(credentialBD.get_id()) &&
			   credentials.getPassword().equals(credentialBD.getPassword()) &&
			   credentials.getApi_key().equals(credentials.getApi_key())){
									
				  principal = new BasicAuth(credentials.getUsername());
				
			}else{			
				throw new AuthenticationException(
						new AuthException(Constants.BAD_CREDENTIALS));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new AuthenticationException(
					new AuthException(Constants.BAD_CREDENTIALS));
		}

		return principal;
	}

}

