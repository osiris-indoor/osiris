package com.bitmonlab.batch.test.acceptancetest.imports.map;

import javax.inject.Inject;

import junit.framework.Assert;

import com.bitmonlab.batch.imports.map.MapImportMain;
import com.bitmonlab.batch.imports.map.exceptions.ParseMapException;


import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ImportMap {
	
	@Inject
	MapImportMain mapImportMain;
		
	private static Exception exceptionCapture;
	
	@When("^I invoke a MapImportMain with \"([^\"]*)\" and applicationIdentifier \"([^\"]*)\"$")
	public void I_invoke_a_MapImportMain_with_and_applicationIdentifier(String mapName, String appIdentifier) throws Throwable {
	    	
			
		try {		
									
			String[] pArgs  = {appIdentifier, "src/acceptance-test/resources/maps/" + mapName, "acceptance-test"};				
			mapImportMain.main(pArgs);
			
		}catch (Exception e){			
			exceptionCapture = e;
		}
			    
	}
	
	@Then("^I receive a ParseMapException$")
	public void I_receive_a_ParseMapException() throws Throwable {
	    // Express the Regexp above with the code you wish you had			    
	    Assert.assertEquals(exceptionCapture.getClass() , new ParseMapException().getClass() );
	}
	

}
