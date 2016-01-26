package com.bitmonlab.osiris.api.map.rest.api;

import javax.ws.rs.core.Response;

import com.bitmonlab.osiris.api.core.map.exceptions.FeatureNotExistException;
import com.bitmonlab.osiris.api.core.map.exceptions.MongoGeospatialException;
import com.bitmonlab.osiris.api.core.map.transferobject.FeatureDTO;
import com.bitmonlab.osiris.core.assembler.AssemblyException;

public interface FeatureResource {
	 	 
	Response storeFeature(String appIdentifier, FeatureDTO featureDTO) throws AssemblyException, MongoGeospatialException;
	 	 
	Response deleteFeature(String appIdentifier, String idFeature) throws FeatureNotExistException;
	 
	Response updateFeature(String appIdentifier, String idFeature, FeatureDTO featureDTO) throws AssemblyException, FeatureNotExistException, MongoGeospatialException;

	Response getFeatureByID(String appIdentifier, String idFeature) throws AssemblyException, FeatureNotExistException;
	  
}
