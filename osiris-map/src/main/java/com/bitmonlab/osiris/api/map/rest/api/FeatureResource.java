package com.bitmonlab.osiris.api.map.rest.api;

import javax.ws.rs.core.Response;

import com.bitmonlab.osiris.api.core.map.exceptions.FeatureNotExistException;
import com.bitmonlab.osiris.api.core.map.exceptions.MongoGeospatialException;
import com.bitmonlab.osiris.api.core.map.transferobject.FeatureDTO;
import com.bitmonlab.osiris.commons.model.security.BasicAuth;
import com.bitmonlab.osiris.core.assembler.AssemblyException;

public interface FeatureResource {
	 	 
	Response storeFeature(BasicAuth principal,String appIdentifier, FeatureDTO featureDTO) throws AssemblyException, MongoGeospatialException;
	 	 
	Response deleteFeature(BasicAuth principal,String appIdentifier, String idFeature) throws FeatureNotExistException;
	 
	Response updateFeature(BasicAuth principal,String appIdentifier, String idFeature, FeatureDTO featureDTO) throws AssemblyException, FeatureNotExistException, MongoGeospatialException;

	Response getFeatureByID(BasicAuth principal,String appIdentifier, String idFeature) throws AssemblyException, FeatureNotExistException;

	
	  
}
