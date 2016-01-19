package com.bitmonlab.osiris.imports.map.dao.api;

import java.util.Collection;

import com.bitmonlab.osiris.commons.map.model.geojson.Feature;
import com.bitmonlab.osiris.imports.map.exceptions.QueryException;

public interface ImportRepositoryCustom {
	
	String saveGeoJson(String appIdentifier, Collection<Feature> featureCollection);
		
	void lockImportProcess(String appIdentifier);
	
	boolean isImportProcessLocked(String appIdentifier) throws QueryException;
	
	void deleteLockImportProcess(String appIdentifier) throws QueryException;

}
