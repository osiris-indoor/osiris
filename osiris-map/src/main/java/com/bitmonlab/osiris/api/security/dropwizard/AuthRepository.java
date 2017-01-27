package com.bitmonlab.osiris.api.security.dropwizard;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

import com.bitmonlab.osiris.api.core.map.exceptions.QueryException;
import com.bitmonlab.osiris.commons.model.security.BasicAuth;

@Named
public class AuthRepository {
	
	@Inject
	@Named("mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	private final static String collectionPrefixCredentials = "credentials_app_";

	public BasicAuth searchUserName(String appIdentifier, String userName) throws QueryException{
		
		Query query = createQuery("{username : '" + userName + "'}");
		
		BasicAuth p = mongoTemplate.findOne(query, BasicAuth.class,collectionPrefixCredentials+appIdentifier); ;
		
		if(p==null){
			throw new QueryException();
		}
		
		return p;
		
		
	}
	
     private Query createQuery(String query) throws QueryException{
		
		BasicQuery basicquery;
		
		try{	
			basicquery = new BasicQuery(query);
		}
		catch(Exception e) {
			throw new QueryException();
		}		
		
		return basicquery;		
	}


}
