package com.bitmonlab.osiris.imports.map.model.osm;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.joda.time.DateTime;


@XmlRootElement(name = "node")
@XmlType(propOrder = { "id", "timestamp", "uid", "user", "visible", "version", "changeset", "lat", "lon", "tags" })
public class Node implements Comparable<Node>, Tags{ 
	
	 private String id;
	 private String visible="true";
	 private String timestamp=new DateTime().toString();
	 private String version="1";
	 private String changeset;
	 private String user;
	 private String uid;
	 private String lat;
	 private String lon;
	 private List<Tag> tags;
	
	 public Node(){}
	 
	 public Node(String id){
		 this.id = id;
	 }
	
	public String getId() {
		return id;
	}
	
	@XmlAttribute
	public void setId(String id) {
		this.id = id;
	}
	public String getVisible() {
		return visible;
	}
	
	@XmlAttribute
	public void setVisible(String visible) {
		this.visible = visible;
	}
	public String getTimestamp() {
		return timestamp;
	}
	
	@XmlAttribute
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getVersion() {
		return version;
	}
	
	@XmlAttribute
	public void setVersion(String version) {
		this.version = version;
	}
	public String getChangeset() {
		return changeset;
	}
	
	@XmlAttribute
	public void setChangeset(String changeset) {
		this.changeset = changeset;
	}
	public String getUser() {
		return user;
	}
	
	@XmlAttribute
	public void setUser(String user) {
		this.user = user;
	}
	public String getUid() {
		return uid;
	}
	
	@XmlAttribute
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getLat() {
		return lat;
	}
	
	@XmlAttribute
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	
	@XmlAttribute
	public void setLon(String lon) {
		this.lon = lon;
	}


		
	public List<Tag> getTags() {
		return tags;
	}
	
	
	@XmlElement (name="tag")
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	public boolean existTag(String eTag){
	        
		boolean exists =false;
	    if(tags!=null){    
		
			for(Tag tag : tags){
		            
				if(tag.getK().equals(eTag)){
					exists = true;
				}
			}
	    }
	        
	    return exists;
	
	}
	
	
	public List<Double> getNodeCoordinates() {

		 List<Double> coordinates = new ArrayList<Double>();

		 coordinates.add(Double.valueOf(this.lon)); 
		 coordinates.add(Double.valueOf(this.lat));

		 return coordinates;
	}
	
    
	@Override
	public String toString() {
		return "Node [id=" + id + ", visible=" + visible + ", timestamp="
				+ timestamp + ", version=" + version + ", changeset="
				+ changeset + ", user=" + user + ", uid=" + uid + ", lat="
				+ lat + ", lon=" + lon + ", tags=" + tags + "]";
	}

	public int compareTo(Node o) {		
		return id.compareTo(o.getId());
	}

	


}

