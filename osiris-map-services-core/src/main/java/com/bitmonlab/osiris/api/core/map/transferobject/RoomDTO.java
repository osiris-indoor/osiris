package com.bitmonlab.osiris.api.core.map.transferobject;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Room of a building")
public class RoomDTO {
	
	@NotNull
	@NotBlank
	@ApiModelProperty(value="Name of room", required=true)
	private String roomName;

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}	
	

}
