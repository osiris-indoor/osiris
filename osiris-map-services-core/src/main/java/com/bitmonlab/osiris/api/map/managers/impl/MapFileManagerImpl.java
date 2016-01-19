package com.bitmonlab.osiris.api.map.managers.impl;

import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Named;

import com.bitmonlab.osiris.api.map.dao.api.MapFileRepository;
import com.bitmonlab.osiris.api.map.exceptions.MapFileNotExistsException;
import com.bitmonlab.osiris.api.map.managers.api.MapFileManager;

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
