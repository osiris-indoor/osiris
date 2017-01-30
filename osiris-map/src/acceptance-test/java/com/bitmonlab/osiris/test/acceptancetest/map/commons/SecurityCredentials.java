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
	
	public void createCredential(String appId, String _id, String password){
		
		BasicAuth principal = new BasicAuth();
		
		principal.setUsername(_id);
		principal.setPassword(password);
		
		mongoTemplate.save(principal, suffixCollectionCredential + appId);
		
		
		DeleteSecurityCredentialsAfter.addIDs(appId, _id);
	}
	
	

}
