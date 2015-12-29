package com.bitmonlab.osiris.api.map.dao.impl;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.bitmonlab.osiris.api.map.dao.impl.MapFileRepositoryCustomImpl;
import com.bitmonlab.osiris.api.map.exceptions.MapFileNotExistsException;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MapFileRepositoryCustomImpl.class,GridFS.class})
public class MapFileRepositoryCustoImplTest {

	private String collectionNameMap = "files_map";
	
	@InjectMocks
	private MapFileRepositoryCustomImpl mapFileRepositoryCustomImpl;
	
	@Mock
	private MongoTemplate mongoTemplate;
		
	private String idApp = "appIDTest";
	
	@Mock
	private DB db;
	
	@Mock
	private GridFS gridFS;
	
	@Mock
	private GridFSDBFile gridFSFile;
	
	@Mock
	private InputStream inputStream;
	
	@Test
	public void getFileMapByAppId() throws Exception{
		
		//Fixture
		Mockito.when(mongoTemplate.getDb()).thenReturn(db);
		PowerMockito.whenNew(GridFS.class).withArguments(db,collectionNameMap).thenReturn(gridFS);		
		Mockito.when(gridFS.findOne(idApp)).thenReturn(gridFSFile);
		Mockito.when(gridFSFile.getInputStream()).thenReturn(inputStream);
		
		// Experimentations
		InputStream response=mapFileRepositoryCustomImpl.getMapFileByAppId(idApp);
		
		// Expectations
		Mockito.verify(gridFS).findOne(idApp);
		Mockito.verify(gridFSFile).getInputStream();
		Assert.assertEquals("File .map must be the same",inputStream,response);
	}	
	
	@Test(expected=MapFileNotExistsException.class)	
	public void getFileMapByAppIdWithoutMapFile() throws Exception{
		
		//Fixture
		Mockito.when(mongoTemplate.getDb()).thenReturn(db);
		PowerMockito.whenNew(GridFS.class).withArguments(db,collectionNameMap).thenReturn(gridFS);		
		Mockito.when(gridFS.findOne(idApp)).thenReturn(null);
		
		// Experimentations
		mapFileRepositoryCustomImpl.getMapFileByAppId(idApp);
		
		// Expectations
		Mockito.verify(gridFS).findOne(idApp);
	}	
	
}
