package com.bitmonlab.batch.imports.map.dao.api;

import java.util.Collection;

import com.bitmonlab.commons.api.map.model.geojson.Feature;
import com.bitmonlab.batch.imports.map.exceptions.QueryException;

public interface ImportRepositoryCustom {
	
	String saveGeoJson(String appIdentifier, Collection<Feature> featureCollection);
		
	void lockImportProcess(String appIdentifier);
	
	boolean isImportProcessLocked(String appIdentifier) throws QueryException;
	
	void deleteLockImportProcess(String appIdentifier) throws QueryException;

}
