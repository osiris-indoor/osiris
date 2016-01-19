package com.bitmonlab.osiris.api.map.transferobject;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Point", parent=GeometryDTO.class)
public class PointDTO extends GeometryDTO{
	
	@NotNull
	@ApiModelProperty(value="Latitude", required=true, allowableValues="Between -90 inclusive and 90 inclusive")
	private Double latitude;
	
	@NotNull	
	@ApiModelProperty(value="Longitude", required=true, allowableValues="Between -180 inclusive and 180 inclusive")
	private Double longitude;
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}	

	
}
