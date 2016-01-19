package com.bitmonlab.osiris.test.acceptancetest.map.feature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import junit.framework.Assert;

import com.bitmonlab.commons.api.map.model.geojson.Feature;
import com.bitmonlab.core.assembler.Assembler;
import com.bitmonlab.core.assembler.AssemblyException;
import com.bitmonlab.osiris.api.map.exceptions.InvalidGeometry;
import com.bitmonlab.osiris.api.map.exceptions.LatitudeValueException;
import com.bitmonlab.osiris.api.map.exceptions.LongitudeValueException;
import com.bitmonlab.osiris.api.map.exceptions.MongoGeospatialException;
import com.bitmonlab.osiris.api.map.managers.api.FeatureManager;
import com.bitmonlab.osiris.api.map.transferobject.FeatureDTO;
import com.bitmonlab.osiris.api.map.transferobject.GeometryDTO;
import com.bitmonlab.osiris.api.map.transferobject.LineStringDTO;
import com.bitmonlab.osiris.api.map.transferobject.PointDTO;
import com.bitmonlab.osiris.api.map.transferobject.PolygonDTO;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
public class StoreFeature {
	
	@Inject
	private FeatureManager featureManager;
	
	@Inject 
	@Named("FeatureAssembler")
	private Assembler<FeatureDTO, Feature> featureAssembler;
			
	private FeatureDTO response;
	
	@Inject
	private CheckWithGetFeature checkWithGet;
	
	@Inject
	private DeleteFeatureAfter deleteAfter;
	
	
	public static Exception exceptionCapture;
	
	@When("^I invoke a POST to storeFeature of type point with latitude (\\d+), longitude (\\d+), altitude (\\d+) and jsonObject with key \"([^\"]*)\" and value \"([^\"]*)\"$")
	public void I_invoke_a_POST_to_storeFeature_of_type_point_with_latitude_longitude_altitude_and_jsonObject_with_key_and_value( 
				Double latitude, Double longitude, Double altitude, 
				String key, String value) throws MongoGeospatialException, AssemblyException{
	    
	 	try{
			Map<String,String> properties = new HashMap<String, String>();
			properties.put(key, value);	
			
			PointDTO pointDTO=createGeometryPoint(latitude,longitude);
			
			FeatureDTO featureDTO=createFeatureDTO(properties, pointDTO);
			
			Feature feature = featureManager.storeFeature("1", featureAssembler.createDomainObject(featureDTO));		
			response=featureAssembler.createDataTransferObject(feature);		
	
			checkWithGet.setResponse(response);
			
			//Check the response of POST to verify I need delete anything 
			if(response!=null){		
				deleteAfter.addIDs("1",response.getId());
			}
		
	 	}catch (Exception e){			
			exceptionCapture = e;
		}
	}	
	
	@When("^I invoke a POST to storeFeature of type lineString with latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+) and jsonObject with key \"([^\"]*)\" and value \"([^\"]*)\"$")
	public void I_invoke_a_POST_to_storeFeature_of_type_lineString_with_latitude_longitude_altitude_latitude_longitude_altitude_and_jsonObject_with_key_and_value( 
				Double latitude1, Double longitude1, Double altitude1, 
				Double latitude2, Double longitude2, Double altitude2, 
				String key, String value) throws MongoGeospatialException, AssemblyException{
	    
		try{
			
		
			Map<String,String> properties = new HashMap<String, String>();
			properties.put(key, value);	
			
			PointDTO pointDTO1=createGeometryPoint(latitude1,longitude1);
			PointDTO pointDTO2=createGeometryPoint(latitude2,longitude2);
			
			Collection<PointDTO> collectionPointDTOs=new ArrayList<PointDTO>();
			collectionPointDTOs.add(pointDTO1);
			collectionPointDTOs.add(pointDTO2);
					
			LineStringDTO lineStringDTO=createGeometryLineString(collectionPointDTOs);
			
			FeatureDTO featureDTO=createFeatureDTO(properties, lineStringDTO);
									
			Feature feature = featureManager.storeFeature("1", featureAssembler.createDomainObject(featureDTO));		
			response=featureAssembler.createDataTransferObject(feature);		
			
			checkWithGet.setResponse(response);
			
			//Check the response of POST to verify I need delete anything 
			if(response!=null){			
				deleteAfter.addIDs("1",response.getId());
			}
		
		}catch (Exception e){			
			exceptionCapture = e;
		}
		
	}
	
	@When("^I invoke a POST to storeFeature of type polygon with latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+), latitude (\\d+), longitude (\\d+), altitude (\\d+) and jsonObject with key \"([^\"]*)\" and value \"([^\"]*)\"$")
	public void I_invoke_a_POST_to_storeFeature_of_type_polygon_with_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_latitude_longitude_altitude_and_jsonObject_with_key_and_value( 
			Double latitude1, Double longitude1, Double altitude1, 
			Double latitude2, Double longitude2, Double altitude2, 
			Double latitude3, Double longitude3, Double altitude3, 
			Double latitude4, Double longitude4, Double altitude4, 
			Double latitude5, Double longitude5, Double altitude5, 
			Double latitude6, Double longitude6, Double altitude6, 
			Double latitude7, Double longitude7, Double altitude7, 
			Double latitude8, Double longitude8, Double altitude8, 
			String key, String value) throws MongoGeospatialException, AssemblyException{
		
		try{
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
							
			Feature feature = featureManager.storeFeature("1", featureAssembler.createDomainObject(featureDTO));		
			response=featureAssembler.createDataTransferObject(feature);		
		
			
			checkWithGet.setResponse(response);
			
			//Check the response of POST to verify I need delete anything 
			if(response!=null){			
				deleteAfter.addIDs("1",response.getId());
			}
			
		}catch (Exception e){			
			exceptionCapture = e;
		}	
	}
	
	@When("^I invoke a POST to storeFeature of type errorGeometry with key \"([^\"]*)\" and value \"([^\"]*)\"$")
	public void I_invoke_a_POST_to_storeFeature_of_type_errorGeometry_with_key_and_value(String key, String value) throws Throwable {
		
		try{
			Map<String,String> properties = new HashMap<String, String>();
			properties.put(key, value);	
			
			FeatureDTO featureDTO=createFeatureDTO(properties, null);
			
			Feature feature = featureManager.storeFeature("1", featureAssembler.createDomainObject(featureDTO));		
			response=featureAssembler.createDataTransferObject(feature);		
				
			checkWithGet.setResponse(response);
			
			//Check the response of POST to verify I need delete anything 
			if(response!=null){			
				deleteAfter.addIDs("1",response.getId());
			}
			
		}catch (Exception e){			
			exceptionCapture = e;
		}	
			
	}
	
	@Then("^I receive a InvalidGeometry$")
	public void I_receive_a_InvalidGeometry() throws Throwable {
	    // Express the Regexp above with the code you wish you had
		Assert.assertEquals(exceptionCapture.getClass() , new InvalidGeometry().getClass() );
	}

	@Then("^I receive a LatitudeValueException$")
	public void I_receive_a_LatitudeValueException() throws Throwable {
	    // Express the Regexp above with the code you wish you had
		Assert.assertEquals(exceptionCapture.getClass() , new LatitudeValueException().getClass() );
	}

	@Then("^I receive a LongitudeValueException$")
	public void I_receive_a_LongitudeValueException() throws Throwable {
	    // Express the Regexp above with the code you wish you had
		Assert.assertEquals(exceptionCapture.getClass() , new LongitudeValueException().getClass() );
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
