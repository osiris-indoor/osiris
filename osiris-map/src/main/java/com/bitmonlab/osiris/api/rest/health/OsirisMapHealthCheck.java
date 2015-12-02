package com.bitmonlab.osiris.api.rest.health;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.yammer.metrics.core.HealthCheck;

@Component
public class OsirisMapHealthCheck extends HealthCheck{
	
	public OsirisMapHealthCheck() {
        super("OsirisMapHealthCheck");        
    }

    @Override
    protected Result check() throws Exception {
    	
    	ClassLoader loader = Thread.currentThread().getContextClassLoader();           
    	InputStream stream = loader.getResourceAsStream("version.properties");
    	Properties prop = new Properties();
    	prop.load(stream);
    	
        return Result.healthy(prop.getProperty("version") + " " + prop.getProperty("build.timestamp"));
    }
}