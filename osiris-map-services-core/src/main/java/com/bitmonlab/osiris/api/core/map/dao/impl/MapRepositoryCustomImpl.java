package com.bitmonlab.osiris.api.core.map.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

import com.bitmonlab.osiris.api.core.map.dao.api.MapRepositoryCustom;
import com.bitmonlab.osiris.api.core.map.exceptions.QueryException;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;
import com.bitmonlab.osiris.commons.map.model.geojson.LineString;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Named
public class MapRepositoryCustomImpl implements MapRepositoryCustom {
	
	@Inject
	@Named("mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	private final static String collectionPrefixMap = "map_app_";
	
	@Override
	public List<Feature> searchByLocation(String appIdentifier,Double longitude, Double latitude, Integer floor) {
		// TODO Auto-generated method stub
		List<Feature> result=new ArrayList<Feature>();
		
		if(mongoTemplate.collectionExists(collectionPrefixMap+appIdentifier)){
			DBCollection dbCollection = mongoTemplate.getCollection(collectionPrefixMap+appIdentifier); 
			DBObject query=getQuerySearchByLocation(longitude, latitude, floor);
			
			DBCursor cursor=dbCollection.find(query);
			
			while(cursor.hasNext()){
				DBObject obj=cursor.next();
				Feature feature=mongoTemplate.getConverter().read(Feature.class, obj);
				result.add(feature);
			}
		}
				
		return result;		
	}

	@Override
	public Collection<Feature> searchIDAppAndQuery(String appIdentifier,
			String query, Integer pageIndex, Integer pageSize) throws QueryException{
		// TODO Auto-generated method stub
		
		Query mongoQuery = createQuery(query,pageIndex, pageSize);
			
		Collection<Feature> features = mongoTemplate.find(mongoQuery, Feature.class, collectionPrefixMap + appIdentifier);
		
		return features;
	}

	@Override
	public Collection<Feature> searchIDAppAndQuery(String appIdentifier,
			String query, Integer pageIndex, Integer pageSize,
			String orderField, String order) throws QueryException{
		
		// TODO Auto-generated method stub
		
		Query mongoQuery = createQuery(query,pageIndex, pageSize).
						                      with(new Sort(Sort.Direction.valueOf(order), orderField));
						
		Collection<Feature> features = mongoTemplate.find(mongoQuery, Feature.class, collectionPrefixMap + appIdentifier);
				
		return features;
	}
	
	private Query createQuery(String query,Integer pageIndex, Integer pageSize) throws QueryException{
		
		BasicQuery basicquery;
		
		try{	
			basicquery = new BasicQuery(query);
		}catch(Exception e) {
			throw new QueryException();
		}
		
		
		Integer skipElements = pageIndex * pageSize;
	
		
		Query mongoQuery = basicquery.skip(skipElements.intValue()).limit(pageSize);
		
		return mongoQuery;		
		
	}
	
	private DBObject getQuerySearchByLocation(Double longitude, Double latitude, Integer floor){
		DBObject queryFeatureRooms = new BasicDBObject();
		
		DBObject queryNearer=createQueryConditionNearer(longitude,latitude);
		DBObject queryIndoor=createQueryConditionIsIndoor();
		DBObject queryIndoorIsNotLevel=createQueryConditionIndoorIsNotLevel();
		DBObject queryIndoorIsNotPath=createQueryConditionIndoorIsNotPath();
		queryFeatureRooms.put("geometry", queryNearer);
		queryFeatureRooms.put("properties.indoor", queryIndoor);
		queryFeatureRooms.put("properties.indoor", queryIndoorIsNotLevel);
		queryFeatureRooms.put("properties.level", floor.toString());
		queryFeatureRooms.put("geometry._class", LineString.class.getName());
		queryFeatureRooms.put("properties.highway", queryIndoorIsNotPath);
		queryFeatureRooms.put("$where", "this.geometry.coordinates.length>3");
		
		return queryFeatureRooms;
	}
	
	private DBObject createQueryConditionNearer(Double longitude, Double latitude){
		
		Double[] coords=new Double[2];
		coords[0]=longitude;
		coords[1]=latitude;
		
		DBObject pointQuery = new BasicDBObject();	
		pointQuery.put("type", "Point");
		pointQuery.put("coordinates", coords);
		
		DBObject geometryAndDistanceQuery = new BasicDBObject();
		geometryAndDistanceQuery.put("$geometry", pointQuery);
		
		DBObject nearQuery=new BasicDBObject("$near",geometryAndDistanceQuery);
		
		return nearQuery;		
	}	
		
	private DBObject createQueryConditionIsIndoor(){
		DBObject queryIndoor = new BasicDBObject();
		queryIndoor.put("$exists",true);
		return queryIndoor;		
	}
	
	private DBObject createQueryConditionIndoorIsNotLevel(){
		DBObject queryIndoorIsNotLevel = new BasicDBObject();
		queryIndoorIsNotLevel.put("$ne","level");
		return queryIndoorIsNotLevel;		
	}
	
	private DBObject createQueryConditionIndoorIsNotPath(){
		DBObject queryIndoorIsNotPath = new BasicDBObject();
		queryIndoorIsNotPath.put("$ne","footway");
		return queryIndoorIsNotPath;		
	}
	
	
	
	
}
