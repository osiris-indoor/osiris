package com.bitmonlab.osiris.imports.map.dao.impl;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

import com.bitmonlab.osiris.commons.map.model.geojson.Feature;
import com.bitmonlab.osiris.commons.map.model.geojson.Geometry;
import com.bitmonlab.osiris.commons.map.model.geojson.Point;
import com.bitmonlab.osiris.commons.map.model.imports.LockImport;
import com.bitmonlab.osiris.imports.map.dao.impl.ImportRepositoryCustomImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ImportRepositoryCustomImpl.class,BasicDBObject.class,Query.class,DateTime.class})
public class ImportRepositoryCustomImplTest {

	@InjectMocks
	private ImportRepositoryCustomImpl importRepositoryCustom;
	
	@Mock
	private MongoTemplate mongoTemplate;
	
	@Mock
	private Feature feature1;
	
	@Mock
	private Geometry geometry1;
	
	@Mock
	private DBCollection dbCollection;
	
	@Mock
	private BasicDBObject obj;
	
	@Mock
	private BasicQuery basicQuery;	
	
	@Mock		
	private List<Feature> features;
				
	@Mock
	private LockImport lockImport;
	
	@Mock 
	private DateTime now;
	
	@Mock
	private List<LockImport> lockImportList;
	
	@Mock
	private Map<String,String> properties;
		
	private final static String collectionMap = "map_app_";
	
	private final String collectionLock = "lock_import_process";
	
			
	@Test	
	public void importRepository() throws Exception {
				
		String appIdentifier = "1";
		String collectionNameMap = collectionMap+appIdentifier;

		List<Feature> featureCollection = new ArrayList<Feature>();
		Feature feature = new Feature();
		Point point = new Point();
		List<Double> coordinates = new ArrayList<Double>();
		coordinates.add(-3.8889018);
		coordinates.add(40.5020356);
		point.setCoordinates(coordinates);
		feature.setGeometry(point);
		feature.setId("102855090");
		properties = new HashMap<String,String>();
		properties.put("building","glass");
		feature.setProperties(properties);		
		featureCollection.add(feature);
		 		
		//Fixture
		Mockito.when(mongoTemplate.collectionExists(collectionNameMap)).thenReturn(true);
		Mockito.when(mongoTemplate.getCollection(collectionNameMap)).thenReturn(dbCollection);
		PowerMockito.whenNew(BasicDBObject.class).withNoArguments().thenReturn(obj);
		Mockito.when(obj.put("geometry", "2dsphere")).thenReturn(obj);			
		
		//Experimentation
		importRepositoryCustom.saveGeoJson(appIdentifier, featureCollection);		
				
		//Expectation
		verify(mongoTemplate).dropCollection(collectionNameMap);	
		verify(mongoTemplate).createCollection(collectionNameMap);		
		verify(mongoTemplate).save(feature,collectionNameMap);
	
				
		
	}
	
	@Test	
	public void importRepositoryGraphIndoor() throws Exception {
						
		String appIdentifier = "1";
		String collectionNameMap = collectionMap+appIdentifier;

		List<Feature> featureCollection = new ArrayList<Feature>();
		Feature feature = new Feature();
		Point point = new Point();
		List<Double> coordinates = new ArrayList<Double>();
		coordinates.add(-3.8889018);
		coordinates.add(40.5020356);
		point.setCoordinates(coordinates);
		feature.setGeometry(point);
		feature.setId("102855090");
		properties = new HashMap<String,String>();
		properties.put("@graphIndoor","true");
		feature.setProperties(properties);
		featureCollection.add(feature);
		 		
		//Fixture
		Mockito.when(mongoTemplate.collectionExists(collectionNameMap)).thenReturn(true);
		Mockito.when(mongoTemplate.getCollection(collectionNameMap)).thenReturn(dbCollection);
		PowerMockito.whenNew(BasicDBObject.class).withNoArguments().thenReturn(obj);
		Mockito.when(obj.put("geometry", "2dsphere")).thenReturn(obj);						        
        		
		//Experimentation
		importRepositoryCustom.saveGeoJson(appIdentifier, featureCollection);
		
				
		//Expectation
		verify(mongoTemplate).dropCollection(collectionNameMap);	
		verify(mongoTemplate).createCollection(collectionNameMap);		
	}
	
	
	//@Test(expected=MongoGeospatialException.class)
	@Test
	public void importRepositoryMongoGeospatialExceptionThenContinue() throws Exception {
		
		String appIdentifier = "1";		
		String collectionNameMap = collectionMap+appIdentifier;

		List<Feature> featureCollection = new ArrayList<Feature>();
		featureCollection.add(feature1);			 
		
		//Fixture		
		Mockito.when(mongoTemplate.collectionExists(collectionNameMap)).thenReturn(true);
		Mockito.when(mongoTemplate.getCollection(collectionNameMap)).thenReturn(dbCollection);
		PowerMockito.whenNew(BasicDBObject.class).withNoArguments().thenReturn(obj);
		Mockito.when(obj.put("geometry", "2dsphere")).thenReturn(obj);
		
		Mockito.when(feature1.getGeometry()).thenReturn(geometry1);					
		Mockito.doThrow(UncategorizedMongoDbException.class).when(mongoTemplate).save(feature1,collectionNameMap);		
										
		//Experimentation
		importRepositoryCustom.saveGeoJson(appIdentifier, featureCollection );
				
		//Expectation		
		verify(mongoTemplate).dropCollection(collectionNameMap);	
		verify(mongoTemplate).createCollection(collectionNameMap);
		verify(dbCollection).ensureIndex(obj);
		verify(mongoTemplate).save(feature1,collectionNameMap);
	}
	

	//@Test(expected=JsonMapperFromFeatureException.class)
	@Test
	public void importRepositoryJsonMapperFromFeatureException() throws Exception {
		
		String appIdentifier = "1";
		String collectionNameMap = collectionMap+appIdentifier;
		List<Feature> featureCollection = new ArrayList<Feature>();
		featureCollection.add(feature1);			 
		
		//Fixture		
		Mockito.when(mongoTemplate.collectionExists(collectionNameMap)).thenReturn(true);
		Mockito.when(mongoTemplate.getCollection(collectionNameMap)).thenReturn(dbCollection);
		PowerMockito.whenNew(BasicDBObject.class).withNoArguments().thenReturn(obj);
		Mockito.when(obj.put("geometry", "2dsphere")).thenReturn(obj);	
		
		Mockito.when(feature1.getGeometry()).thenReturn(geometry1);					
		Mockito.doThrow(JsonProcessingException.class).when(mongoTemplate).save(feature1,collectionNameMap);		
						
		//Experimentation
		importRepositoryCustom.saveGeoJson(appIdentifier, featureCollection );
		
		
		//Expectation		
		verify(mongoTemplate).dropCollection(collectionNameMap);	
		verify(mongoTemplate).createCollection(collectionNameMap);	
		verify(dbCollection).ensureIndex(obj);
		verify(mongoTemplate).save(feature1,collectionNameMap);
	}

	@Test	
	public void lockImportProcess() throws Exception{
		
		
		String idApplication = "1";		
		String strNow = DateTime.now().toString();
		
		//Fixture
		Mockito.when(mongoTemplate.collectionExists(collectionLock)).thenReturn(true);
		Mockito.when(mongoTemplate.getCollection(collectionLock)).thenReturn(dbCollection);
		PowerMockito.whenNew(LockImport.class).withNoArguments().thenReturn(lockImport);
		PowerMockito.mockStatic(DateTime.class);
		Mockito.when(DateTime.now()).thenReturn(now);
		Mockito.when(now.toString()).thenReturn(strNow);
				
						
		//Experimentation
		importRepositoryCustom.lockImportProcess(idApplication);
		
		//Expectations
		verify(lockImport).setAppIdentifier(idApplication);		
		verify(lockImport).setTimeStamp(strNow);
		verify(mongoTemplate).save(lockImport,collectionLock);
		
	}
	
	@Test
	public void isImportProcessLocked() throws Exception{
		
		String idApplication = "1";		
		String strQuery = "{ '_id':'" + idApplication + "'}";
		
		//Fixture
		Query query=Mockito.mock(Query.class);	    
		PowerMockito.whenNew(BasicQuery.class).withArguments(strQuery).thenReturn(basicQuery);								
		Mockito.when(mongoTemplate.find(query, LockImport.class, collectionLock)).thenReturn(lockImportList);
		Mockito.when(lockImportList.get(0)).thenReturn(lockImport);
						
		//Experimentation
		importRepositoryCustom.isImportProcessLocked(idApplication);
		
		//Expectations
		
		
		
	}
	
	@Test
	public void isImportProcessLockedSpentTheTime() throws Exception{
		
		String idApplication = "1";		
		String strQuery = "{ '_id':'" + idApplication + "'}";	
		String strNow = DateTime.now().toString();
		
		
		//Fixture
		Query query=Mockito.mock(Query.class);	    
		PowerMockito.whenNew(BasicQuery.class).withArguments(strQuery).thenReturn(basicQuery);								
		Mockito.when(mongoTemplate.find(query, LockImport.class, collectionLock)).thenReturn(lockImportList);		
		Mockito.when(mongoTemplate.collectionExists(collectionLock)).thenReturn(true);
		Mockito.when(mongoTemplate.getCollection(collectionLock)).thenReturn(dbCollection);
		PowerMockito.whenNew(LockImport.class).withNoArguments().thenReturn(lockImport);
		PowerMockito.mockStatic(DateTime.class);
		Mockito.when(DateTime.now()).thenReturn(now);
		Mockito.when(now.toString()).thenReturn(strNow);
						
		//Experimentation
		importRepositoryCustom.isImportProcessLocked(idApplication);
		
		//Expectations		
		
		
	}
	
	@Test
	public void deleteLockImportProcess() throws Exception{
		
		String idApplication = "1";		
		String strQuery = "{ '_id':'" + idApplication + "'}";
		
		//Fixture		
		PowerMockito.whenNew(BasicQuery.class).withArguments(strQuery).thenReturn(basicQuery);	
		Mockito.when(mongoTemplate.collectionExists(collectionLock)).thenReturn(true);
										
		//Experimentation
		importRepositoryCustom.deleteLockImportProcess(idApplication);
		
		//Expectations
		verify(mongoTemplate).collectionExists(collectionLock);
		verify(mongoTemplate).remove(basicQuery,collectionLock);
		
	}	
	
		
	
}
