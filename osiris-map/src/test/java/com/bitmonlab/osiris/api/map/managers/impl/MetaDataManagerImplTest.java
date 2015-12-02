package com.bitmonlab.osiris.api.map.managers.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bitmonlab.osiris.api.map.dao.api.MetaDataMapRepository;
import com.bitmonlab.osiris.api.map.exceptions.MetaDataNotExistsException;
import com.bitmonlab.osiris.api.map.managers.impl.MetaDataManagerImpl;
import com.bitmonlab.commons.api.map.model.geojson.MetaData;



@RunWith(MockitoJUnitRunner.class)
public class MetaDataManagerImplTest {
	
	@InjectMocks
	private MetaDataManagerImpl metaDataManagerImpl;
		
	@Mock
	private MetaDataMapRepository metaDataRepository;
	
	@Mock
	private MetaData metaData;
	
	@Test
	public void getMetaDataExist() throws MetaDataNotExistsException{	
		
		 String idApplication = "9";  
		
		//Fixture	
		Mockito.when(metaDataRepository.findOne(idApplication)).thenReturn(metaData);
			
		//Experimentation
		MetaData response=metaDataManagerImpl.getMetaData(idApplication);	
		
		//Expectations
		Mockito.verify(metaDataRepository).findOne(idApplication);
		Assert.assertEquals("Response must be equals", metaData,response);
	}
	
	@Test(expected=MetaDataNotExistsException.class)
	public void getMetaDataNotExist() throws MetaDataNotExistsException{	
		
		String idApplication = "9";  
			
		//Fixture	
		Mockito.when(metaDataRepository.findOne(idApplication)).thenReturn(null);
				
		//Experimentation
		metaDataManagerImpl.getMetaData(idApplication);	
			
		//Expectations
		Mockito.verify(metaDataRepository).findOne(idApplication);
	} 

}
