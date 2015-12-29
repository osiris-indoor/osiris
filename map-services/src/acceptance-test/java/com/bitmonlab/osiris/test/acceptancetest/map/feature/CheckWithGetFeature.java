package com.bitmonlab.osiris.test.acceptancetest.map.feature;

import java.util.Collection;
import java.util.Iterator;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Assert;

import com.bitmonlab.commons.api.map.model.geojson.Feature;
import com.bitmonlab.core.assembler.Assembler;
import com.bitmonlab.osiris.api.map.managers.api.FeatureManager;
import com.bitmonlab.osiris.api.map.transferobject.FeatureDTO;
import com.bitmonlab.osiris.api.map.transferobject.LineStringDTO;
import com.bitmonlab.osiris.api.map.transferobject.PointDTO;
import com.bitmonlab.osiris.api.map.transferobject.PolygonDTO;

import cucumber.api.java.en.Then;

public class CheckWithGetFeature {
	
	@Inject
	private FeatureManager featureManager;
	
	@Inject 
	@Named("FeatureAssembler")
	private Assembler<FeatureDTO, Feature> featureAssembler;
		
	private FeatureDTO response;
	
		
	
	@Then("^I invoke a GET to getFeatureById with Id to check a type point$")
	public void I_invoke_a_GET_to_getFeatureById_with_Id_to_check_a_type_point() throws Throwable {
		
		FeatureDTO featureDTO = response;
		
		try{
			Feature feature = featureManager.getFeatureByID("1",featureDTO.getId());
			response=featureAssembler.createDataTransferObject(feature);
		}catch (Exception e){			
			DeleteFeature.exceptionCapture = e;
		}	
					
					
		FeatureDTO featureDTOResponse = response;
				
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
	
	@Then("^I invoke a GET to getFeatureById with Id to check a type lineString$")
	public void I_invoke_a_GET_to_getFeatureById_with_Id_to_check_a_type_lineString() throws Throwable {
		
		FeatureDTO featureDTO = response;
		
		try{
			Feature feature = featureManager.getFeatureByID("1",featureDTO.getId());
			response=featureAssembler.createDataTransferObject(feature);
		}catch (Exception e){			
			DeleteFeature.exceptionCapture = e;
		}
		
			
			FeatureDTO featureDTOResponse = response;
			
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
	
	@Then("^I invoke a GET to getFeatureById with Id to check a type polygon$")
	public void I_invoke_a_GET_to_getFeatureById_with_Id_to_check_a_type_polygon() throws Throwable {
		
		FeatureDTO featureDTO = response;
		
		try{
			Feature feature = featureManager.getFeatureByID("1",featureDTO.getId());
			response=featureAssembler.createDataTransferObject(feature);
		}catch (Exception e){			
			DeleteFeature.exceptionCapture = e;
		}	
					
		FeatureDTO featureDTOResponse = response;
			
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
	
	
	
	
	public void setResponse(FeatureDTO response) {
		this.response = response;
	}


}
