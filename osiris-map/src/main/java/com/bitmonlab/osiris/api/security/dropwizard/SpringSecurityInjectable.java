package com.bitmonlab.osiris.api.security.dropwizard;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.google.common.base.Preconditions.*;

import com.bitmonlab.osiris.commons.model.security.BasicAuth;
import com.bitmonlab.osiris.commons.model.security.Constants;
import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;

public class SpringSecurityInjectable extends
		AbstractHttpContextInjectable<BasicAuth> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringSecurityInjectable.class);

	private final boolean authenticationRequired;
	private final ApplicationContext appContext;

	public SpringSecurityInjectable(boolean authenticationRequired,	ApplicationContext context) {
		this.authenticationRequired = authenticationRequired;
		this.appContext = context;
	}

	@Override
	public BasicAuth getValue(HttpContext context) {
		
		

		Optional<BasicAuth> principal = Optional.absent();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		
		

		if (auth != null && authenticationRequired) {

			String username = String.valueOf(auth.getPrincipal());
			String password = String.valueOf(auth.getCredentials());
			String api_key = String.valueOf(context.getRequest().getRequestHeader("api_key").get(0));

			if (username == null || username.isEmpty())
				throw new AuthException(Constants.REQUIRED_USERNAME);

			if (password == null || password.isEmpty())
				throw new AuthException(Constants.REQUIRED_PASSWORD);
			
			if (api_key == null || api_key.isEmpty())
				throw new AuthException(Constants.REQUIRED_APPID);

			@SuppressWarnings("unchecked")
			Authenticator<SpringSecurityCredentials, BasicAuth> authenticator = 
					(Authenticator<SpringSecurityCredentials, BasicAuth>) appContext.getBean(DropwizardAuthenticator.class.getName());

			SpringSecurityCredentials credentials = new SpringSecurityCredentials(username, password, api_key);

			principal = authenticate(authenticator, credentials);

		} else if (auth == null && authenticationRequired)
			throw new AuthException(Constants.REQUIRED_CREDENTIALS);

		return principal.get();
	}

	private <C, P> Optional<P> authenticate(Authenticator<C, P> authenticator, C credentials) {
		checkNotNull(authenticator);
		checkNotNull(credentials);

		Optional<P> principal;

		try {
			principal = authenticator.authenticate(credentials);
		} catch (AuthenticationException e) {
			Throwable cause = Throwables.getRootCause(e);
			LOGGER.info("authentication failed", cause.getMessage());
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
		} catch (Exception e) {
			LOGGER.error("authentication error", e);
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
		}

		return principal;
	}

}
