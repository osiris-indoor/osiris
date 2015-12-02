package com.bitmonlab.osiris.api.map.rest.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bitmonlab.osiris.api.map.assemblers.FeatureAssemblerImpl;
import com.bitmonlab.osiris.api.map.exceptions.FeatureNotExistException;
import com.bitmonlab.osiris.api.map.exceptions.MongoGeospatialException;
import com.bitmonlab.osiris.api.map.managers.impl.FeatureManagerImpl;
import com.bitmonlab.osiris.api.map.rest.impl.FeatureResourceImpl;
import com.bitmonlab.osiris.api.map.transferobject.FeatureDTO;
import com.bitmonlab.commons.api.map.model.geojson.Feature;
import com.bitmonlab.core.assembler.AssemblyException;
import com.bitmonlab.core.validations.validator.Validations;

@RunWith(MockitoJUnitRunner.class)
public class FeatureResourceImplTest {
	
	private static final String APP_IDENTIFIER="9";
	private static final String ID_FEATURE="1";
	
	@Mock
	private FeatureDTO featureDTO;
	
	@Mock
	private FeatureDTO featureDTOReturn;
	
	@InjectMocks
	private FeatureResourceImpl featureResourceImpl;
	
	@Mock
	private FeatureManagerImpl featureManagerImpl;
	
	@Mock
	private Feature feature;
	
	@Mock
	private Feature featureReturn;
	
	@Mock
	private FeatureAssemblerImpl featureAssembler;
	
	@Mock
	private Validations validations;

	@Test
	public void storeFeature() throws AssemblyException, MongoGeospatialException{	
		//Fixture	
		Mockito.when(featureAssembler.createDomainObject(featureDTO)).thenReturn(feature);	//DTO --> Model Object
		Mockito.when(featureManagerImpl.storeFeature(APP_IDENTIFIER, feature)).thenReturn(featureReturn);  //store
		Mockito.when(featureAssembler.createDataTransferObject(featureReturn)).thenReturn(featureDTOReturn); //Model Object --> DTO
		
		//Experimentation
		Response response=featureResourceImpl.storeFeature(APP_IDENTIFIER,featureDTO);	
		
		//Expectations
		Mockito.verify(validations).checkIsNotNullAndNotBlank(APP_IDENTIFIER);
		Mockito.verify(validations).checkIsNotNull(featureDTO);
		
		Mockito.verify(featureAssembler).createDomainObject(featureDTO);
		Mockito.verify(featureManagerImpl).storeFeature(APP_IDENTIFIER,feature);
		Mockito.verify(featureAssembler).createDataTransferObject(featureReturn);
		
		assertNotNull(response.getEntity());
		assertEquals("FeatureDTO is not the expected", featureDTOReturn, (FeatureDTO) response.getEntity());
		assertEquals("The Status response is not the expected", 200, response.getStatus());
		
	}  
	
	@Test
	public void deleteGeoPoint() throws AssemblyException, FeatureNotExistException{
		//Fixture
		
		//Experimentation
		Response response=featureResourceImpl.deleteFeature(APP_IDENTIFIER,ID_FEATURE);
		
		//Expectation
		Mockito.verify(validations).checkIsNotNullAndNotBlank(APP_IDENTIFIER,ID_FEATURE);
		
		Mockito.verify(featureManagerImpl).deleteFeature(APP_IDENTIFIER,ID_FEATURE);
		
		assertEquals("The Status response is not the expected", 204, response.getStatus());
	}
	
	@Test
	public void updateFeature() throws AssemblyException, FeatureNotExistException, MongoGeospatialException{
		
		String idFeature = "1";
		
		//Fixture	
		Mockito.when(featureAssembler.createDomainObject(featureDTO)).thenReturn(feature);	//DTO --> Model Object
		Mockito.when(featureManagerImpl.updateFeature(APP_IDENTIFIER,idFeature, feature)).thenReturn(featureReturn);  //update
		Mockito.when(featureAssembler.createDataTransferObject(featureReturn)).thenReturn(featureDTOReturn); //Model Object --> DTO
				
		//Experimentation
		Response response=featureResourceImpl.updateFeature(APP_IDENTIFIER, idFeature, featureDTO);	
							
			
		//Expectations
		Mockito.verify(validations).checkIsNotNullAndNotBlank(APP_IDENTIFIER,ID_FEATURE);
		Mockito.verify(validations).checkIsNotNull(featureDTO);
		
		Mockito.verify(featureManagerImpl).updateFeature(APP_IDENTIFIER,idFeature,feature);		
		
		assertNotNull(response.getEntity());
		assertEquals("FeatureDTO is not the expected", featureDTOReturn, (FeatureDTO) response.getEntity());
		assertEquals("The Status response is not the expected", 200, response.getStatus());
		
	}
	
	@Test
	public void getFeatureWithID() throws AssemblyException, FeatureNotExistException {
						
		//Fixture
		Mockito.when(featureManagerImpl.getFeatureByID(APP_IDENTIFIER,ID_FEATURE)).thenReturn(feature);
		Mockito.when(featureAssembler.createDataTransferObject(feature)).thenReturn(featureDTO);
		
		//Experimentation
		Response response = featureResourceImpl.getFeatureByID(APP_IDENTIFIER, ID_FEATURE);
				
		//Expectation	
		Mockito.verify(validations).checkIsNotNullAndNotBlank(APP_IDENTIFIER,ID_FEATURE);
		
		Mockito.verify(featureManagerImpl).getFeatureByID(APP_IDENTIFIER, ID_FEATURE);
		Mockito.verify(featureAssembler).createDataTransferObject(feature);
	    
	    assertNotNull(response.getEntity());
		assertEquals("FeatureDTO is not the expected", featureDTO, (FeatureDTO) response.getEntity());
		assertEquals("The Status response is not the expected", 200, response.getStatus());
		
	}
	

}
