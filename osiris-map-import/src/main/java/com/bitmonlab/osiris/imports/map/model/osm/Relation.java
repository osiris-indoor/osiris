package com.bitmonlab.osiris.imports.map.model.osm;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.joda.time.DateTime;

@XmlRootElement(name = "relation")
@XmlType(propOrder = { "id", "timestamp", "uid", "user", "visible", "version", "changeset", "members", "tags"})
public class Relation  implements Comparable<Relation>{

	
	 private String id;	 
	 private String visible;
	 private String timestamp=new DateTime().toString();
	 private String version="1";
	 private String changeset;
	 private String user;
	 private String uid;	 
	 private List<Member> members;
	 private List<Tag> tags;
	 
	 
	 public Relation(){}
	 
	 public Relation(String id){
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
	public List<Member> getMembers() {
		return members;
	}
	@XmlElement(name="member")
	public void setMembers(List<Member> members) {
		this.members = members;
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
		return "Relation [id=" + id + ", visible=" + visible + ", timestamp="
				+ timestamp + ", version=" + version + ", changeset="
				+ changeset + ", user=" + user + ", uid=" + uid + ", members="
				+ members + ", tags=" + tags + "]";
	}
	public int compareTo(Relation o) {		
		return id.compareTo(o.getId());
	}

	
	
    
}
