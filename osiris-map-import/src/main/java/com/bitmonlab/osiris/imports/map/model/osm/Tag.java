package com.bitmonlab.osiris.imports.map.model.osm;
import javax.xml.bind.annotation.XmlAttribute;

public class Tag implements Comparable<Tag>
{
	private String k;
	private String v;


	

public String getK() {
	return k;
}

@XmlAttribute 
public void setK(String k) {
	this.k = k;
}


public String getV() {
	return v;
}

@XmlAttribute 
public void setV(String v) {
	this.v = v;
}

@Override
public String toString() {
	return "Tag [k=" + k + ", v=" + v + "]";
}

@Override
public int compareTo(Tag o) {
	// TODO Auto-generated method stub	
	
	return k.compareTo(o.getK());
}


}
