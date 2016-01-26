package com.bitmonlab.osiris.test.acceptancetest.map.feature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.bitmonlab.osiris.test.acceptancetest.map.commons.HttpResponse;
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

import cucumber.api.java.en.When;
public class UpdateFeature {
	
	@Inject
	@Named("osirisMapRequestSender")
	private RestRequestSender sender;
	
	@Inject
	private HttpResponse httpResponse;
	
	@Inject
	private PreconditionStoreFeature preconditionStoreFeature;
	
	private ClientResponse<FeatureDTO> response;
	
	@Inject
	private CheckWithGetFeature checkWithGet;
	
	@When("^I invoke a PUT to \"([^\"]*)\" of type point and idFeature with latitude (\\d+), longitude (\\d+), altitude (\\d+) and jsonObject with key \"([^\"]*)\" and value \"([^\"]*)\"$")
	public void I_invoke_a_PUT_to_of_type_point_and_idFeature_with_latitude_longitude_altitude_and_jsonObject_with_key_and_value(String url, Double latitude, Double longitude, Double altitude, String key, String value){
	    
		Map<String,String> properties = new HashMap<String, String>();
		properties.put(key, value);	
		
		PointDTO pointDTO=createGeometryPoint(latitude,longitude);
		
		FeatureDTO featureDTO=createFeatureDTO(properties, pointDTO);
						
		FeatureDTO feature =  preconditionStoreFeature.getResponse().getEntity();		
		String idFeature = feature.getId();
		
		response=sender.invoke(RestMethod.PUT, url + "/" + idFeature , featureDTO, new GenericType<FeatureDTO>(){}, new Headers("api_key", "1"));	
		
		httpResponse.setResponse(response);
		
		checkWithGet.setResponse(response);
		
	}
	
	@When("^I invoke a PUT to \"([^\"]*)\" of type lineString and idFeature with latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+) and jsonObject with key \"([^\"]*)\" and value \"([^\"]*)\"$")
	public void I_invoke_a_PUT_to_of_type_lineString_and_idFeature_with_latitude_longitude_altitude_latitude_longitude_altitude_and_jsonObject_with_key_and_value(String url, Double latitude1, Double longitude1, Double altitude1, Double latitude2, Double longitude2, Double altitude2, String key, String value) throws Throwable {

		Map<String,String> properties = new HashMap<String, String>();
		properties.put(key, value);	
		
		PointDTO pointDTO1=createGeometryPoint(latitude1,longitude1);
		PointDTO pointDTO2=createGeometryPoint(latitude2,longitude2);
		
		Collection<PointDTO> collectionPointDTOs=new ArrayList<PointDTO>();
		collectionPointDTOs.add(pointDTO1);
		collectionPointDTOs.add(pointDTO2);
				
		LineStringDTO lineStringDTO=createGeometryLineString(collectionPointDTOs);
		
		FeatureDTO featureDTO=createFeatureDTO(properties, lineStringDTO);
						
		FeatureDTO feature =  preconditionStoreFeature.getResponse().getEntity();		
		String idFeature = feature.getId();
		
		response=sender.invoke(RestMethod.PUT, url + "/" + idFeature , featureDTO, new GenericType<FeatureDTO>(){}, new Headers("api_key", "1"));	
		
		httpResponse.setResponse(response);
		
		checkWithGet.setResponse(response);
		
	}
	
	@When("^I invoke a PUT to \"([^\"]*)\" of type polygon and idFeature with latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+) and jsonObject with key \"([^\"]*)\" and value \"([^\"]*)\"$")
	public void I_invoke_a_PUT_to_of_type_polygon_and_idFeature_with_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_and_jsonObject_with_key_and_value(
			String url, 
			Double latitude1, Double longitude1, Double altitude1, 
			Double latitude2, Double longitude2, Double altitude2, 
			Double latitude3, Double longitude3, Double altitude3, 
			Double latitude4, Double longitude4, Double altitude4, 
			Double latitude5, Double longitude5, Double altitude5, 
			Double latitude6, Double longitude6, Double altitude6, 
			Double latitude7, Double longitude7, Double altitude7, 
			Double latitude8, Double longitude8, Double altitude8,			
			String key, String value){
		
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
					
		FeatureDTO feature =  preconditionStoreFeature.getResponse().getEntity();		
		String idFeature = feature.getId();
		
		response=sender.invoke(RestMethod.PUT, url + "/" + idFeature , featureDTO, new GenericType<FeatureDTO>(){}, new Headers("api_key", "1"));	
		
		httpResponse.setResponse(response);
		
		checkWithGet.setResponse(response);

	}
	
	@When("^I invoke a PUT to \"([^\"]*)\" of type errorGeometry with key \"([^\"]*)\" and value \"([^\"]*)\"$")
	public void I_invoke_a_PUT_to_of_type_errorGeometry_with_key_and_value(String url, String key, String value) throws Throwable {

		Map<String,String> properties = new HashMap<String, String>();
		properties.put(key, value);	
		
		FeatureDTO featureDTO=createFeatureDTO(properties, null);
						
		FeatureDTO feature =  preconditionStoreFeature.getResponse().getEntity();		
		String idFeature = feature.getId();
		
		response=sender.invoke(RestMethod.PUT, url + "/" + idFeature , featureDTO, new GenericType<FeatureDTO>(){}, new Headers("api_key", "1"));	
		
		httpResponse.setResponse(response);
		
		checkWithGet.setResponse(response);
		
	}	

	@When("^I invoke a PUT to \"([^\"]*)\" of type point with latitude (\\d+), longitude (\\d+), altitude (\\d+) and jsonObject with key \"([^\"]*)\" and value \"([^\"]*)\"$")
	public void I_invoke_a_PUT_to_of_type_point_with_latitude_longitude_altitude_and_jsonObject_with_key_and_value(String url, Double latitude, Double longitude, Double altitude, String key, String value) throws Throwable {

		Map<String,String> properties = new HashMap<String, String>();
		properties.put(key, value);	
		
		PointDTO pointDTO=createGeometryPoint(latitude,longitude);
		
		FeatureDTO featureDTO=createFeatureDTO(properties, pointDTO);
					
		response=sender.invoke(RestMethod.PUT, url, featureDTO, new GenericType<FeatureDTO>(){}, new Headers("api_key", "1"));	
		
		httpResponse.setResponse(response);
		
		checkWithGet.setResponse(response);
		
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
