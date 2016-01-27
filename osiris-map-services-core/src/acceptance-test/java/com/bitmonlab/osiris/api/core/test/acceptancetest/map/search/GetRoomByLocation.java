package com.bitmonlab.osiris.api.core.test.acceptancetest.map.search;

import javax.inject.Inject;
import javax.inject.Named;

import junit.framework.Assert;

import com.bitmonlab.osiris.api.core.map.exceptions.RoomNotFoundException;
import com.bitmonlab.osiris.api.core.map.managers.api.SearchManager;
import com.bitmonlab.osiris.api.core.map.transferobject.RoomDTO;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;
import com.bitmonlab.osiris.core.assembler.Assembler;
import com.bitmonlab.osiris.core.assembler.AssemblyException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GetRoomByLocation {
	
	@Inject
	private SearchManager searchManager;
	
	@Inject 
	@Named("RoomAssembler")
	private Assembler<RoomDTO, Feature> roomAssembler;
	
	private RoomDTO response;
	
	public static Exception exceptionCapture;
	
	@Given("^I have a map with a Room and appId \"([^\"]*)\"$")
	public void I_have_a_map_with_a_Room_and_appId(String appId) throws Throwable {
	    // Express the Regexp above with the code you wish you had
		Runtime.getRuntime().exec("mongorestore --db osirisGeolocation --collection map_app_"+appId+" src/acceptance-test/resources/scripts/mapRoom_app_1.bson");
	}

	@When("^I invoke a GET to getRoomByLocation with appId \"([^\"]*)\", longitude \"([^\"]*)\", latitude \"([^\"]*)\" and floor (\\d+)$")
	public void I_invoke_a_GET_to_getRoomByLocation_with_appId_longitude_latitude_and_floor(String appId, String sLongitude, String sLatitude, int floor) throws RoomNotFoundException, AssemblyException{
	    // Express the Regexp above with the code you wish you had
		
		try {	
			
			Double longitude = Double.parseDouble(sLongitude);
			Double latitude = Double.parseDouble(sLatitude);
			
			Feature room=searchManager.getRoomByLocation(appId, longitude, latitude, floor);			
			response=roomAssembler.createDataTransferObject(room);
			
		}catch (Exception e){			
				exceptionCapture = e;
		}
		
	}

	@Then("^returned room is \"([^\"]*)\"$")
	public void returned_room_is(String room) {
	    // Express the Regexp above with the code you wish you had
	    RoomDTO roomDTO=response;
	    String roonName=roomDTO.getRoomName();
	    Assert.assertEquals("Name of room must be equals", room, roonName);
	}
	
	@When("^I check that (\\d+) rooms are returned$")
	public void I_check_that_rooms_are_returned(int numRooms) throws Throwable {
		
		Assert.assertEquals("Must return " + numRooms + " rooms", response, numRooms);
		
		 
	}

	@Then("^I receive a RoomNotFoundException$")
	public void I_receive_a_RoomNotFoundException() throws Throwable {
		 Assert.assertEquals(exceptionCapture.getClass() , new RoomNotFoundException().getClass() );
	}
	
	
	

}
