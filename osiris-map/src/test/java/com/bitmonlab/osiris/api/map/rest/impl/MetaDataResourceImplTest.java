package com.bitmonlab.osiris.api.map.rest.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.bitmonlab.core.validations.validator.Validations;
import com.bitmonlab.osiris.api.map.assemblers.MetaDataAssemblerImpl;
import com.bitmonlab.osiris.api.map.managers.impl.MetaDataManagerImpl;
import com.bitmonlab.osiris.api.map.rest.impl.MetaDataResourceImpl;
import com.bitmonlab.osiris.api.map.transferobject.MetaDataDTO;
import com.bitmonlab.commons.api.map.model.geojson.MetaData;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MetaDataResourceImpl.class})
public class MetaDataResourceImplTest {
	
	@InjectMocks
	private MetaDataResourceImpl metaDataResourceImpl;
	
	@Mock
	private MetaDataManagerImpl metaDataManagerImpl;
	
	@Mock
	private MetaDataAssemblerImpl metaDataAssemblerImpl;
	
	@Mock
	private MetaData metaData;
	
	@Mock
	private MetaDataDTO metaDataDTO;
	
	@Mock
	private Validations validations;
	
	@Test
	public void getMetaData() throws Exception{
				
		String appIdentifier = "1";
		
		//Fixture
		Mockito.when(metaDataManagerImpl.getMetaData(appIdentifier)).thenReturn(metaData);
		Mockito.when(metaDataAssemblerImpl.createDataTransferObject(metaData)).thenReturn(metaDataDTO);
		
		//Experimentation
		Response response = metaDataResourceImpl.getMetaData(appIdentifier);
				
		//Expectation	
		Mockito.verify(validations).checkIsNotNullAndNotBlank(appIdentifier);
		assertNotNull(response.getEntity());
		assertEquals("FeatureDTO is not the expected", metaDataDTO, (MetaDataDTO) response.getEntity());
		assertEquals("The Status response is not the expected", 200, response.getStatus());
		
	}
	

}
