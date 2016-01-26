package com.bitmonlab.osiris.test.acceptancetest.map.commons;

import javax.ws.rs.core.Response.Status;

import org.junit.Assert;

import com.bitmonlab.osiris.restsender.SimpleClientResponse;

import cucumber.api.java.en.Then;

public class HttpResponse {
	
	
	private SimpleClientResponse response;
	
	@Then("^I receive a HTTP \"([^\"]*)\"$")
	public void I_receive_a_HTTP(String errorCode) throws Throwable {
	    // Express the Regexp above with the code you wish you had
		Assert.assertEquals("The response must be a " + errorCode, Status.valueOf(errorCode).getStatusCode(),response.getStatus().getStatusCode());
	}

	public void setResponse(SimpleClientResponse response) {
		this.response = response;
	}
	
	public SimpleClientResponse getResponse(){return response;}


}
