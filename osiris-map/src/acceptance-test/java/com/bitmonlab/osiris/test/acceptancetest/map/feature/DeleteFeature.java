package com.bitmonlab.osiris.test.acceptancetest.map.feature;

import javax.inject.Inject;
import javax.inject.Named;

import com.bitmonlab.osiris.test.acceptancetest.map.commons.HttpResponse;
import com.bitmonlab.osiris.test.acceptancetest.map.commons.SecurityCredentials;
import com.bitmonlab.osiris.api.core.map.transferobject.FeatureDTO;
import com.bitmonlab.osiris.restsender.ClientResponse;
import com.bitmonlab.osiris.restsender.Headers;
import com.bitmonlab.osiris.restsender.RestMethod;
import com.bitmonlab.osiris.restsender.RestRequestSender;
import com.bitmonlab.osiris.restsender.SimpleClientResponse;

import cucumber.api.java.en.When;

public class DeleteFeature {
	
	@Inject
	@Named("osirisMapRequestSender")
	private RestRequestSender sender;
			
	@Inject
	private PreconditionStoreFeature preconditionStoreFeature;
	
	@Inject
	private HttpResponse httpResponse;
	
	@Inject
	private SecurityCredentials securityCredentials;
		
		
	@When("^I invoke a DELETE to \"([^\"]*)\" with idFeature$")
	public void I_invoke_a_DELETE_to_with_idGeoPoint(String url) throws Throwable {
	    // Express the Regexp above with the code you wish you had
		
		String appId = "1";
		securityCredentials.createCredential(appId, "root", "1234");
		
		ClientResponse<FeatureDTO>  responsePrecondition = preconditionStoreFeature.getResponse();
		
		FeatureDTO featureDTOResponse = responsePrecondition.getEntity();
		String id=featureDTOResponse.getId();
		
		SimpleClientResponse response = sender.invoke(RestMethod.DELETE, url + "/" + id, new Headers("api_key", appId) , new Headers("Authorization", "Basic cm9vdDoxMjM0"));
		
		httpResponse.setResponse(response);
	}

	@When("^I invoke a DELETE to \"([^\"]*)\"$")
	public void I_invoke_a_DELETE_to(String url) throws Throwable {
	    // Express the Regexp above with the code you wish you had
		
		String appId = "1";
		securityCredentials.createCredential(appId, "root", "1234");
				
		SimpleClientResponse response = sender.invoke(RestMethod.DELETE, url, new Headers("api_key", "1") , new Headers("Authorization", "Basic cm9vdDoxMjM0"));
		
		httpResponse.setResponse(response);

	}

	

}
