package com.bitmonlab.osiris.test.acceptancetest.map.feature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;

import com.bitmonlab.osiris.api.core.map.transferobject.FeatureDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.GeometryDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.LineStringDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.PointDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.PolygonDTO;
import com.bitmonlab.osiris.restsender.ClientResponse;
import com.bitmonlab.osiris.restsender.Headers;
import com.bitmonlab.osiris.restsender.RestMethod;
import com.bitmonlab.osiris.restsender.RestRequestSender;
import com.sun.jersey.api.client.GenericType;

import cucumber.api.java.en.Given;
public class PreconditionStoreFeature {
	
	private ClientResponse<FeatureDTO> response;
	
	@Inject
	private CheckWithGetFeature checkWithGet;
	
	@Inject
	@Named("osirisMapRequestSender")
	private RestRequestSender sender;
	
	@Inject
	private DeleteFeatureAfter deleteAfter;
	
	public ClientResponse<FeatureDTO> getResponse() {
		return response;
	}

	@Given("^I store a feature of type point with latitude (\\d+), longitude (\\d+), altitude (\\d+) and jsonObject with key \"([^\"]*)\" and value \"([^\"]*)\" and applicationIdentifier \"([^\"]*)\"$")
	public void I_store_a_feature_of_type_point_with_latitude_longitude_altitude_and_jsonObject_with_key_and_value_and_applicationIdentifier(Double latitude, Double longitude, Double altitude, String key, String value, String applicationID) throws Throwable {
	    
		Map<String,String> properties = new HashMap<String, String>();
		properties.put(key, value);	
		
		PointDTO pointDTO=createGeometryPoint(latitude,longitude);
		
		FeatureDTO featureDTO=createFeatureDTO(properties, pointDTO);
	
		response=sender.invoke(RestMethod.POST, "osiris/geolocation/territory/feature", featureDTO, new GenericType<FeatureDTO>(){}, new Headers("api_key", applicationID));
		
		Assert.assertEquals("The response must be an OK", Status.valueOf("OK").getStatusCode(),response.getStatus().getStatusCode());
		
		checkWithGet.setResponse(response);
		
		//Check the response of POST to verify I need delete anything 
		if(response.getEntity()!=null){
			FeatureDTO featureDTOResponse = response.getEntity();
			deleteAfter.addIDs(applicationID,featureDTOResponse.getId());
		}
		
	}
	
	@Given("^I store a feature of type lineString with latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+) and jsonObject with key \"([^\"]*)\" and value \"([^\"]*)\" and applicationIdentifier \"([^\"]*)\"$")
	public void I_store_a_feature_of_type_point_with_latitude_longitude_altitude_latitude_longitude_altitude_and_jsonObject_with_key_and_value_and_applicationIdentifier(Double latitude1, Double longitude1, Double altitude1, Double latitude2, Double longitude2, Double altitude2, String key, String value, String applicationID) throws Throwable {
	    
		Map<String,String> properties = new HashMap<String, String>();
		properties.put(key, value);	
		
		PointDTO pointDTO1=createGeometryPoint(latitude1,longitude1);
		PointDTO pointDTO2=createGeometryPoint(latitude2,longitude2);
		
		Collection<PointDTO> collectionPointDTOs=new ArrayList<PointDTO>();
		collectionPointDTOs.add(pointDTO1);
		collectionPointDTOs.add(pointDTO2);
				
		LineStringDTO lineStringDTO=createGeometryLineString(collectionPointDTOs);
		
		FeatureDTO featureDTO=createFeatureDTO(properties, lineStringDTO);
	
		response=sender.invoke(RestMethod.POST, "osiris/geolocation/territory/feature", featureDTO, new GenericType<FeatureDTO>(){}, new Headers("api_key", applicationID));
		
		Assert.assertEquals("The response must be an OK", Status.valueOf("OK").getStatusCode(),response.getStatus().getStatusCode());
		
		checkWithGet.setResponse(response);
		
		//Check the response of POST to verify I need delete anything 
		if(response.getEntity()!=null){
			FeatureDTO featureDTOResponse = response.getEntity();
			deleteAfter.addIDs(applicationID,featureDTOResponse.getId());
		}
		
	}
	
	@Given("^I store a feature of type polygon with latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+) and jsonObject with key \"([^\"]*)\" and value \"([^\"]*)\" and applicationIdentifier \"([^\"]*)\"$")
	public void I_store_a_feature_of_type_polygon_with_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_and_jsonObject_with_key_and_value_and_applicationIdentifier(
			Double latitude1, Double longitude1, Double altitude1, 
			Double latitude2, Double longitude2, Double altitude2, 
			Double latitude3, Double longitude3, Double altitude3, 
			Double latitude4, Double longitude4, Double altitude4, 
			Double latitude5, Double longitude5, Double altitude5, 
			Double latitude6, Double longitude6, Double altitude6, 
			Double latitude7, Double longitude7, Double altitude7, 
			Double latitude8, Double longitude8, Double altitude8,  
			String key, String value, String applicationID){
		
		Map<String,String> properties = new HashMap<String, String>();
		properties.put(key, value);	
		
		PointDTO pointDTO1=createGeometryPoint(latitude1,longitude1);
		PointDTO pointDTO2=createGeometryPoint(latitude2,longitude2);
		PointDTO pointDTO3=createGeometryPoint(latitude3,longitude3);
		PointDTO pointDTO4=createGeometryPoint(latitude4,longitude4);
		PointDTO pointDTO5=createGeometryPoint(latitude5,longitude5);
		PointDTO pointDTO6=createGeometryPoint(latitude6,longitude6);
		PointDTO pointDTO7=createGeometryPoint(latitude7,longitude7);
		PointDTO pointDTO8=createGeometryPoint(latitude8,longitude8);
		
		Collection<PointDTO> collectionPointDTOs1=new ArrayList<PointDTO>();
		collectionPointDTOs1.add(pointDTO1);
		collectionPointDTOs1.add(pointDTO2);
		collectionPointDTOs1.add(pointDTO3);
		collectionPointDTOs1.add(pointDTO4);
		collectionPointDTOs1.add(pointDTO1);
		
		Collection<PointDTO> collectionPointDTOs2=new ArrayList<PointDTO>();
		collectionPointDTOs2.add(pointDTO5);
		collectionPointDTOs2.add(pointDTO6);
		collectionPointDTOs2.add(pointDTO7);
		collectionPointDTOs2.add(pointDTO8);
		collectionPointDTOs2.add(pointDTO5);
				
		LineStringDTO lineStringDTO1=createGeometryLineString(collectionPointDTOs1);
		LineStringDTO lineStringDTO2=createGeometryLineString(collectionPointDTOs2);
		
		Collection<LineStringDTO> collectionLineString=new ArrayList<LineStringDTO>();
		collectionLineString.add(lineStringDTO1);
		collectionLineString.add(lineStringDTO2);
		
		PolygonDTO polygonDTO=createGeometryPolygon(collectionLineString);
		
		FeatureDTO featureDTO=createFeatureDTO(properties, polygonDTO);
						
		response=sender.invoke(RestMethod.POST, "osiris/geolocation/territory/feature", featureDTO, new GenericType<FeatureDTO>(){}, new Headers("api_key", applicationID) );	
		
		Assert.assertEquals("The response must be an OK", Status.valueOf("OK").getStatusCode(),response.getStatus().getStatusCode());
		
		checkWithGet.setResponse(response);
		
		//Check the response of POST to verify I need delete anything 
		if(response.getEntity()!=null){
			FeatureDTO featureDTOResponse = response.getEntity();
			deleteAfter.addIDs(applicationID,featureDTOResponse.getId());
		}

	}
		
	private PointDTO createGeometryPoint(Double latitude, Double longitude){
		PointDTO pointDTO = new PointDTO();
		pointDTO.setLatitude(latitude);
		pointDTO.setLongitude(longitude);
		return pointDTO;
	}
	
	private LineStringDTO createGeometryLineString(Collection<PointDTO> collectionPointDTOs){
		LineStringDTO lineStringDTO = new LineStringDTO();
		lineStringDTO.setCollectionPointDTO(collectionPointDTOs);
		return lineStringDTO;
	}	
	
	private PolygonDTO createGeometryPolygon(Collection<LineStringDTO> collectionLineStringDTOs){
		PolygonDTO polygonDTO = new PolygonDTO();
		polygonDTO.setCollectionLineStringDTO(collectionLineStringDTOs);
		return polygonDTO;
	}	
	
	private FeatureDTO createFeatureDTO(Map<String,String> properties, GeometryDTO geometryDTO){
		FeatureDTO featureDTO = new FeatureDTO();
		featureDTO.setProperties(properties);
		featureDTO.setGeometryDTO(geometryDTO);
		return featureDTO;
	}


}