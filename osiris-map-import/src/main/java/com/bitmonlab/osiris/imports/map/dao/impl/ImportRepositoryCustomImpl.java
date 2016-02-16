package com.bitmonlab.osiris.imports.map.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;

import com.bitmonlab.osiris.commons.map.model.geojson.Feature;
import com.bitmonlab.osiris.commons.map.model.imports.LockImport;
import com.bitmonlab.osiris.imports.map.dao.api.ImportRepositoryCustom;
import com.bitmonlab.osiris.imports.map.exceptions.QueryException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Named
public class ImportRepositoryCustomImpl implements ImportRepositoryCustom {
	
	@Inject
	@Named("mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	private final static String collectionPrefixMap = "map_app_";
			
	private final String collectionLock = "lock_import_process";
	
	private Logger log = LoggerFactory.getLogger(ImportRepositoryCustomImpl.class);

	@Override
	public String saveGeoJson(String appIdentifier, Collection<Feature> featureCollection){
		 
		String collectionNameMap = collectionPrefixMap + appIdentifier;		 

		createCollection(collectionNameMap);

		String jsonStr = "";
		String jsonFeature ="";
		
		ObjectMapper mapper=new ObjectMapper();	;

		for(Feature feature: featureCollection){

			if (feature.getGeometry() != null) { 
					
				try{	
					
					mongoTemplate.save(feature, collectionNameMap);					
															
					StringBuilder stringBuilder =  new StringBuilder();					
					jsonFeature = mapper.writeValueAsString(feature); 
					jsonStr = stringBuilder.append(jsonStr).append(jsonFeature).append("\n").toString();
					
				}
				catch(UncategorizedMongoDbException exception){					
					//throw new MongoGeospatialException();
					log.error("---> Bad Mongo geometry collection Map: " + collectionNameMap);
					log.error("---> Bad Mongo geometry feature ID: " + feature.getId());
					try {
						log.error("---> Bad Mongo geometry feature: " + mapper.writeValueAsString(feature));
					} catch (JsonProcessingException e) {
						log.error("---> Bad Mongo geometry feature and Bad Json mapper: " + feature.getId());
						continue;
					}
					continue;
				}
				catch(JsonProcessingException exception){
					log.error("---> Bad Json mapper collection Map: " + collectionNameMap);
					log.error("---> Bad Json mapper feature ID: " + feature.getId());					
					//throw new JsonMapperFromFeatureException();
					continue;
				}
							 			
			}		
		}

		return jsonStr;
	}
	
	
	private void createCollection(String collectionName){
		
		 if(mongoTemplate.collectionExists(collectionName)){
			 mongoTemplate.dropCollection(collectionName);
			 mongoTemplate.createCollection(collectionName);			 
		 }else{
			 mongoTemplate.createCollection(collectionName);
		 }
		  				 
				 		
		 DBCollection dbCollection = mongoTemplate.getCollection(collectionName); 
		 DBObject obj = new BasicDBObject();		
		 obj.put("geometry", "2dsphere");		
		// DBObject objoptions = new BasicDBObject();
		// objoptions.put("2dsphereIndexVersion", 1);
		 dbCollection.ensureIndex(obj/*, objoptions*/);	
		 dbCollection.resetIndexCache();
		 
		
	}
		
		
	public void lockImportProcess(String appIdentifier){
				
				
		if(!mongoTemplate.collectionExists(collectionLock)){			
			mongoTemplate.createCollection(collectionLock);						
		}
		
		LockImport lockImport = new LockImport();
		lockImport.setAppIdentifier(appIdentifier);		
		lockImport.setTimeStamp(DateTime.now().toString());
		
		mongoTemplate.save(lockImport, collectionLock);
		
	}
	
	public boolean isImportProcessLocked(String appIdentifier) throws QueryException{
		
		    boolean ret = false;
		
			BasicQuery basicquery;
		
			try{
				
				basicquery = new BasicQuery("{ '_id':'" + appIdentifier + "'}");
				
			}catch(Exception e) {
				throw new QueryException();
			}
			
						
			List<LockImport> lockImportList = mongoTemplate.find(basicquery, LockImport.class, collectionLock);		
			
			if(!lockImportList.isEmpty()){						
				ret = true;
				LockImport lockImport = lockImportList.get(0);			
				DateTime dateLockImport = new DateTime(lockImport.getTimeStamp());			
				DateTime dateNow = new DateTime();			
				Interval interval = new Interval(dateLockImport, dateNow);
				
				if (interval.toDurationMillis() > 3600000){
					lockImportProcess(appIdentifier);					
					ret = false;
				}
			}
			
			return ret;
		
	}
	
	public void deleteLockImportProcess(String appIdentifier) throws QueryException{
		
		BasicQuery basicquery;
		
		try{
			
			basicquery = new BasicQuery("{ '_id':'" + appIdentifier + "'}");
			
		}catch(Exception e) {
			throw new QueryException();
		}
		
		
		if(mongoTemplate.collectionExists(collectionLock)){			
			mongoTemplate.remove(basicquery, collectionLock);						
		}		
				
		
	}		
	
}
