package com.bitmonlab.osiris.api.map.rest.api;

import javax.ws.rs.core.Response;

import com.bitmonlab.osiris.api.map.exceptions.MetaDataNotExistsException;
import com.bitmonlab.core.assembler.AssemblyException;

public interface MetaDataResource {
	
	Response getMetaData(String appIdentifier) throws AssemblyException, MetaDataNotExistsException;
		

}
