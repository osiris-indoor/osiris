package com.bitmonlab.osiris.api.map.dao.api;

import java.util.Collection;

import com.bitmonlab.osiris.api.map.exceptions.QueryException;
import com.bitmonlab.commons.api.map.model.geojson.Feature;

public interface MapRepositoryCustom {
		
	Collection<Feature> searchIDAppAndQuery(String appIdentifier, String query, Integer pageIndex, Integer pageSize) throws QueryException;

	Collection<Feature> searchIDAppAndQuery(String appIdentifier, String query, Integer pageIndex, Integer pageSize, 
										String orderField, String order) throws QueryException;

}
