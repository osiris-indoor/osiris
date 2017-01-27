package com.bitmonlab.osiris.api.map.rest.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bitmonlab.osiris.core.validations.validator.Validations;
import com.bitmonlab.osiris.api.core.map.exceptions.MapFileNotExistsException;
import com.bitmonlab.osiris.api.core.map.managers.impl.MapFileManagerImpl;
import com.bitmonlab.osiris.api.map.rest.impl.MapFileResourceImpl;
import com.bitmonlab.osiris.commons.model.security.BasicAuth;

@RunWith(MockitoJUnitRunner.class)
public class MapFileResourceImplTest {
	
	@InjectMocks
	private MapFileResourceImpl mapFileResourceImpl;
	
	@Mock
	private MapFileManagerImpl mapFileManagerImpl;
	
	@Mock
	private InputStream inputStream;
	
	@Mock
	private Validations validations;
	
	@Mock
	private BasicAuth principal;
	
	@Test
	public void getFileMap() throws MapFileNotExistsException{
		//Fixture
		String appIdentifier = "1";
		Mockito.when(mapFileManagerImpl.getMapFile(appIdentifier)).thenReturn(inputStream);
		
		//Experimentation
		Response response=mapFileResourceImpl.getMapFile(principal, appIdentifier) ;
		
		//Expectations
		Mockito.verify(validations).checkIsNotNullAndNotBlank(appIdentifier);
		Mockito.verify(mapFileManagerImpl).getMapFile(appIdentifier);
		assertNotNull(response.getEntity());
		assertEquals("The object response is not the expected", inputStream, (InputStream) response.getEntity());
		assertEquals("The Status response is not the expected", 200, response.getStatus());
	}

}
