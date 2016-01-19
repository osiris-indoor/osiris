package com.bitmonlab.osiris.imports.map.dao.api;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bitmonlab.osiris.commons.map.model.geojson.MetaData;

public interface MetaDataImportRepository extends MongoRepository<MetaData, String>{

}
