package com.bitmonlab.osiris.api.map.dao.api;

import java.io.InputStream;

import com.bitmonlab.osiris.api.map.exceptions.MapFileNotExistsException;

public interface MapFileRepositoryCustom {

	InputStream getMapFileByAppId(String appIdentifier) throws MapFileNotExistsException;
}
