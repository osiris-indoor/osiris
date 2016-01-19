package com.bitmonlab.osiris.test.acceptancetest.map.metadata;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.mongodb.core.MongoTemplate;

import cucumber.api.java.After;

public class DeleteMetaDataAfter {
		
	
	private final static String collectionMetaData="Metadata";
	
	@Inject 
	@Named("mongoTemplate")
	private MongoTemplate mongoTemplate;    
		
	@After(value = "@deleteMetadata")		
	public void deleteDataBaseMetaData(){
		mongoTemplate.dropCollection(collectionMetaData);
	}

}
