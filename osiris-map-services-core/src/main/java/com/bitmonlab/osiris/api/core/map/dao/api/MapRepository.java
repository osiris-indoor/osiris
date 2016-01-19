package com.bitmonlab.osiris.api.core.map.dao.api;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bitmonlab.osiris.api.core.map.dao.api.MapRepositoryCustom;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;

public interface MapRepository extends MongoRepository<Feature, String> , MapRepositoryCustom {

	

		
}
