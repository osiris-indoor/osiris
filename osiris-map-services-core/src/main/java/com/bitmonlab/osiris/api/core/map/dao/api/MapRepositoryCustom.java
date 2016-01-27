package com.bitmonlab.osiris.api.core.map.dao.api;

import java.util.Collection;
import java.util.List;

import com.bitmonlab.osiris.api.core.map.exceptions.QueryException;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;

public interface MapRepositoryCustom {
		
	Collection<Feature> searchIDAppAndQuery(String appIdentifier, String query, Integer pageIndex, Integer pageSize) throws QueryException;

	Collection<Feature> searchIDAppAndQuery(String appIdentifier, String query, Integer pageIndex, Integer pageSize, 
										String orderField, String order) throws QueryException;

	List<Feature> searchByLocation(String appIdentifier, Double longitude,
			Double latitude, Integer floor);

}
