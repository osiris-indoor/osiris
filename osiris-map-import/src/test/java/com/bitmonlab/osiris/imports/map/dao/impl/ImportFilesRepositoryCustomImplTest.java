package com.bitmonlab.osiris.imports.map.dao.impl;

import java.io.File;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.bitmonlab.osiris.imports.map.dao.impl.ImportFilesRepositoryCustomImpl;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ImportFilesRepositoryCustomImpl.class,GridFS.class})
public class ImportFilesRepositoryCustomImplTest {

	private String collectionNameMap = "files_map";
	
	private String collectionNameOSM = "files_osm";
	
	private String collectionNameObj = "files_obj";
	
	@InjectMocks
	private ImportFilesRepositoryCustomImpl importFilesRepositoryCustomImpl;
	
	@Mock
	private MongoTemplate mongoTemplate;
	
	@Mock
	private File file;
	
	private String idApp = "appIDTest";
	
	@Mock
	private DB db;
	
	@Mock
	private GridFS gridFS;
	
	@Mock
	private GridFSInputFile gridFSInputFile;
	
	@Mock
	private GridFSDBFile gridFSFile;
	
	@Mock
	private InputStream inputStream;
	
	@Test
	public void saveFileMap() throws Exception{
		
		//Fixture
		Mockito.when(mongoTemplate.getDb()).thenReturn(db);
		PowerMockito.whenNew(GridFS.class).withArguments(db, collectionNameMap).thenReturn(gridFS);		
		Mockito.when(gridFS.findOne(idApp)).thenReturn(gridFSFile);
		Mockito.when(gridFS.createFile(file)).thenReturn(gridFSInputFile);
				
		// Experimentations
		importFilesRepositoryCustomImpl.saveFileMap(idApp,file);
		
		// Expectations
		Mockito.verify(gridFS).remove(gridFSFile);
		Mockito.verify(gridFS).createFile(file);
		Mockito.verify(gridFSInputFile).setFilename(idApp);
		Mockito.verify(gridFSInputFile).save();
	}
	
	@Test
	public void saveFileMapWithoutRemoving() throws Exception{
		
		//Fixture
		Mockito.when(mongoTemplate.getDb()).thenReturn(db);
		PowerMockito.whenNew(GridFS.class).withArguments(db, collectionNameMap).thenReturn(gridFS);		
		Mockito.when(gridFS.findOne(idApp)).thenReturn(null);
		Mockito.when(gridFS.createFile(file)).thenReturn(gridFSInputFile);
				
		// Experimentations
		importFilesRepositoryCustomImpl.saveFileMap(idApp,file);
		
		// Expectations
		Mockito.verify(gridFS).createFile(file);
		Mockito.verify(gridFSInputFile).setFilename(idApp);
		Mockito.verify(gridFSInputFile).save();
	}
	
	@Test
	public void saveFileOSM() throws Exception{
		
		//Fixture
		Mockito.when(mongoTemplate.getDb()).thenReturn(db);
		PowerMockito.whenNew(GridFS.class).withArguments(db, collectionNameOSM).thenReturn(gridFS);		
		Mockito.when(gridFS.findOne(idApp)).thenReturn(gridFSFile);
		Mockito.when(gridFS.createFile(file)).thenReturn(gridFSInputFile);
				
		// Experimentations
		importFilesRepositoryCustomImpl.saveFileOSM(idApp,file);
		
		// Expectations
		Mockito.verify(gridFS).remove(gridFSFile);
		Mockito.verify(gridFS).createFile(file);
		Mockito.verify(gridFSInputFile).setFilename(idApp);
		Mockito.verify(gridFSInputFile).save();
	}
	
	@Test
	public void saveFileOSMWithoutRemoving() throws Exception{
		
		//Fixture
		Mockito.when(mongoTemplate.getDb()).thenReturn(db);
		PowerMockito.whenNew(GridFS.class).withArguments(db, collectionNameOSM).thenReturn(gridFS);		
		Mockito.when(gridFS.findOne(idApp)).thenReturn(null);
		Mockito.when(gridFS.createFile(file)).thenReturn(gridFSInputFile);
				
		// Experimentations
		importFilesRepositoryCustomImpl.saveFileOSM(idApp,file);
		
		// Expectations
		Mockito.verify(gridFS).createFile(file);
		Mockito.verify(gridFSInputFile).setFilename(idApp);
		Mockito.verify(gridFSInputFile).save();
	}
	
	@Test
	public void saveFileObj() throws Exception{
		
		//Fixture
		Mockito.when(mongoTemplate.getDb()).thenReturn(db);
		PowerMockito.whenNew(GridFS.class).withArguments(db, collectionNameObj).thenReturn(gridFS);		
		Mockito.when(gridFS.findOne(idApp)).thenReturn(gridFSFile);
		Mockito.when(gridFS.createFile(file)).thenReturn(gridFSInputFile);
				
		// Experimentations
		importFilesRepositoryCustomImpl.saveFileObj(idApp,file);
		
		// Expectations
		Mockito.verify(gridFS).remove(gridFSFile);
		Mockito.verify(gridFS).createFile(file);
		Mockito.verify(gridFSInputFile).setFilename(idApp);
		Mockito.verify(gridFSInputFile).save();
	}
	
	@Test
	public void saveFileObjWithoutRemoving() throws Exception{
		
		//Fixture
		Mockito.when(mongoTemplate.getDb()).thenReturn(db);
		PowerMockito.whenNew(GridFS.class).withArguments(db, collectionNameObj).thenReturn(gridFS);		
		Mockito.when(gridFS.findOne(idApp)).thenReturn(null);
		Mockito.when(gridFS.createFile(file)).thenReturn(gridFSInputFile);
				
		// Experimentations
		importFilesRepositoryCustomImpl.saveFileObj(idApp,file);
		
		// Expectations
		Mockito.verify(gridFS).createFile(file);
		Mockito.verify(gridFSInputFile).setFilename(idApp);
		Mockito.verify(gridFSInputFile).save();
	}

	
}