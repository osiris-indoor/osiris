package com.bitmonlab.osiris.api.core.test.acceptancetest.map.search;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import junit.framework.Assert;

import com.bitmonlab.osiris.core.assembler.Assembler;
import com.bitmonlab.osiris.core.assembler.AssemblyException;
import com.bitmonlab.osiris.api.core.map.exceptions.QueryException;
import com.bitmonlab.osiris.api.core.map.managers.api.SearchManager;
import com.bitmonlab.osiris.api.core.map.transferobject.FeatureDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.LayerDTO;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class SearchFeatureMap {
	
			
	@Inject
	private SearchManager searchManager;
	
	@Inject 
	@Named("FeatureAssembler")
	private Assembler<FeatureDTO, Feature> featureAssembler;
	
	private Collection<FeatureDTO> collectionFeaturesDTO;
	
	public static Exception exceptionCapture;
	
	@Given("^I have a map with appId \"([^\"]*)\"$")
	public void I_a_map_with_appId(String appId) throws IOException{
	    // Express the Regexp above with the code you wish you had
		Runtime.getRuntime().exec("mongorestore --db osirisGeolocation --collection map_app_"+appId+" src/acceptance-test/resources/scripts/map_app_1.bson");
	}
	
	
	@When("^I invoke a getFeatureByQuery to feature and query \"([^\"]*)\" and applicationIdentifier \"([^\"]*)\"$")
	public void I_invoke_a_getFeatureByQuery_to_feature_and_query_and_applicationIdentifier(String query, String appID) throws QueryException, AssemblyException{
		
		Collection<Feature>  features = null;
				
		features =  searchManager.getFeaturesByQuery(appID, query, LayerDTO.FEATURES, 0, 20);				
		
		collectionFeaturesDTO=featureAssembler.createDataTransferObjects(features);
		
	}
	
	@When("^I invoke a getFeatureByQuery to map and query \"([^\"]*)\" and applicationIdentifier \"([^\"]*)\"$")
	public void I_invoke_a_getFeatureByQuery_to_map_and_query_and_applicationIdentifier(String query, String appID) throws QueryException, AssemblyException{

		Collection<Feature>  features = null;
		
		features =  searchManager.getFeaturesByQuery(appID, query, LayerDTO.MAP, 0, 20);				
		
		collectionFeaturesDTO=featureAssembler.createDataTransferObjects(features);
			
	}
	
	@When("^I invoke a getFeatureByQuery to all and query \"([^\"]*)\" and applicationIdentifier \"([^\"]*)\"$")
	public void I_invoke_a_getFeatureByQuery_to_all_and_query_and_applicationIdentifier(String query, String appID) throws Throwable {
		
		try {	
			Collection<Feature>  features = null;
		
			features =  searchManager.getFeaturesByQuery(appID, query, LayerDTO.ALL, 0, 20);				
		
			collectionFeaturesDTO=featureAssembler.createDataTransferObjects(features);
			
		}catch (Exception e){			
			exceptionCapture = e;
		}

	}

	@Then("^I check that (\\d+) features are returned$")
	public void I_check_that_geopoints_are_returned(int numFeatures) throws Throwable {
	    // Express the Regexp above with the code you wish you had
				
		Assert.assertEquals("Must return " + numFeatures + " features", collectionFeaturesDTO.size(), numFeatures);
	 
	}
	
	@Then("^I receive a QueryException$")
	public void I_receive_a_QueryException() throws Throwable {
	    Assert.assertEquals(exceptionCapture.getClass() , new QueryException().getClass() );
	}

}