package com.bitmonlab.osiris.api.configuration;

import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.ext.ExceptionMapper;

import com.bitmonlab.osiris.core.errorhandler.RestErrorsHandler;
import com.github.pnayak.dropwizard.spring.AutoWiredService;
import com.sun.jersey.api.core.ResourceConfig;


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

