package com.bitmonlab.osiris.test.acceptancetest.map.search;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import junit.framework.Assert;

import com.bitmonlab.osiris.test.acceptancetest.map.commons.HttpResponse;
import com.bitmonlab.osiris.api.core.map.transferobject.RoomDTO;
import com.bitmonlab.osiris.restsender.ClientResponse;
import com.bitmonlab.osiris.restsender.Headers;
import com.bitmonlab.osiris.restsender.RestMethod;
import com.bitmonlab.osiris.restsender.RestRequestSender;
import com.sun.jersey.api.client.GenericType;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GetRoomByLocation {
	
	@Inject
	private HttpResponse httpResponse;	
	
	@Inject
	@Named("aideMapRequestSender")
	private RestRequestSender sender;
	
	private ClientResponse<RoomDTO> response;
	
	@Given("^I have a map with appId \"([^\"]*)\"$")
	public void I_a_map_with_appId(String appId) throws IOException, InterruptedException{
	    // Express the Regexp above with the code you wish you had
		Runtime.getRuntime().exec("mongorestore --db aideGeolocation --collection map_app_"+appId+" src/acceptance-test/resources/scripts/map_app_1.bson");
		
		//Time to create 2D index in mongo
		Thread.sleep(500);
	}

	@When("^I invoke a GET to \"([^\"]*)\" with appId \"([^\"]*)\", longitude (.+), latitude (.+) and floor (\\d+)$")
	public void I_invoke_a_GET_to_with_appId_longitude_latitude_and_floor(String url, String appId, double longitude, double latitude, int floor){
	    // Express the Regexp above with the code you wish you had
		String queryParams="?longitude="+longitude+"&latitude="+latitude+"&floor="+floor;
		response= sender.invoke(RestMethod.GET, url+queryParams, new GenericType<RoomDTO>(){}, new Headers("api_key", appId));
							
		httpResponse.setResponse(response);
	}

	@Then("^returned room is \"([^\"]*)\"$")
	public void returned_room_is(String room) {
	    // Express the Regexp above with the code you wish you had
	    RoomDTO roomDTO=response.getEntity();
	    String roonName=roomDTO.getRoomName();
	    Assert.assertEquals("Name of room must be equals", room, roonName);
	}
	
	

}
