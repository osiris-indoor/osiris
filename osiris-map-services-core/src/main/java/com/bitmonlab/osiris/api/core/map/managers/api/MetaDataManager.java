package com.bitmonlab.osiris.api.core.map.managers.api;

import com.bitmonlab.osiris.api.core.map.exceptions.MetaDataNotExistsException;
import com.bitmonlab.osiris.commons.map.model.geojson.MetaData;



public interface MetaDataManager {
	
	MetaData getMetaData(String appIdentifier) throws MetaDataNotExistsException;

}
