package com.bitmonlab.osiris.test.acceptancetest.map.commons;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

import com.bitmonlab.osiris.api.core.map.exceptions.QueryException;

import cucumber.api.java.After;


public class DeleteSecurityCredentialsAfter {
	
	private static List<String> userNameDelete = new ArrayList<String>();
	private static List<String> idsApp = new ArrayList<String>();
	
	@Inject 	
	private MongoTemplate mongoTemplate;

	private final static String suffixCollectionCredential = "credentials_app_";

		
	@After(value = "@deleteSecurityCredentials")		
	public void deleteSecurityCredentials() throws QueryException{
		
		for(int i=0; i<userNameDelete.size();i++){	
			String _id = userNameDelete.get(i);
			String idApp = idsApp.get(i);
			
			Query query = createQuery("{_id : '" + _id + "'}");
						
			mongoTemplate.remove(query, suffixCollectionCredential + idApp);
									
		}
		
		userNameDelete.clear();
		idsApp.clear();
		
		
	}
	
	public static void addIDs(String idApp, String _id){
		
		userNameDelete.add(_id);
		idsApp.add(idApp);
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
