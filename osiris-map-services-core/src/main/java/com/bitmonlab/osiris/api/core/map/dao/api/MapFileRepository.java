package com.bitmonlab.osiris.api.core.map.dao.api;

import java.io.File;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bitmonlab.osiris.api.core.map.dao.api.MapFileRepositoryCustom;

public interface MapFileRepository extends MongoRepository<File, String>, MapFileRepositoryCustom{

	
}
