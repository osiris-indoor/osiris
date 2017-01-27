package com.bitmonlab.osiris.test.acceptancetest.map.commons;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.bitmonlab.osiris.commons.model.security.BasicAuth;

@Named
public class SecurityCredentials {
	
	@Inject 	
	private MongoTemplate mongoTemplate;
	
		
	private final static String suffixCollectionCredential = "credentials_app_";
	
	public void createCredential(String appId, String username, String password){
		
		BasicAuth principal = new BasicAuth();
		
		principal.setUsername(username);
		principal.setPassword(password);
		
		mongoTemplate.save(principal, suffixCollectionCredential + appId);
		
		
		DeleteSecurityCredentialsAfter.addIDs(appId, username);
	}
	
	

}
