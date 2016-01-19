package com.bitmonlab.osiris.api.map.managers.api;

import java.io.InputStream;

import com.bitmonlab.osiris.api.map.exceptions.MapFileNotExistsException;

public interface MapFileManager {
	
	InputStream getMapFile(String appIdentifier) throws MapFileNotExistsException;

}
