package com.bitmonlab.osiris.api.map.rest.api;

import javax.ws.rs.core.Response;

import com.bitmonlab.osiris.api.core.map.exceptions.MetaDataNotExistsException;
import com.bitmonlab.osiris.commons.model.security.BasicAuth;
import com.bitmonlab.osiris.core.assembler.AssemblyException;

public interface MetaDataResource {
	
	
	Response getMetaData(BasicAuth principal, String appIdentifier)
			throws AssemblyException, MetaDataNotExistsException;
		

}
