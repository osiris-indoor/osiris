package com.bitmonlab.osiris.api.core.map.managers.impl;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bitmonlab.osiris.api.core.map.dao.api.MapFileRepository;
import com.bitmonlab.osiris.api.core.map.exceptions.MapFileNotExistsException;
import com.bitmonlab.osiris.api.core.map.managers.impl.MapFileManagerImpl;

@RunWith(MockitoJUnitRunner.class)
public class MapFileManagerImplTest {
	
	@InjectMocks
	private MapFileManagerImpl mapFileManagerImpl;
	
	@Mock
	private MapFileRepository mapFileRepository;
	
	@Mock
	private InputStream inputStream;
		
	
	@Test
	public void getFileMap() throws MapFileNotExistsException{
		
		//Fixture
		String appIdentifier = "1";
		Mockito.when(mapFileRepository.getMapFileByAppId(appIdentifier)).thenReturn(inputStream);
						
		//Experimentation
		InputStream response=mapFileManagerImpl.getMapFile(appIdentifier);
		
		//Expectations	
		Mockito.verify(mapFileRepository).getMapFileByAppId(appIdentifier);
		Assert.assertEquals("Map must be the same",inputStream,response);
	}

}
