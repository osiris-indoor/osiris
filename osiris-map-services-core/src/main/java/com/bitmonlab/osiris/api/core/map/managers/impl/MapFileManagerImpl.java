package com.bitmonlab.osiris.api.core.map.managers.impl;

import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Named;

import com.bitmonlab.osiris.api.core.map.dao.api.MapFileRepository;
import com.bitmonlab.osiris.api.core.map.exceptions.MapFileNotExistsException;
import com.bitmonlab.osiris.api.core.map.managers.api.MapFileManager;

@Named
public class MapFileManagerImpl implements MapFileManager {

	@Inject
	private MapFileRepository mapFileRepository;
	
	@Override
	public InputStream getMapFile(String appIdentifier)
			throws MapFileNotExistsException {
	
		InputStream fileMap=mapFileRepository.getMapFileByAppId(appIdentifier);
		return fileMap;
	}
	
	
	
	
	

}
