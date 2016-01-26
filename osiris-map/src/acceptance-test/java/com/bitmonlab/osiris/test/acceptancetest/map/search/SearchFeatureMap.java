package com.bitmonlab.osiris.test.acceptancetest.map.search;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import junit.framework.Assert;

import com.bitmonlab.osiris.api.core.map.transferobject.FeatureDTO;
import com.bitmonlab.osiris.test.acceptancetest.map.commons.HttpResponse;
import com.bitmonlab.osiris.restsender.ClientResponse;
import com.bitmonlab.osiris.restsender.Headers;
import com.bitmonlab.osiris.restsender.RestMethod;
import com.bitmonlab.osiris.restsender.RestRequestSender;
import com.sun.jersey.api.client.GenericType;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class SearchFeatureMap {
	
	@Inject
	@Named("osirisMapRequestSender")
	private RestRequestSender sender;
		
	@Inject
	private HttpResponse httpResponse;
	
	private ClientResponse<Collection<FeatureDTO>> response;
	
	@Given("^I have a map with appId \"([^\"]*)\"$")
	public void I_a_map_with_appId(String appId) throws IOException{
	    // Express the Regexp above with the code you wish you had
		Runtime.getRuntime().exec("mongorestore --db osirisGeolocation --collection map_app_"+appId+" src/acceptance-test/resources/scripts/map_app_1.bson");
	}
	
	
	@When("^I invoke a POST to \"([^\"]*)\" to feature and query \"([^\"]*)\" and applicationIdentifier \"([^\"]*)\"$")
	public void I_invoke_a_POST_to_to_feature_and_query_and_applicationIdentifier(String url, String query, String appID){

		response=sender.invoke(RestMethod.POST, url+"?layer=FEATURES", query, new GenericType<Collection<FeatureDTO>>(){}, new Headers("api_key", appID));		
		
		httpResponse.setResponse(response);
	}
	
	@When("^I invoke a POST to \"([^\"]*)\" to map and query \"([^\"]*)\" and applicationIdentifier \"([^\"]*)\"$")
	public void I_invoke_a_POST_to_to_map_and_query_and_applicationIdentifier(String url, String query, String appID){

		response=sender.invoke(RestMethod.POST, url+"?layer=MAP", query, new GenericType<Collection<FeatureDTO>>(){}, new Headers("api_key", appID));		
		
		httpResponse.setResponse(response);
	}
	
	@When("^I invoke a POST to \"([^\"]*)\" to all and query \"([^\"]*)\" and applicationIdentifier \"([^\"]*)\"$")
	public void I_invoke_a_POST_to_to_all_and_query_and_applicationIdentifier(String url, String query, String appID) throws Throwable {
		
		response=sender.invoke(RestMethod.POST, url+"?layer=ALL", query, new GenericType<Collection<FeatureDTO>>(){}, new Headers("api_key", appID));		
		
		httpResponse.setResponse(response);
	}

	@Then("^I check that (\\d+) features are returned$")
	public void I_check_that_geopoints_are_returned(int numFeatures) throws Throwable {
	    // Express the Regexp above with the code you wish you had
		Collection<FeatureDTO> collectionFeaturesDTO = response.getEntity();
		
		Assert.assertEquals("Must return " + numFeatures + " features", collectionFeaturesDTO.size(), numFeatures);
	 
	}

}