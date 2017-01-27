package com.bitmonlab.osiris.api.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


/**
 * Class that configuration of the service..
 */
@Configuration
@ComponentScan({"com.bitmonlab.osiris.api.security.dropwizard"})
@ImportResource("classpath:osiris-map-context.xml")
public class SpringConfiguration{

}
