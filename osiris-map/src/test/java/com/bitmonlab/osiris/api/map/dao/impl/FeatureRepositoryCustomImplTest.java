package com.bitmonlab.osiris.api.map.dao.impl;

import java.util.Collection;
import java.util.List;

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
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

import com.bitmonlab.osiris.api.core.map.dao.impl.FeatureRepositoryCustomImpl;
import com.bitmonlab.osiris.api.core.map.exceptions.MongoGeospatialException;
import com.bitmonlab.osiris.api.core.map.exceptions.QueryException;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;


@RunWith(PowerMockRunner.class)
@PrepareForTest({FeatureRepositoryCustomImpl.class, BasicDBObject.class})
public class FeatureRepositoryCustomImplTest {
	
	@InjectMocks
	private FeatureRepositoryCustomImpl featureRepository;
	
	@Mock
	private BasicQuery basicQuery;
	
	@Mock
	private MongoTemplate mongoTemplate;
	
	@Mock
	private List<Feature> features;
	
	@Mock
	private Feature feature;
	
	private final static String collectionName = "feature_app_";
	
	@Test
	public void saveIndexAndFeatureByAppIdTest() throws Exception{
		
		String idApplication = "1";  
		//Fixture
		Feature feature=Mockito.mock(Feature.class);
		DBCollection dbCollection=Mockito.mock(DBCollection.class);
		BasicDBObject obj=Mockito.mock(BasicDBObject.class);
		Mockito.when(mongoTemplate.collectionExists(collectionName+idApplication)).thenReturn(false);
		Mockito.when(mongoTemplate.getCollection(collectionName+idApplication)).thenReturn(dbCollection);
		PowerMockito.whenNew(BasicDBObject.class).withNoArguments().thenReturn(obj);
		Mockito.when(obj.put("geometry", "2dsphere")).thenReturn(dbCollection);
		
		//Experimentation
		featureRepository.save(idApplication, feature);
		//Expectation
		Mockito.verify(mongoTemplate).collectionExists(collectionName+idApplication);
		Mockito.verify(mongoTemplate).save(feature,collectionName+idApplication);
	}
	
	@Test
	public void saveFeatureByAppIdTest() throws MongoGeospatialException{
		
		String idApplication = "1";  
		//Fixture
		Feature feature=Mockito.mock(Feature.class);
		Mockito.when(mongoTemplate.collectionExists(collectionName+idApplication)).thenReturn(true);
		//Experimentation
		featureRepository.save(idApplication, feature);
		//Expectation
		Mockito.verify(mongoTemplate).collectionExists(collectionName+idApplication);
		Mockito.verify(mongoTemplate).save(feature,collectionName+idApplication);
	}
	
	@Test(expected=MongoGeospatialException.class)
	public void shouldThrowErrorWhenGeospatialIndexNotCreated() throws Exception{
		
		String idApplication = "1";  
		//Fixture
		Feature feature=Mockito.mock(Feature.class);
		DBCollection dbCollection=Mockito.mock(DBCollection.class);
		BasicDBObject obj=Mockito.mock(BasicDBObject.class);
		Mockito.when(mongoTemplate.collectionExists(collectionName+idApplication)).thenReturn(false);
		Mockito.when(mongoTemplate.getCollection(collectionName+idApplication)).thenReturn(dbCollection);
		PowerMockito.whenNew(BasicDBObject.class).withNoArguments().thenReturn(obj);
		Mockito.doThrow(UncategorizedMongoDbException.class).when(mongoTemplate).save(feature,collectionName+idApplication);
		//Experimentation
		featureRepository.save(idApplication, feature);
		//Expectation
		Mockito.verify(mongoTemplate).collectionExists(collectionName+idApplication);
		Mockito.verify(mongoTemplate).save(feature,collectionName+idApplication);
	}
	
	@Test
	public void deleteFeatureTest(){
		
		String idApplication = "1";  
		String idFeature = "2";  
		//Fixture
		Feature feature=Mockito.mock(Feature.class);
		Mockito.when(mongoTemplate.findById(idFeature,Feature.class,collectionName+idApplication)).thenReturn(feature);
		//Experimentation
		featureRepository.delete(idApplication, idFeature);
		//Expectation
		Mockito.verify(mongoTemplate).findById(idFeature,Feature.class,collectionName+idApplication);
		Mockito.verify(mongoTemplate).remove(feature,collectionName+idApplication);
	}
	
	@Test
	public void findByApplicationIdentifierAndIdTest(){
		
		String idApplication = "1";  
		String idFeature = "2";  
		//Fixture
		//Experimentation
		featureRepository.findByApplicationIdentifierAndId(idApplication, idFeature);
		//Expectation
		Mockito.verify(mongoTemplate).findById(idFeature,Feature.class,collectionName+idApplication);
	}	
			
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
		Mockito.when(mongoTemplate.find(queryLimit, Feature.class, collectionName+idApplication)).thenReturn(features);
		
		
		
		//Experimentation
		Collection<Feature> featuresResponse=featureRepository.searchIDAppAndQuery(idApplication, queryJSON, pageIndex, pageSize);
		
		//Expectations
		Mockito.verify(mongoTemplate).find(queryLimit, Feature.class, collectionName+idApplication);
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
		featureRepository.searchIDAppAndQuery(idApplication, queryJSON, pageIndex, pageSize);
		
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
		Mockito.when(mongoTemplate.find(query, Feature.class, collectionName+idApplication)).thenReturn(features);
				
		//Experimentation
		Collection<Feature> featuresResponse=featureRepository.searchIDAppAndQuery(idApplication, queryJSON, pageIndex, pageSize, orderField, order);
		
		//Expectations
		Mockito.verify(mongoTemplate).find(query, Feature.class, collectionName+idApplication);
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
		featureRepository.searchIDAppAndQuery(idApplication, queryJSON, pageIndex, pageSize, orderField, order);
		
		//Expectations

	}
	
	

}
