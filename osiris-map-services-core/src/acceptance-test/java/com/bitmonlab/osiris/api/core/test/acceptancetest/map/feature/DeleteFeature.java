package com.bitmonlab.osiris.api.core.test.acceptancetest.map.feature;

import javax.inject.Inject;

import junit.framework.Assert;

import com.bitmonlab.osiris.api.core.map.exceptions.FeatureNotExistException;
import com.bitmonlab.osiris.api.core.map.managers.api.FeatureManager;
import com.bitmonlab.osiris.api.core.map.transferobject.FeatureDTO;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DeleteFeature {
	
	@Inject
	private FeatureManager featureManager;
			
	@Inject
	private PreconditionStoreFeature preconditionStoreFeature;
	
	public static Exception exceptionCapture;
	
		
	@When("^I invoke a DELETE to deleteFeature$")
	public void I_invoke_a_DELETE_to_deleteFeature() throws Throwable {		
		
		FeatureDTO  responsePrecondition = preconditionStoreFeature.getResponse();
		
		String idFeature = responsePrecondition.getId();		
								
		preconditionStoreFeature.getDeleteAfter().deleteDataBaseFeature();
				
	}
	
	@When("^I invoke a DELETE to deleteFeature with idFeature \"([^\"]*)\"$")
	public void I_invoke_a_DELETE_to_deleteFeature_with_idFeature(String idFeature) throws Throwable {
		
		try {
			
			featureManager.deleteFeature("1", idFeature);
			
		}catch (Exception e){			
			exceptionCapture = e;
		}	
	}

	@Then("^I receive a FeatureNotExistException$")
	public void I_receive_a_FeatureNotExistException() throws Throwable {
	    // Express the Regexp above with the code you wish you had
				
		Assert.assertEquals(exceptionCapture.getClass() , new FeatureNotExistException().getClass() );

	}

	

}
