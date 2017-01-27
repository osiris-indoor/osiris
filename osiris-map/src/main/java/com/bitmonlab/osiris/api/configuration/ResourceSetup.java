package com.bitmonlab.osiris.api.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.ext.ExceptionMapper;

import org.eclipse.jetty.server.session.SessionHandler;

import com.bitmonlab.osiris.api.security.dropwizard.DropwizardAuthenticator;
import com.bitmonlab.osiris.api.security.dropwizard.SpringSecurityCredentials;
import com.bitmonlab.osiris.api.security.dropwizard.SpringSecurityProvider;
import com.bitmonlab.osiris.commons.model.security.BasicAuth;
import com.bitmonlab.osiris.core.errorhandler.RestErrorsHandler;
import com.github.pnayak.dropwizard.spring.AutoWiredService;
import com.google.common.cache.CacheBuilderSpec;
import com.sun.jersey.api.core.ResourceConfig;
import com.yammer.dropwizard.auth.CachingAuthenticator;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;


public class ResourceSetup extends AutoWiredService<OsirisConfiguration>{
		
	public ResourceSetup() {
		super("ResourceSetup", "com.bitmonlab.osiris.api");		
	}	
	
	
	
	public static void main(String[] args) throws Exception {
		new ResourceSetup().run(args);
	}		
	
	public void initialize(Bootstrap<OsirisConfiguration> bootstrap) {
		
		bootstrap.setName("ResourceSetup");		
	}	
	
	
	

	public void run(OsirisConfiguration configuration, Environment environment) throws Exception {
		
				
		super.runWithAppContext(configuration,environment,appContext);
		
		appContext.registerShutdownHook();
		appContext.scan("com.bitmonlab.osiris.api");
				
							
		// Remove all of Dropwizard's custom ExceptionMappers
        ResourceConfig jrConfig = environment.getJerseyResourceConfig();
        Set<Object> dwSingletons = jrConfig.getSingletons();
        List<Object> singletonsToRemove = new ArrayList<Object>();

        for (Object s : dwSingletons) {
            if (s instanceof ExceptionMapper && s.getClass().getName().startsWith("com.yammer.dropwizard.jersey.")) {
                singletonsToRemove.add(s);
            }
        }

        for (Object s : singletonsToRemove) {
            jrConfig.getSingletons().remove(s);
        }
		
		
        environment.addProvider(new RestErrorsHandler());
        
        // Allowing cross-origin    
		environment.addFilter(CORSFilter.class, "/*");
		
		//Security config
		environment.setSessionHandler(new SessionHandler());
		DropwizardAuthenticator authenticator = appContext.getBean(DropwizardAuthenticator.class);
				
    	CachingAuthenticator<SpringSecurityCredentials, BasicAuth> cachingAuthenticator = 
    			CachingAuthenticator.wrap(authenticator, 
    					CacheBuilderSpec.parse(configuration.getAuthenticationCachePolicy()));
    	appContext.getBeanFactory().registerSingleton(DropwizardAuthenticator.class.getName(), cachingAuthenticator);		 
		new SpringSecurityProvider(appContext).registerProvider(environment);
		
 	
	
	}
	
}

