package com.bitmonlab.osiris.api.core.map.assemblers;

import java.util.Map;

import javax.inject.Named;



import com.bitmonlab.osiris.api.core.map.transferobject.RoomDTO;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;
import com.bitmonlab.osiris.core.assembler.SimpleAssembler;

@Named("RoomAssembler")
public class RoomAssemblerImpl extends SimpleAssembler<RoomDTO, Feature>{
	
	public RoomAssemblerImpl() {
		super(RoomDTO.class,Feature.class);
	}
	
	@Override
	public RoomDTO createDataTransferObject(Feature room){
		Map<String,String> properties=room.getProperties();
		String roomName=properties.get("name");
		
		RoomDTO roomDTO=new RoomDTO();
		roomDTO.setRoomName(roomName);
		
		return roomDTO;		
	}

}
