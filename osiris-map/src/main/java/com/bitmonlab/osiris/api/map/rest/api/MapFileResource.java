package com.bitmonlab.osiris.api.map.rest.api;

import javax.ws.rs.core.Response;

import com.bitmonlab.osiris.api.map.exceptions.MapFileNotExistsException;

public abstract interface MapFileResource
{

  Response getMapFile(String appIdentifier) throws MapFileNotExistsException;

}
