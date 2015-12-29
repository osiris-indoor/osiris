package com.bitmonlab.osiris.api.map.managers.api;

import com.bitmonlab.osiris.api.map.exceptions.MetaDataNotExistsException;
import com.bitmonlab.commons.api.map.model.geojson.MetaData;



public interface MetaDataManager {
	
	MetaData getMetaData(String appIdentifier) throws MetaDataNotExistsException;

}
