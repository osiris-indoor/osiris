package com.bitmonlab.osiris.commons.map.model.geojson;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Metadata")
public class MetaData {

	@NotNull
	@NotBlank
	@Id
	private String appId;
	
	@NotNull
	@NotBlank
	private String osmChecksum;
	
	@NotNull
	@NotBlank
	private String routingChecksum;
		
	
	
	private Double minlat;
	
	private Double minlon;
	
	private Double maxlat;
	
	private Double maxlon;	

	public String getOSMChecksum() {
		return osmChecksum;
	}

	public void setOSMChecksum(String chkSum) {
		this.osmChecksum = chkSum;
	}

	public Double getMinlat() {
		return minlat;
	}
	
	public void setMinlat(Double minlat) {
		this.minlat = minlat;
	}
	public Double getMinlon() {
		return minlon;
	}
	
	public void setMinlon(Double minlon) {
		this.minlon = minlon;
	}
	public Double getMaxlat() {
		return maxlat;
	}
	
	public void setMaxlat(Double maxlat) {
		this.maxlat = maxlat;
	}
	public Double getMaxlon() {
		return maxlon;
	}
	
	public void setMaxlon(Double maxlon) {
		this.maxlon = maxlon;
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
