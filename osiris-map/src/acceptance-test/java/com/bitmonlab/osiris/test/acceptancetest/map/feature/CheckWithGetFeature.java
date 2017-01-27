package com.bitmonlab.osiris.test.acceptancetest.map.feature;

import java.util.Collection;
import java.util.Iterator;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;


import com.bitmonlab.osiris.api.core.map.transferobject.FeatureDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.LineStringDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.PointDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.PolygonDTO;
import com.bitmonlab.osiris.restsender.ClientResponse;
import com.bitmonlab.osiris.restsender.Headers;
import com.bitmonlab.osiris.restsender.RestMethod;
import com.bitmonlab.osiris.restsender.RestRequestSender;

import cucumber.api.java.en.Then;

public class CheckWithGetFeature {
		
	@Inject
	@Named("osirisMapRequestSender")
	private RestRequestSender sender;
	
	private ClientResponse<FeatureDTO> response;
	
	@Then("^I invoke a GET to \"([^\"]*)\" with Id to check and receive a \"([^\"]*)\" with type point$")
	public void I_invoke_a_GET_to_with_Id_to_check_and_receive_a_with_type_point(String url, String responseExpected) throws Throwable {
	    
		FeatureDTO featureDTO = response.getEntity();		
		
		response= sender.invoke(RestMethod.GET, url + "/" + featureDTO.getId(), FeatureDTO.class, new Headers("api_key", "1"), new Headers("Authorization", "Basic cm9vdDoxMjM0"));
				
		Assert.assertEquals("The response must be a " + responseExpected, Status.valueOf(responseExpected).getStatusCode(),response.getStatus().getStatusCode());
		
		if(Status.valueOf(responseExpected).getStatusCode()==200){
			FeatureDTO featureDTOResponse = response.getEntity();
			
			PointDTO pointDTO=(PointDTO)featureDTO.getGeometryDTO();
			Double latitude=pointDTO.getLatitude();
			Double longitude=pointDTO.getLongitude();
			
			PointDTO pointDTOResponse=(PointDTO)featureDTOResponse.getGeometryDTO();
			Double latitudeResponse=pointDTOResponse.getLatitude();
			Double longitudeResponse=pointDTOResponse.getLongitude();
			
			Assert.assertEquals("Ids must be the same", featureDTO.getId(), featureDTOResponse.getId());
			Assert.assertEquals("Properties must be the same", featureDTO.getProperties(), featureDTOResponse.getProperties());
			Assert.assertEquals("Latitudes must be the same", latitude, latitudeResponse);
			Assert.assertEquals("Longitudes must be the same", longitude, longitudeResponse);
		}
		
	}
	
	@Then("^I invoke a GET to \"([^\"]*)\" with Id to check and receive a \"([^\"]*)\" with type lineString$")
	public void I_invoke_a_GET_to_with_Id_to_check_and_receive_a_with_type_lineString(String url, String responseExpected) throws Throwable {

		FeatureDTO featureDTO = response.getEntity();		
		
		response= sender.invoke(RestMethod.GET, url + "/" + featureDTO.getId(), FeatureDTO.class, new Headers("api_key", "1"), new Headers("Authorization", "Basic cm9vdDoxMjM0"));
				
		Assert.assertEquals("The response must be a " + responseExpected, Status.valueOf(responseExpected).getStatusCode(),response.getStatus().getStatusCode());
		
		if(Status.valueOf(responseExpected).getStatusCode()==200){
			FeatureDTO featureDTOResponse = response.getEntity();
			
			LineStringDTO lineStringDTO=(LineStringDTO)featureDTO.getGeometryDTO();
			Collection<PointDTO> collectionPointDTO=lineStringDTO.getCollectionPointDTO();
			Iterator<PointDTO> iteratorPointDTO=collectionPointDTO.iterator();
			PointDTO pointDTO=iteratorPointDTO.next();		
			Double latitude1=pointDTO.getLatitude();
			Double longitude1=pointDTO.getLongitude();
			pointDTO=iteratorPointDTO.next();		
			Double latitude2=pointDTO.getLatitude();
			Double longitude2=pointDTO.getLongitude();
			
			LineStringDTO lineStringDTOResponse=(LineStringDTO)featureDTOResponse.getGeometryDTO();
			Collection<PointDTO> collectionPointDTOResponse=lineStringDTOResponse.getCollectionPointDTO();
			Iterator<PointDTO> iteratorPointDTOResponse=collectionPointDTOResponse.iterator();
			PointDTO pointDTOResponse=iteratorPointDTOResponse.next();	
			Double latitudeResponse1=pointDTOResponse.getLatitude();
			Double longitudeResponse1=pointDTOResponse.getLongitude();
			pointDTOResponse=iteratorPointDTOResponse.next();	
			Double latitudeResponse2=pointDTOResponse.getLatitude();
			Double longitudeResponse2=pointDTOResponse.getLongitude();
			
			Assert.assertEquals("Ids must be the same", featureDTO.getId(), featureDTOResponse.getId());
			Assert.assertEquals("Properties must be the same", featureDTO.getProperties(), featureDTOResponse.getProperties());
			Assert.assertEquals("Latitudes1 must be the same", latitude1, latitudeResponse1);
			Assert.assertEquals("Longitudes1 must be the same", longitude1, longitudeResponse1);
			Assert.assertEquals("Latitudes2 must be the same", latitude2, latitudeResponse2);
			Assert.assertEquals("Longitudes2 must be the same", longitude2, longitudeResponse2);
		}
		
	}
	
	@Then("^I invoke a GET to \"([^\"]*)\" with Id to check and receive a \"([^\"]*)\" with type polygon$")
	public void I_invoke_a_GET_to_with_Id_to_check_and_receive_a_with_type_polygon(String url, String responseExpected) throws Throwable {

		FeatureDTO featureDTO = response.getEntity();		
		
		response= sender.invoke(RestMethod.GET, url + "/" + featureDTO.getId(), FeatureDTO.class, new Headers("api_key", "1"), new Headers("Authorization", "Basic cm9vdDoxMjM0"));
				
		Assert.assertEquals("The response must be a " + responseExpected, Status.valueOf(responseExpected).getStatusCode(),response.getStatus().getStatusCode());
		
		if(Status.valueOf(responseExpected).getStatusCode()==200){
			FeatureDTO featureDTOResponse = response.getEntity();
			
			PolygonDTO polygonDTO=(PolygonDTO)featureDTO.getGeometryDTO();
			Collection<LineStringDTO> collectionLineStringDTO=polygonDTO.getCollectionLineStringDTO();
			Iterator<LineStringDTO> iteratorLineStringDTO=collectionLineStringDTO.iterator();
			
			LineStringDTO lineStringDTO=iteratorLineStringDTO.next();	
			Collection<PointDTO> collectionPointDTO=lineStringDTO.getCollectionPointDTO();
			Iterator<PointDTO> iteratorPointDTO=collectionPointDTO.iterator();
			PointDTO pointDTO=iteratorPointDTO.next();		
			Double latitude1=pointDTO.getLatitude();
			Double longitude1=pointDTO.getLongitude();
			pointDTO=iteratorPointDTO.next();		
			Double latitude2=pointDTO.getLatitude();
			Double longitude2=pointDTO.getLongitude();
			
			lineStringDTO=iteratorLineStringDTO.next();	
			collectionPointDTO=lineStringDTO.getCollectionPointDTO();
			iteratorPointDTO=collectionPointDTO.iterator();
			pointDTO=iteratorPointDTO.next();		
			Double latitude3=pointDTO.getLatitude();
			Double longitude3=pointDTO.getLongitude();
			pointDTO=iteratorPointDTO.next();		
			Double latitude4=pointDTO.getLatitude();
			Double longitude4=pointDTO.getLongitude();			
			
			PolygonDTO polygonDTOResponse=(PolygonDTO)featureDTOResponse.getGeometryDTO();
			Collection<LineStringDTO> collectionLineStringDTOResponse=polygonDTOResponse.getCollectionLineStringDTO();
			Iterator<LineStringDTO> iteratorLineStringDTOResponse=collectionLineStringDTOResponse.iterator();
			
			LineStringDTO lineStringDTOResponse=iteratorLineStringDTOResponse.next();	
			Collection<PointDTO> collectionPointDTOResponse=lineStringDTOResponse.getCollectionPointDTO();
			Iterator<PointDTO> iteratorPointDTOResponse=collectionPointDTOResponse.iterator();
			PointDTO pointDTOResponse=iteratorPointDTOResponse.next();		
			Double latitudeResponse1=pointDTOResponse.getLatitude();
			Double longitudeResponse1=pointDTOResponse.getLongitude();
			pointDTOResponse=iteratorPointDTOResponse.next();		
			Double latitudeResponse2=pointDTOResponse.getLatitude();
			Double longitudeResponse2=pointDTOResponse.getLongitude();
			
			lineStringDTOResponse=iteratorLineStringDTOResponse.next();	
			collectionPointDTOResponse=lineStringDTOResponse.getCollectionPointDTO();
			iteratorPointDTOResponse=collectionPointDTOResponse.iterator();
			pointDTOResponse=iteratorPointDTOResponse.next();		
			Double latitudeResponse3=pointDTOResponse.getLatitude();
			Double longitudeResponse3=pointDTOResponse.getLongitude();
			pointDTOResponse=iteratorPointDTOResponse.next();		
			Double latitudeResponse4=pointDTOResponse.getLatitude();
			Double longitudeResponse4=pointDTOResponse.getLongitude();
						
			Assert.assertEquals("Ids must be the same", featureDTO.getId(), featureDTOResponse.getId());
			Assert.assertEquals("Properties must be the same", featureDTO.getProperties(), featureDTOResponse.getProperties());
			Assert.assertEquals("Latitudes1 must be the same", latitude1, latitudeResponse1);
			Assert.assertEquals("Longitudes1 must be the same", longitude1, longitudeResponse1);
			Assert.assertEquals("Latitudes2 must be the same", latitude2, latitudeResponse2);
			Assert.assertEquals("Longitudes2 must be the same", longitude2, longitudeResponse2);
			Assert.assertEquals("Latitudes3 must be the same", latitude3, latitudeResponse3);
			Assert.assertEquals("Longitudes3 must be the same", longitude3, longitudeResponse3);
			Assert.assertEquals("Latitudes4 must be the same", latitude4, latitudeResponse4);
			Assert.assertEquals("Longitudes4 must be the same", longitude4, longitudeResponse4);
		}
	}
	
	
	
	
	public void setResponse(ClientResponse<FeatureDTO> response) {
		this.response = response;
	}


}
