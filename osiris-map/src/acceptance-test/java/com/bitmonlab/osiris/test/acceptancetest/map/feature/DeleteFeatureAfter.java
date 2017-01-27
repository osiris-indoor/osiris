package com.bitmonlab.osiris.test.acceptancetest.map.feature;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.bitmonlab.osiris.restsender.Headers;
import com.bitmonlab.osiris.restsender.RestMethod;
import com.bitmonlab.osiris.restsender.RestRequestSender;

import cucumber.api.java.After;

public class DeleteFeatureAfter {
		
	
	private static List<String> idsDelete = new ArrayList<String>();
	private static List<String> idsApp = new ArrayList<String>();
	
		
	@Inject
	@Named("osirisMapRequestSender")
	private RestRequestSender sender;
	
    
		
	@After(value = "@deleteFeatures")		
	public void deleteDataBaseFeature(){
						
			for(int i=0; i<idsDelete.size();i++){	
				String idObject = idsDelete.get(i);
				String idApp = idsApp.get(i);
				
				sender.invoke(RestMethod.DELETE, "osiris/geolocation/territory/feature/" + idObject, new Headers("api_key", idApp), new Headers("Authorization", "Basic cm9vdDoxMjM0"));			
				//Assert.assertEquals("The response must be a  NO_CONTENT", Status.valueOf("NO_CONTENT").getStatusCode(),response.getStatus().getStatusCode());
										
			}
			
			idsDelete.clear();
			idsApp.clear();
							
	}
	
  
	public void addIDs(String idApp, String idFeature){
				
		idsDelete.add(idFeature);
		idsApp.add(idApp);
	}

}
