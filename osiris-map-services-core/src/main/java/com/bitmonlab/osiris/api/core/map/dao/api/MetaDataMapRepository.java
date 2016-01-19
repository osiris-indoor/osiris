package com.bitmonlab.osiris.api.core.map.dao.api;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bitmonlab.osiris.commons.map.model.geojson.MetaData;



public interface MetaDataMapRepository extends MongoRepository<MetaData, String>{

}
