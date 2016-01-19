package com.bitmonlab.batch.imports.map.model.osm;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "bounds")
@XmlType(propOrder = { "minlat", "minlon", "maxlat", "maxlon","origin"})
public class Bounds {
	
	private String minlat= "90";
	private String minlon= "180";
	private String maxlat= "-90";
	private String maxlon= "-180";
	private String origin;
	
	public String getMinlat() {
		return minlat;
	}
	@XmlAttribute
	public void setMinlat(String minlat) {
		this.minlat = minlat;
	}
	public String getMinlon() {
		return minlon;
	}
	@XmlAttribute
	public void setMinlon(String minlon) {
		this.minlon = minlon;
	}
	public String getMaxlat() {
		return maxlat;
	}
	@XmlAttribute
	public void setMaxlat(String maxlat) {
		this.maxlat = maxlat;
	}
	
	public String getMaxlon() {
		return maxlon;
	}
	
	@XmlAttribute
	public void setMaxlon(String maxlon) {
		this.maxlon = maxlon;
	}
	
	public String getOrigin() {
		return origin;
	}
	@XmlAttribute
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	
	

}
