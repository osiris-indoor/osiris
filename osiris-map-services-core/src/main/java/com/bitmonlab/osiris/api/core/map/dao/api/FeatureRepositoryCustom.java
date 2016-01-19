package com.bitmonlab.osiris.api.core.map.dao.api;

import java.util.Collection;

import com.bitmonlab.osiris.api.core.map.exceptions.MongoGeospatialException;
import com.bitmonlab.osiris.api.core.map.exceptions.QueryException;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;


public interface FeatureRepositoryCustom {
	
	Collection<Feature> searchIDAppAndQuery(String idApplication, String query, Integer pageIndex, Integer pageSize) throws QueryException;

	Collection<Feature> searchIDAppAndQuery(String idApplication, String query, Integer pageIndex, Integer pageSize,String orderField, String order) throws QueryException;  

	Feature save(String idApplication,Feature feature) throws MongoGeospatialException;
	
	void delete(String idApplication,String idFeature);
	
	Feature findByApplicationIdentifierAndId(String idApplication,String idFeature);
	
}
