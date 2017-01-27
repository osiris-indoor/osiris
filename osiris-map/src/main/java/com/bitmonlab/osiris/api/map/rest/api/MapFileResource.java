package com.bitmonlab.osiris.api.map.rest.api;

import javax.ws.rs.core.Response;

import com.bitmonlab.osiris.api.core.map.exceptions.MapFileNotExistsException;
import com.bitmonlab.osiris.commons.model.security.BasicAuth;

public abstract interface MapFileResource
{

	Response getMapFile(BasicAuth principal, String appIdentifier) throws MapFileNotExistsException;

}
