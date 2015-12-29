package com.bitmonlab.osiris.api.map.managers.impl;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.bitmonlab.osiris.api.map.dao.api.FeatureRepository;
import com.bitmonlab.osiris.api.map.dao.api.MapRepository;
import com.bitmonlab.osiris.api.map.exceptions.QueryException;
import com.bitmonlab.osiris.api.map.managers.impl.SearchManagerImpl;
import com.bitmonlab.osiris.api.map.transferobject.LayerDTO;
import com.bitmonlab.commons.api.map.model.geojson.Feature;

@RunWith(MockitoJUnitRunner.class)
//@RunWith(PowerMockRunner.class)
@PrepareForTest({SearchManagerImpl.class, Integer.class})
public class SearchManagerImplTest {

	@InjectMocks
	private SearchManagerImpl searchManagerImpl;
	
	@Mock 
	private FeatureRepository featureRepository;
	
	@Mock
	private MapRepository mapRepository;
	
	@Mock
	private Collection<Feature> collectionFeatures;
	
	@Mock
	private Collection<Feature> collectionMap;

	
	@Test
	public void getFeaturesByQueryAllTest() throws QueryException{
		
		 String idApplication = "9";  
		 String query ="query";
		 Integer pageIndex=5;
		 Integer pageSize= 20;
		 LayerDTO layerDTO=LayerDTO.ALL;
		 
		//Fixture
		Mockito.when(featureRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize)).thenReturn(collectionFeatures);
		Mockito.when(mapRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize)).thenReturn(collectionMap);
		 
		//Experimentation
		searchManagerImpl.getFeaturesByQuery(idApplication, query, layerDTO, pageIndex, pageSize);
		
		//Expectation
		Mockito.verify(featureRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize);
		Mockito.verify(mapRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize);
		Mockito.verify(collectionFeatures).addAll(collectionMap);		
	}
	
	@Test
	public void getFeaturesByQueryFeaturesTest() throws QueryException{
		
		 String idApplication = "9";  
		 String query ="query";
		 Integer pageIndex=5;
		 Integer pageSize= 20;
		 LayerDTO layerDTO=LayerDTO.FEATURES;
		 
		//Fixture
		Mockito.when(featureRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize)).thenReturn(collectionFeatures);
				 
		//Experimentation
		searchManagerImpl.getFeaturesByQuery(idApplication, query, layerDTO, pageIndex, pageSize);
		
		//Expectation
		Mockito.verify(featureRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize);
	}
	
	@Test
	public void getFeaturesByQueryMapTest() throws QueryException{
		
		String idApplication = "9";  
		String query ="query";
		Integer pageIndex=5;
		Integer pageSize= 20;
		LayerDTO layerDTO=LayerDTO.MAP;
				 
		//Fixture
		Mockito.when(mapRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize)).thenReturn(collectionFeatures);
				 
		//Experimentation
		searchManagerImpl.getFeaturesByQuery(idApplication, query, layerDTO, pageIndex, pageSize);
		
		//Expectation
		Mockito.verify(mapRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize);
	}
	
	
	
	@Test
	public void getFeaturesByQueryAllWithOrderTest() throws QueryException{
		
		String idApplication = "9";  
		String query ="query";
		Integer pageIndex=5;
		Integer pageSize= 20;
		LayerDTO layerDTO=LayerDTO.ALL;
		String orderField="field";
	    String order="ASC";
		
		//Fixture
		Mockito.when(featureRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize, orderField, order)).thenReturn(collectionFeatures);
		Mockito.when(mapRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize, orderField, order)).thenReturn(collectionMap);
		 
		//Experimentation
		searchManagerImpl.getFeaturesByQuery(idApplication, query, layerDTO, pageIndex, pageSize, orderField, order);
		
		//Expectation
		Mockito.verify(featureRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize, orderField, order);
		Mockito.verify(mapRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize, orderField, order);
		Mockito.verify(collectionFeatures).addAll(collectionMap);		
	}
	
	@Test
	public void getFeaturesByQueryFeaturesWithOrderTest() throws QueryException{
		
		String idApplication = "9";  
		String query ="query";
		Integer pageIndex=5;
		Integer pageSize= 20;
		LayerDTO layerDTO=LayerDTO.FEATURES;
		String orderField="field";
	    String order="ASC";
		 
		//Fixture
		Mockito.when(featureRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize, orderField, order)).thenReturn(collectionFeatures);
				 
		//Experimentation
		searchManagerImpl.getFeaturesByQuery(idApplication, query, layerDTO, pageIndex, pageSize, orderField, order);
		
		//Expectation
		Mockito.verify(featureRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize, orderField, order);
	}
	
	@Test
	public void getFeaturesByQueryMapWithOrderTest() throws QueryException{
		
		String idApplication = "9";  
		String query ="query";
		Integer pageIndex=5;
		Integer pageSize= 20;
		LayerDTO layerDTO=LayerDTO.MAP;
		String orderField="field";
		String order="ASC";
		 
		//Fixture
		Mockito.when(mapRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize, orderField, order)).thenReturn(collectionFeatures);
				 
		//Experimentation
		searchManagerImpl.getFeaturesByQuery(idApplication, query, layerDTO, pageIndex, pageSize, orderField, order);
		
		//Expectation
		Mockito.verify(mapRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize, orderField, order);
	}
	
	
	
}
