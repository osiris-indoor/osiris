package com.bitmonlab.osiris.api.core.test.acceptancetest.map.feature;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.bitmonlab.osiris.api.core.map.exceptions.FeatureNotExistException;
import com.bitmonlab.osiris.api.core.map.managers.api.FeatureManager;

import cucumber.api.java.After;

public class DeleteFeatureAfter {
		
	
	@Inject
	private FeatureManager featureManager;
	
	private static List<String> idsDelete = new ArrayList<String>();
	private static List<String> idsApp = new ArrayList<String>();
	
				
	@After(value = "@deleteFeatures")		
	public void deleteDataBaseFeature() throws FeatureNotExistException{
						
			for(int i=0; i<idsDelete.size();i++){	
				String idObject = idsDelete.get(i);
				String idApp = idsApp.get(i);
								
				featureManager.deleteFeature(idApp, idObject);
				
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
