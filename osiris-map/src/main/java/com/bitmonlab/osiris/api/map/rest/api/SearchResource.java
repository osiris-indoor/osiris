package com.bitmonlab.osiris.api.map.rest.api;

import javax.ws.rs.core.Response;

import com.bitmonlab.osiris.api.map.exceptions.QueryException;
import com.bitmonlab.osiris.api.map.transferobject.LayerDTO;
import com.bitmonlab.core.assembler.AssemblyException;

public interface SearchResource {
	
	Response getFeaturesByQuery(String appIdentifier, String query, LayerDTO layer, Integer pageIndex,Integer pageSize, String orderField, String order) throws AssemblyException, QueryException;

}
