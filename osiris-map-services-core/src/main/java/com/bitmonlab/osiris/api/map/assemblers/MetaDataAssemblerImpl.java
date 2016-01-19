package com.bitmonlab.osiris.api.map.assemblers;

import javax.inject.Named;

import com.bitmonlab.osiris.api.map.transferobject.MetaDataDTO;
import com.bitmonlab.commons.api.map.model.geojson.MetaData;
import com.bitmonlab.core.assembler.AssemblyException;
import com.bitmonlab.core.assembler.SimpleAssembler;

@Named("MetaDataAssembler")
public class MetaDataAssemblerImpl extends SimpleAssembler<MetaDataDTO, MetaData>{
	
	
	public MetaDataAssemblerImpl() {
		super(MetaDataDTO.class, MetaData.class);		
	}

	@Override
	public MetaData createDomainObject(MetaDataDTO metaDataDTO) throws AssemblyException{
		
		MetaData metaData = new MetaData();
		
		metaData.setOSMChecksum(metaDataDTO.getOSMChecksum());
		metaData.setRoutingChecksum(metaDataDTO.getRoutingChecksum());
		metaData.setMaxlat(Double.valueOf(metaDataDTO.getMaxLatitude()));
		metaData.setMaxlon(Double.valueOf(metaDataDTO.getMaxLongitude()));
		metaData.setMinlon(Double.valueOf(metaDataDTO.getMinLongitude()));
		metaData.setMinlat(Double.valueOf(metaDataDTO.getMinLatitude()));
				
		return metaData;
		
	}
	
	@Override
	public MetaDataDTO createDataTransferObject(MetaData metaData) throws AssemblyException{
		
		MetaDataDTO metaDataDTO = new MetaDataDTO();
		
		metaDataDTO.setOSMChecksum(metaData.getOSMChecksum());	
		metaDataDTO.setRoutingChecksum(metaData.getRoutingChecksum());	
		metaDataDTO.setMaxLatitude(String.valueOf(metaData.getMaxlat()));
		metaDataDTO.setMaxLongitude(String.valueOf(metaData.getMaxlon()));
		metaDataDTO.setMinLatitude(String.valueOf(metaData.getMinlat()));
		metaDataDTO.setMinLongitude(String.valueOf(metaData.getMinlon()));
		metaDataDTO.setAppId(metaData.getAppId());
		
		return metaDataDTO;
		
	}

}
