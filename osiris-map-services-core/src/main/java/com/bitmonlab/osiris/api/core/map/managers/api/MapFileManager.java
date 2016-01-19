package com.bitmonlab.osiris.api.core.map.managers.api;

import java.io.InputStream;

import com.bitmonlab.osiris.api.core.map.exceptions.MapFileNotExistsException;

public interface MapFileManager {
	
	InputStream getMapFile(String appIdentifier) throws MapFileNotExistsException;

}
