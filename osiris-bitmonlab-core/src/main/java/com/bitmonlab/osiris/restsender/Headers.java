package com.bitmonlab.osiris.restsender;

public class Headers {

	private String key;
	private String value;
	
	public Headers(String k, String v){
		this.key = k;
		this.value = v;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
