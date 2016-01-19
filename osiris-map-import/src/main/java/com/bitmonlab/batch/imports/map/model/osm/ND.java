package com.bitmonlab.batch.imports.map.model.osm;

import javax.xml.bind.annotation.XmlAttribute;

public class ND {
	
	private String  ref;

	public String getRef() {
		return ref;
	}

	@XmlAttribute 
	public void setRef(String ref) {
		this.ref = ref;
	}

	@Override
	public String toString() {
		return "ND [ref=" + ref + "]";
	}
	 

}
