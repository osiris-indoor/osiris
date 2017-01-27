package com.bitmonlab.osiris.api.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;


public class OsirisConfiguration extends Configuration  {
	
	public static class CacheConfiguration {

		@NotEmpty
		@JsonProperty
		private String authenticationCachePolicy;

		public String getAuthenticationCachePolicy() {
			return authenticationCachePolicy;
		}
		
	}
	
	@Valid
	@NotNull
	private CacheConfiguration cache;
	
	public CacheConfiguration getCache() {
		return cache;
	}
	
	public String getAuthenticationCachePolicy() {
		return cache.getAuthenticationCachePolicy();
	}

}
