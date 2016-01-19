package com.bitmonlab.osiris.commons.map.model.geojson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
//import org.springframework.data.mongodb.core.index.CompoundIndexes;
//import org.springframework.data.mongodb.core.index.CompoundIndex;

import org.springframework.data.annotation.Id;

/*@CompoundIndexes({
    @CompoundIndex(name = "geometryindex", def= "{'geometry' : '2dsphere'}")
})*/
//@Named
public class Feature implements Comparable<Feature>{
	
	@Id
	private String id;
		
	
	private final String type="Feature";
	
	@NotNull	
	private Geometry geometry;	
	private Map<String,String> properties;
	
    private List<Map<String,String>> propertiesRelations = new ArrayList<Map<String,String>>();
    
    public Feature(Geometry geometry,Map<String,String> properties){
    	this.geometry=geometry;
    	this.properties=properties;
    }
    
    public Feature(){
    	
    }
	 
	public List<Map<String, String>> getPropertiesRelations() {
		return propertiesRelations;
	}

	public void setPropertiesRelations(
			List<Map<String, String>> propertiesRelations) {			
	 if(propertiesRelations != null){			
			for(Map<String, String> property : propertiesRelations){
				this.propertiesRelations.add(property);
			}		
	 	}	 
	}	
	
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	
	public Map<String, String> getProperties() {
		return properties;
	}
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}


	public void updateProperties(final List<Map<String,String>> newInheritedProperties) {

			boolean exist = false;
			
			

			for (Map<String,String> propertiesNews: newInheritedProperties) {				
				Iterator<Map<String, String>> it =this.propertiesRelations.iterator();				
				while (!exist && it.hasNext()) {
					Map<String, String> propertiesFeature = it.next();
					if (propertiesFeature.get("@id").equals(propertiesNews.get("@id"))) {
						exist = true;
					}
				}
				if (!exist) {
					this.propertiesRelations.add(propertiesNews);
				}
				exist = false;
			}

	}
	
	public List<Map<String, String>> getAllProperties() {

		   List<Map<String, String>> allProperties = new ArrayList<Map<String, String>>();

		   allProperties.add(this.properties);  

		   if (propertiesRelations != null) {
			   for (Map<String, String> propertiesRelation: propertiesRelations) {
				   allProperties.add(propertiesRelation);		   
			   }
		    }

		   return allProperties;

	}

	public boolean searchProperty(String property) {

		boolean found = false;   
		
		 if(properties.containsKey(property)){
			 found = true;
		 }else if (propertiesRelations != null){		 
			 int j = 0;			 
			 while (j < propertiesRelations.size() && !found) {
			    Map<String,String> propertyRelation = propertiesRelations.get(j);
			    if(propertyRelation.containsKey(property)){
			    	found = true;
			    }			    	
				j++;
			 }
		 }
		
		 return found;

	}
	
	public String getValueRelationProperties(String property){
		String value=null;
		for(Map<String,String> propertyRelation:propertiesRelations){
			value=propertyRelation.get(property);
			if(value!=null){
				break;
			}						
		}
		return value;
	}
	

	@Override
	public String toString() {
		return "Feature [id=" + id + ", type=" + type + ", geometry="
				+ geometry + ", properties=" + properties
				+ ", propertiesRelations=" + propertiesRelations + "]";
	}

	public int compareTo(Feature f) {
	
		return id.compareTo(f.getId());

	}
	
	

}
