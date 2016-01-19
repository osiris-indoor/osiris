package com.bitmonlab.osiris.imports.map.dao.api;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.bitmonlab.osiris.commons.map.model.geojson.Feature;

public interface ImportRepository extends MongoRepository<Feature, String> , ImportRepositoryCustom {

	

		
}
