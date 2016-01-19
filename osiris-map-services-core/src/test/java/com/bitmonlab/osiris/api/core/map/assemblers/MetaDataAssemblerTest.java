package com.bitmonlab.osiris.api.core.map.assemblers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.bitmonlab.osiris.api.core.map.assemblers.MetaDataAssemblerImpl;
import com.bitmonlab.osiris.api.core.map.transferobject.MetaDataDTO;
import com.bitmonlab.osiris.commons.map.model.geojson.MetaData;
import com.bitmonlab.osiris.core.assembler.AssemblyException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MetaDataAssemblerImpl.class})
public class MetaDataAssemblerTest {
	
	@InjectMocks	
	private MetaDataAssemblerImpl metaDataAssemblerImpl;
	
	@Mock
	private MetaData metaData;
	
	@Mock
	private MetaDataDTO metaDataDTO;
	

	@Test
	public void transformMetaDataToMetaDataDTO() throws AssemblyException{
		
		String chkSum = "BF46EC8EDC949779798B752A849262A96FE20D5619D294CBB7605E594CDFDB5F3686219B9F56BF7760D2D70EBB62C76569781F0E9A5E8020721465E059398148";
		String chkSumRouting = "checksumRouting";
		Double minLat = 41.5609602;
		Double minLon = -8.3979911;
		Double maxLat = 41.5618352;
		Double maxLon = -8.3967036;
		String appId="1";
			
		//Fixture
		Mockito.when(metaData.getOSMChecksum()).thenReturn(chkSum);
		Mockito.when(metaData.getRoutingChecksum()).thenReturn(chkSumRouting);
		Mockito.when(metaData.getMaxlat()).thenReturn(maxLat);
		Mockito.when(metaData.getMaxlon()).thenReturn(maxLon);
		Mockito.when(metaData.getMinlat()).thenReturn(minLat);		
		Mockito.when(metaData.getMinlon()).thenReturn(minLon);
		Mockito.when(metaData.getAppId()).thenReturn(appId);
				
		
		//Experimentation
		MetaDataDTO metaDataDTOResponse = metaDataAssemblerImpl.createDataTransferObject(metaData);
				
		//Expectation
		assertEquals("OSMCheckSum must be the same", metaDataDTOResponse.getOSMChecksum(), chkSum);
		assertEquals("RoutingCheckSum must be the same", metaDataDTOResponse.getRoutingChecksum(), chkSumRouting);
		assertEquals("Max. Latitude must be the same", metaDataDTOResponse.getMaxLatitude(), String.valueOf(maxLat));
		assertEquals("Min. Latitude must be the same", metaDataDTOResponse.getMinLatitude(), String.valueOf(minLat));
		assertEquals("Max. Longitude must be the same", metaDataDTOResponse.getMaxLongitude(), String.valueOf(maxLon));
		assertEquals("Min. Longitude must be the same", metaDataDTOResponse.getMinLongitude(), String.valueOf(minLon));
		assertEquals("AppId must be the same", appId, metaDataDTOResponse.getAppId());
			
	}
	
	@Test
	public void transformMetaDataDTOtoMetaData() throws AssemblyException {
		
		String chkSum = "BF46EC8EDC949779798B752A849262A96FE20D5619D294CBB7605E594CDFDB5F3686219B9F56BF7760D2D70EBB62C76569781F0E9A5E8020721465E059398148";
		String chkSumRouting = "checksumRouting";
		String minLat = "41.5609602";
		String minLon = "-8.3979911";
		String maxLat = "41.5618352";
		String maxLon = "-8.3967036";
		
		//Fixture		
		Mockito.when(metaDataDTO.getOSMChecksum()).thenReturn(chkSum);
		Mockito.when(metaDataDTO.getRoutingChecksum()).thenReturn(chkSumRouting);
		Mockito.when(metaDataDTO.getMaxLatitude()).thenReturn(maxLat);
		Mockito.when(metaDataDTO.getMaxLongitude()).thenReturn(maxLon);
		Mockito.when(metaDataDTO.getMinLongitude()).thenReturn(minLon);
		Mockito.when(metaDataDTO.getMinLatitude()).thenReturn(minLat);
		
				
		//Experimentation
		MetaData metaDataResponse = metaDataAssemblerImpl.createDomainObject(metaDataDTO);
			
				
		//Expectation
		assertEquals("MapCheckSum must be the same", metaDataResponse.getOSMChecksum(), chkSum);
		assertEquals("RoutingCheckSum must be the same", metaDataResponse.getRoutingChecksum(), chkSumRouting);
		assertEquals("Max. Latitude must be the same", metaDataResponse.getMaxlat(), Double.valueOf(maxLat));
		assertEquals("Min. Latitude must be the same", metaDataResponse.getMinlat(), Double.valueOf(minLat));
		assertEquals("Max. Longitude must be the same", metaDataResponse.getMaxlon(), Double.valueOf(maxLon));
		assertEquals("Min. Longitude must be the same", metaDataResponse.getMinlon(), Double.valueOf(minLon));
		
		
	}
	
	
}
