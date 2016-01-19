package com.bitmonlab.osiris.imports.map.model.osm;


import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.joda.time.DateTime;

@XmlRootElement(name = "way")
@XmlType(propOrder = { "id", "action", "timestamp", "uid", "user", "visible", "version", "changeset", "nds", "tags"})
public class Way implements Comparable<Way>, Tags{
	
	 private String id;
	 private String action;
	 private String visible;
	 private String timestamp=new DateTime().toString();
	 private String version="1";
	 private String changeset;
	 private String user;
	 private String uid;	 
	 private List<ND> nds;
	 private List<Tag> tags;
	 
	 public Way(){}
	 
	 public Way(String id){
		 this.id = id;
	 }
	 
	public String getId() {
		return id;
	}
	@XmlAttribute
	public void setId(String id) {
		this.id = id;
	}
	public String getAction() {
		return action;
	}
	@XmlAttribute
	public void setAction(String action) {
		this.action = action;
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
	public List<ND> getNds() {
		return nds;
	}
	@XmlElement(name="nd")
	public void setNds(List<ND> nds) {
		this.nds = nds;
	}
	public List<Tag> getTags() {
		return tags;
	}
	@XmlElement(name="tag")
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	@Override
	public String toString() {
		return "Way [id=" + id + ", action=" + action + ", visible=" + visible
				+ ", timestamp=" + timestamp + ", version=" + version
				+ ", changeset=" + changeset + ", user=" + user + ", uid="
				+ uid + ", nds=" + nds + ", tags=" + tags + "]";
	}
	public int compareTo(Way o) {	
		return id.compareTo(o.getId());
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
	
 

}
