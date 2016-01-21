package com.bitmonlab.osiris.api.map.rest.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bitmonlab.osiris.api.core.map.assemblers.FeatureAssemblerImpl;
import com.bitmonlab.osiris.api.core.map.exceptions.QueryException;
import com.bitmonlab.osiris.api.core.map.managers.impl.SearchManagerImpl;
import com.bitmonlab.osiris.api.core.map.transferobject.FeatureDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.LayerDTO;
import com.bitmonlab.osiris.api.map.rest.impl.SearchResourceImpl;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;
import com.bitmonlab.osiris.core.assembler.AssemblyException;
import com.bitmonlab.osiris.core.validations.validator.Validations;

@RunWith(MockitoJUnitRunner.class)
public class SearchResourceImplTest {
	
	private static final String APP_IDENTIFIER="9";
	private static final String queryJson = "query";
	private static final Integer pageIndex = 5;
	private static final Integer pageSize = 20;
	private static final String orderField = "mockfield";
	private static final String order = "ASC";
	private static final LayerDTO layerDTO=LayerDTO.ALL;
		
	@InjectMocks
	private SearchResourceImpl searchResourceImpl;
	
	@Mock
	private SearchManagerImpl searchManagerImpl;
	
	@Mock
	private FeatureAssemblerImpl featureAssembler;
	
	@Mock
	private Collection<Feature> collectionFeatures;
	
	@Mock
	private Collection<FeatureDTO> featuresDTO;
	
	@Mock
	private Validations validations;
	
	@Test
	public void getFeaturesByQueryTest() throws AssemblyException, QueryException{
					
		//Fixture
		Mockito.when(searchManagerImpl.getFeaturesByQuery(APP_IDENTIFIER,queryJson, layerDTO,pageIndex, pageSize)).thenReturn(collectionFeatures);
		Mockito.when(featureAssembler.createDataTransferObjects(collectionFeatures)).thenReturn(featuresDTO);
		
		//Experimentation
		Response response = searchResourceImpl.getFeaturesByQuery(APP_IDENTIFIER, queryJson, layerDTO, pageIndex, pageSize, "", order);
				
		//Expectation	
		Mockito.verify(validations).checkIsNotNullAndNotBlank(APP_IDENTIFIER,queryJson);
		Mockito.verify(validations).checkIsNotNull(layerDTO);
		Mockito.verify(validations).checkMin(0, pageIndex);
		Mockito.verify(validations).checkMin(1, pageSize);
		
		Mockito.verify(searchManagerImpl).getFeaturesByQuery(APP_IDENTIFIER,queryJson,layerDTO, pageIndex, pageSize);
		Mockito.verify(featureAssembler).createDataTransferObjects(collectionFeatures);
		
		assertNotNull(response.getEntity());
		assertEquals("CollectionFeatureDTO is not the expected", featuresDTO, (Collection<FeatureDTO>) response.getEntity());
		assertEquals("The Status response is not the expected", 200, response.getStatus());
		
	}
	
	@Test
	public void getFeaturesByQueryWithOrderTest() throws AssemblyException, QueryException{
	    
	  //Fixture
	  Mockito.when(searchManagerImpl.getFeaturesByQuery(APP_IDENTIFIER,queryJson, layerDTO,pageIndex, pageSize, orderField, order)).thenReturn(collectionFeatures);
	  Mockito.when(featureAssembler.createDataTransferObjects(collectionFeatures)).thenReturn(featuresDTO);
	  	
	  //Experimentation
	  Response response = searchResourceImpl.getFeaturesByQuery(APP_IDENTIFIER, queryJson, layerDTO, pageIndex, pageSize, orderField, order);
	  				
	  //Expectation		
	  Mockito.verify(searchManagerImpl).getFeaturesByQuery(APP_IDENTIFIER,queryJson,layerDTO, pageIndex, pageSize, orderField, order);
	  Mockito.verify(featureAssembler).createDataTransferObjects(collectionFeatures);
	  
	  assertNotNull(response.getEntity());
	  assertEquals("CollectionFeatureDTO is not the expected", featuresDTO, (Collection<FeatureDTO>) response.getEntity());
	  assertEquals("The Status response is not the expected", 200, response.getStatus());
		
	}

}
