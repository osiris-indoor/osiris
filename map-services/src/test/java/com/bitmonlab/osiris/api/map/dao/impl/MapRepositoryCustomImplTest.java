package com.bitmonlab.osiris.api.map.dao.impl;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

import com.bitmonlab.osiris.api.map.dao.impl.MapRepositoryCustomImpl;
import com.bitmonlab.osiris.api.map.exceptions.QueryException;
import com.bitmonlab.commons.api.map.model.geojson.Feature;
import com.mongodb.BasicDBObject;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MapRepositoryCustomImpl.class,BasicDBObject.class,Query.class,DateTime.class})
public class MapRepositoryCustomImplTest {

	@InjectMocks
	private MapRepositoryCustomImpl mapRepositoryCustomImpl;
	
	@Mock
	private MongoTemplate mongoTemplate;
	
	@Mock
	private BasicQuery basicQuery;	
	
	@Mock		
	private List<Feature> features;
				
	private final static String collectionMap = "map_app_";

		
	@Test
	public void findByIDAppAndQueryTest() throws Exception{
		
		String idApplication = "1";  
		String queryJSON = "{ geometry:{ $geoWithin:{ $centerSphere:[ [20.05,20.01] , 0.05]} } }";	
		Integer pageIndex=5;
		Integer pageSize= 20;
		int skipElementsValue = pageIndex*pageSize;		 
			
		//Fixture		 	    
	    Query querySkip=Mockito.mock(Query.class);
	    Query queryLimit=Mockito.mock(Query.class);
		PowerMockito.whenNew(BasicQuery.class).withArguments(queryJSON).thenReturn(basicQuery);				
		Mockito.when(basicQuery.skip(skipElementsValue)).thenReturn(querySkip);
		Mockito.when(querySkip.limit(pageSize)).thenReturn(queryLimit);				
		Mockito.when(mongoTemplate.find(queryLimit, Feature.class, collectionMap+idApplication)).thenReturn(features);
		
		//Experimentation
		Collection<Feature> featuresResponse=mapRepositoryCustomImpl.searchIDAppAndQuery(idApplication, queryJSON, pageIndex, pageSize);
		
		//Expectations
		Mockito.verify(mongoTemplate).find(queryLimit, Feature.class, collectionMap+idApplication);
		Assert.assertEquals("Features must be equals", featuresResponse, features);
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Test(expected=QueryException.class)
	public void findByIDAppAndQueryErrorQueryTesTest() throws Exception{

		String idApplication = "1";  
		String queryJSON = "{ geometry:{ $geoWithin: $centerSphere:[ [20.05,20.01] , 0.05]} } }";	
		Integer pageIndex=5;
		Integer pageSize= 20;		
					
		//Fixture		 	    	    	    	    
		PowerMockito.whenNew(BasicQuery.class).withArguments(queryJSON).thenThrow(Exception.class);						
						 		
		//Experimentation
		mapRepositoryCustomImpl.searchIDAppAndQuery(idApplication, queryJSON, pageIndex, pageSize);
		
		//Expectations

	}
	
	@Test
	public void findByIDAppAndQueryWithOrderTest() throws Exception{
		
		String idApplication = "1";  
		String queryJSON = "{ geometry:{ $geoWithin:{ $centerSphere:[ [20.05,20.01] , 0.05]} } }";	
		Integer pageIndex=5;
		Integer pageSize= 20;
		int skipElementsValue = pageIndex*pageSize;
		String orderField = "_id"; 
		String order = "ASC";
					
		//Fixture		 	    
	    Query querySkip=Mockito.mock(Query.class);
	    Query queryLimit=Mockito.mock(Query.class);
	    Query query=Mockito.mock(Query.class);
	    Sort  sort = Mockito.mock(Sort.class);
		PowerMockito.whenNew(BasicQuery.class).withArguments(queryJSON).thenReturn(basicQuery);				
		Mockito.when(basicQuery.skip(skipElementsValue)).thenReturn(querySkip);
		Mockito.when(querySkip.limit(pageSize)).thenReturn(queryLimit);				
		PowerMockito.whenNew(Sort.class).withArguments(Sort.Direction.valueOf(order), orderField).thenReturn(sort);
		Mockito.when(queryLimit.with(sort)).thenReturn(query);		
		Mockito.when(mongoTemplate.find(query, Feature.class, collectionMap+idApplication)).thenReturn(features);
				
		//Experimentation
		Collection<Feature> featuresResponse=mapRepositoryCustomImpl.searchIDAppAndQuery(idApplication, queryJSON, pageIndex, pageSize, orderField, order);
		
		//Expectations
		Mockito.verify(mongoTemplate).find(query, Feature.class, collectionMap+idApplication);
		Assert.assertEquals("Features must be equals", featuresResponse, features);
	}
	
	
	@SuppressWarnings("unchecked")
	@Test(expected=QueryException.class)
	public void findByIDAppAndQueryErrorQueryTest() throws Exception{
		
		 String idApplication = "1";  
		 String queryJSON = "{ geometry:{ $geoWithin: $centerSphere:[ [20.05,20.01] , 0.05]} } }";	
		 Integer pageIndex=5;
		 Integer pageSize= 20;		
		
		 String orderField = "_id"; 
		 String order = "ASC";
		  
			
		//Fixture		 	    	    	    	    
		PowerMockito.whenNew(BasicQuery.class).withArguments(queryJSON).thenThrow(Exception.class);						
						 		
		//Experimentation
		mapRepositoryCustomImpl.searchIDAppAndQuery(idApplication, queryJSON, pageIndex, pageSize, orderField, order);
		
		//Expectations

	}
	
	
	
}
