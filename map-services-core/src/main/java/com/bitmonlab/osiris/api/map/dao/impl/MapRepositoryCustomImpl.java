package com.bitmonlab.osiris.api.map.dao.impl;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

import com.bitmonlab.osiris.api.map.dao.api.MapRepositoryCustom;
import com.bitmonlab.osiris.api.map.exceptions.QueryException;
import com.bitmonlab.commons.api.map.model.geojson.Feature;

@Named
public class MapRepositoryCustomImpl implements MapRepositoryCustom {
	
	@Inject
	@Named("mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	private final static String collectionPrefixMap = "map_app_";

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
	
	
	
}
