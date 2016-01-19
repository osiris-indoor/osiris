package com.bitmonlab.osiris.imports.map.dao.api;

import java.io.File;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImportFilesRepository extends MongoRepository<File, String>, ImportFilesRepositoryCustom{

	
}
