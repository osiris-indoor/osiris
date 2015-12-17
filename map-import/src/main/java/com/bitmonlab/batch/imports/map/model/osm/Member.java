package com.bitmonlab.batch.imports.map.model.osm;

import javax.xml.bind.annotation.XmlAttribute;

public class Member implements Comparable<Member>{

   private String type;
   private String ref;
   private String role;
   
   
   
   
public String getType() {
	return type;
}

@XmlAttribute 
public void setType(String type) {
	this.type = type;
}
public String getRef() {
	return ref;
}

@XmlAttribute 
public void setRef(String ref) {
	this.ref = ref;
}
public String getRole() {
	return role;
}

@XmlAttribute 
public void setRole(String role) {
	this.role = role;
}

@Override
public String toString() {
	return "Member [type=" + type + ", ref=" + ref + ", role=" + role + "]";
}

public int compareTo(Member o) {	
	return ref.compareTo(o.getRef());
}

   
}
