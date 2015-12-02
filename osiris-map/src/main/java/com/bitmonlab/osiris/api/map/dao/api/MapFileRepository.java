package com.bitmonlab.osiris.api.map.dao.api;

import java.io.File;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MapFileRepository extends MongoRepository<File, String>, MapFileRepositoryCustom{

	
}
