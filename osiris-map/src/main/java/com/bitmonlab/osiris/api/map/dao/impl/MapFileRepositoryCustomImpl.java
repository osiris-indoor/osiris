package com.bitmonlab.osiris.api.map.dao.impl;

import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.bitmonlab.osiris.api.map.dao.api.MapFileRepositoryCustom;
import com.bitmonlab.osiris.api.map.exceptions.MapFileNotExistsException;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

@Named
public class MapFileRepositoryCustomImpl implements MapFileRepositoryCustom{
	
	@Inject
	@Named("osirisGeolocationMongoTemplate")
	private MongoTemplate mongoTemplate;
	
	private String collectionNameMap = "files_map";

	@Override
	public InputStream getMapFileByAppId(String appIdentifier) throws MapFileNotExistsException{
		// TODO Auto-generated method stub
		GridFS gridFS = getGridFS(collectionNameMap);
		InputStream fileMap=getMapFileByAppId(gridFS,appIdentifier);
		return fileMap;
	}
	
	private GridFS getGridFS(String collectionName){
		DB db=mongoTemplate.getDb();		
		GridFS gridFS = new GridFS(db, collectionName);
		return gridFS;
	}
	private InputStream getMapFileByAppId(GridFS gridFS,String appIdentifier) throws MapFileNotExistsException{
		InputStream fileMap=null;
		GridFSDBFile gridFSFileMap = gridFS.findOne(appIdentifier);
		if(gridFSFileMap==null){
			throw new MapFileNotExistsException();
		}
		fileMap=gridFSFileMap.getInputStream();
		return fileMap;
	}
	


	
	
}
