package com.bitmonlab.osiris.api.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.ext.ExceptionMapper;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.bitmonlab.osiris.core.errorhandler.RestErrorsHandler;
import com.github.pnayak.dropwizard.spring.AutoWiredService;
import com.sun.jersey.api.core.ResourceConfig;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.FilterBuilder;


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
		
		 // Allowing cross-origin
        FilterBuilder filter = environment.addFilter(CrossOriginFilter.class, "crossOriginFilter");
        filter.setInitParam("allowedOrigins", "*");
        filter.setInitParam("allowedHeaders", "api_key,X-Requested-With,Content-Type,Accept,Origin,access-control-allow-origin,Access-Control-Allow-Origin,Authorization,Access-Control-Request-Method");
        filter.setInitParam("allowedMethods", "GET,PUT,POST,DELETE,HEAD");
        filter.addUrlPattern("/*");
		
					
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
		
	}
	
}

