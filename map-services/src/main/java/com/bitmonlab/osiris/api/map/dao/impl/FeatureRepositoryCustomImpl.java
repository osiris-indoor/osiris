package com.bitmonlab.osiris.api.map.dao.impl;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

import com.bitmonlab.osiris.api.map.dao.api.FeatureRepositoryCustom;
import com.bitmonlab.osiris.api.map.exceptions.MongoGeospatialException;
import com.bitmonlab.osiris.api.map.exceptions.QueryException;
import com.bitmonlab.commons.api.map.model.geojson.Feature;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;


@Named
public class FeatureRepositoryCustomImpl implements FeatureRepositoryCustom{

	@Inject
	@Named("mongoTemplate")
	private MongoTemplate mongoTemplate;

	private final static String collectionName = "feature_app_";
		
	@Override
	public Feature save(String idApplication, Feature feature) throws MongoGeospatialException {
		// TODO Auto-generated method stub
		if(!mongoTemplate.collectionExists(collectionName+idApplication)){
			DBCollection dbCollection = mongoTemplate.getCollection(collectionName+idApplication); 
			DBObject obj = new BasicDBObject();		
			obj.put("geometry", "2dsphere");
			dbCollection.ensureIndex(obj);
		}
		try{
			mongoTemplate.save(feature, collectionName+idApplication);
		}
		catch(UncategorizedMongoDbException uncategorizedMongoDbException){
			throw new MongoGeospatialException();
		}
		return feature;
	}
	
	@Override
	public void delete(String idApplication, String idFeature) {
		// TODO Auto-generated method stub
		Feature feature=mongoTemplate.findById(idFeature, Feature.class, collectionName+idApplication);
		mongoTemplate.remove(feature, collectionName+idApplication);
	}

	@Override
	public Feature findByApplicationIdentifierAndId(String idApplication, String idFeature) {
		return mongoTemplate.findById(idFeature, Feature.class, collectionName+idApplication);
	}
	
	@Override
	public Collection<Feature> searchIDAppAndQuery(String idApplication,String query,Integer pageIndex, Integer pageSize) throws QueryException {
		
		// TODO Auto-generated method stub
				
		Query mongoQuery = createQuery(query,pageIndex, pageSize);
		
		Collection<Feature> features = mongoTemplate.find(mongoQuery, Feature.class, collectionName+idApplication);
		
		return features;
		
	}
	
	@Override
	public Collection<Feature> searchIDAppAndQuery(String idApplication,String query,Integer pageIndex, Integer pageSize, String orderField, String order) throws QueryException {
		
		// TODO Auto-generated method stub
		
		Query mongoQuery = createQuery(query,pageIndex, pageSize).
				                      with(new Sort(Sort.Direction.valueOf(order), orderField));
						
		Collection<Feature> features = mongoTemplate.find(mongoQuery, Feature.class, collectionName+idApplication);
				
		return features;
	}

	private Query createQuery(String query,Integer pageIndex, Integer pageSize) throws QueryException{
		
		BasicQuery basicquery;
		
		try{	
			basicquery = new BasicQuery(query);
		}
		catch(Exception e) {
			throw new QueryException();
		}		
		
		Integer skipElements = pageIndex * pageSize;	
		
		Query mongoQuery = basicquery.skip(skipElements.intValue()).limit(pageSize);
		
		return mongoQuery;		
	}

}


