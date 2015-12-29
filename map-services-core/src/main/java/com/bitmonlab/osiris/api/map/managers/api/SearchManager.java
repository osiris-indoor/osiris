package com.bitmonlab.osiris.api.map.managers.api;

import java.util.Collection;

import com.bitmonlab.osiris.api.map.exceptions.QueryException;
import com.bitmonlab.osiris.api.map.transferobject.LayerDTO;
import com.bitmonlab.commons.api.map.model.geojson.Feature;

public interface SearchManager {
	
	Collection<Feature> getFeaturesByQuery(String appIdentifier,
			String query, LayerDTO layer, Integer pageIndex, Integer pageSize) throws QueryException;

	Collection<Feature> getFeaturesByQuery(String appIdentifier,
			String query, LayerDTO layer, Integer pageIndex, Integer pageSize,
			String orderField, String order) throws QueryException;

}
