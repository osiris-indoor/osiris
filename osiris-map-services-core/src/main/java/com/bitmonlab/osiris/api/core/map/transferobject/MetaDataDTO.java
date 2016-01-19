package com.bitmonlab.osiris.api.core.map.transferobject;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Metadata of map")
public class MetaDataDTO {
	
	@NotNull
	@NotBlank
	@ApiModelProperty(value="Checksum of osm file", required=true)
	private String osmChecksum;
	
	@NotNull
	@NotBlank
	@ApiModelProperty(value="Checksum of routing file", required=true)
	private String routingChecksum;
	
	@ApiModelProperty(value="Minimum latitude of map", required=false)
	private String minLatitude;
	
	@ApiModelProperty(value="Minimum longitude of map", required=false)
	private String minLongitude;
	
	@ApiModelProperty(value="Maximum latitude of map", required=false)
	private String maxLatitude;
	
	@ApiModelProperty(value="Maximum longitude of map", required=false)
	private String maxLongitude;
	
	@NotNull
	@NotBlank
	@ApiModelProperty(value="Application identifier of map", required=true)
	private String appId;

	public String getOSMChecksum() {
		return osmChecksum;
	}

	public void setOSMChecksum(String chkSum) {
		this.osmChecksum = chkSum;
	}

	public String getMinLatitude() {
		return minLatitude;
	}

	public void setMinLatitude(String minLatitude) {
		this.minLatitude = minLatitude;
	}

	public String getMinLongitude() {
		return minLongitude;
	}

	public void setMinLongitude(String minLongitude) {
		this.minLongitude = minLongitude;
	}

	public String getMaxLatitude() {
		return maxLatitude;
	}

	public void setMaxLatitude(String maxLatitude) {
		this.maxLatitude = maxLatitude;
	}

	public String getMaxLongitude() {
		return maxLongitude;
	}

	public void setMaxLongitude(String maxLongitude) {
		this.maxLongitude = maxLongitude;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getRoutingChecksum() {
		return routingChecksum;
	}

	public void setRoutingChecksum(String routingChecksum) {
		this.routingChecksum = routingChecksum;
	}		
	
}
