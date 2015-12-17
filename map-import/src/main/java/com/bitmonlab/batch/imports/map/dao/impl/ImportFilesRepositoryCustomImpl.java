package com.bitmonlab.batch.imports.map.dao.impl;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.bitmonlab.batch.imports.map.dao.api.ImportFilesRepositoryCustom;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Named
public class ImportFilesRepositoryCustomImpl implements ImportFilesRepositoryCustom{

	@Inject
	@Named("mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	private String collectionNameMap = "files_map";
	
	private String collectionNameOSM = "files_osm";

	@Override
	public void saveFileMap(String appIdentifier, File map) throws IOException{
		// TODO Auto-generated method stub
		GridFS gridFS = getGridFS(collectionNameMap);
		removeFile(appIdentifier,gridFS);		
		saveFile(appIdentifier,map,gridFS);
	}
	
	@Override
	public void saveFileOSM(String appIdentifier, File osm) throws IOException{
		// TODO Auto-generated method stub
		GridFS gridFS = getGridFS(collectionNameOSM);
		removeFile(appIdentifier,gridFS);		
		saveFile(appIdentifier,osm,gridFS);
	}
	
	private GridFS getGridFS(String collectionName){
		DB db=mongoTemplate.getDb();		
		GridFS gridFS = new GridFS(db, collectionName);
		return gridFS;
	}
	
	private void saveFile(String appIdentifier, File file, GridFS gridFS) throws IOException{
		GridFSInputFile gridFSInputFile = gridFS.createFile(file);
		gridFSInputFile.setFilename(appIdentifier);
		gridFSInputFile.save();
	}		
		
	private void removeFile(String appIdentifier,GridFS gridFS){
		GridFSDBFile gridFSFileRemoveFile = gridFS.findOne(appIdentifier);
		if(gridFSFileRemoveFile!=null){
			gridFS.remove(gridFSFileRemoveFile);
		}
	}

	
	
}
