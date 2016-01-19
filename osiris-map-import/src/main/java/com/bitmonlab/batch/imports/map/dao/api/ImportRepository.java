package com.bitmonlab.batch.imports.map.dao.api;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.bitmonlab.commons.api.map.model.geojson.Feature;

public interface ImportRepository extends MongoRepository<Feature, String> , ImportRepositoryCustom {

	

		
}
