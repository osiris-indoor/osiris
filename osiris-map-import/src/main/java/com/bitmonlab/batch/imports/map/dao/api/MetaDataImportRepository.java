package com.bitmonlab.batch.imports.map.dao.api;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bitmonlab.commons.api.map.model.geojson.MetaData;

public interface MetaDataImportRepository extends MongoRepository<MetaData, String>{

}
