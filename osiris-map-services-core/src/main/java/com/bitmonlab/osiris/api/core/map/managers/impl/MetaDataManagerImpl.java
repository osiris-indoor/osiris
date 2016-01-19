package com.bitmonlab.osiris.api.core.map.managers.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.bitmonlab.osiris.api.core.map.dao.api.MetaDataMapRepository;
import com.bitmonlab.osiris.api.core.map.exceptions.MetaDataNotExistsException;
import com.bitmonlab.osiris.api.core.map.managers.api.MetaDataManager;
import com.bitmonlab.osiris.commons.map.model.geojson.MetaData;

@Named
public class MetaDataManagerImpl implements MetaDataManager{

	@Inject
	private MetaDataMapRepository metaDataRepository;
	
	@Override
	public MetaData getMetaData(String appIdentifier) throws MetaDataNotExistsException {

		MetaData metaData = metaDataRepository.findOne(appIdentifier);
		
		if (metaData==null){
			throw new MetaDataNotExistsException();
		}
		
		return metaData;
	}
	

}
