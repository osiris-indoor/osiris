package com.bitmonlab.osiris.test.acceptancetest.map.feature;
import javax.inject.Inject;
import javax.inject.Named;

import com.bitmonlab.osiris.test.acceptancetest.map.commons.HttpResponse;
import com.bitmonlab.osiris.api.map.transferobject.FeatureDTO;
import com.bitmonlab.restsender.ClientResponse;
import com.bitmonlab.restsender.Headers;
import com.bitmonlab.restsender.RestMethod;
import com.bitmonlab.restsender.RestRequestSender;
import com.sun.jersey.api.client.GenericType;

import cucumber.api.java.en.When;
public class GetFeatureByID {
	
	@Inject
	private PreconditionStoreFeature preconditionStoreFeature;
	
	@Inject
	@Named("osirisMapRequestSender")
	private RestRequestSender sender;
	

	private ClientResponse<FeatureDTO> response;
	
	@Inject
	private HttpResponse httpResponse;
	
	@When("^I invoke a GET to \"([^\"]*)\" with id$")
	public void I_invoke_a_GET_to_with_id(String url) throws Throwable {
	    // Express the Regexp above with the code you wish you had
		
		ClientResponse<FeatureDTO> responsePrecondition =  preconditionStoreFeature.getResponse();
		
		FeatureDTO featureDTO = responsePrecondition.getEntity();
		 
		response= sender.invoke(RestMethod.GET, url + "/" + featureDTO.getId(), FeatureDTO.class, new Headers("api_key", "1"));	
		
		httpResponse.setResponse(response);
	    
	}

	@When("^I invoke a GET to \"([^\"]*)\"$")
	public void I_invoke_a_GET_to(String url) throws Throwable {
	    // Express the Regexp above with the code you wish you had
		response= sender.invoke(RestMethod.GET, url , new GenericType<FeatureDTO>(){}, new Headers("api_key", "1"));	
		
		httpResponse.setResponse(response);
	}


}
