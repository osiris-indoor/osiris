package com.bitmonlab.osiris.api.core.map.dao.api;

import javax.inject.Named;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bitmonlab.osiris.api.core.map.dao.api.FeatureRepositoryCustom;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;

@Named
public interface FeatureRepository extends MongoRepository<Feature, String> , FeatureRepositoryCustom {
	
}
