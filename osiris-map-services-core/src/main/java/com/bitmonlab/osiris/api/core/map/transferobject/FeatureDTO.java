package com.bitmonlab.osiris.api.core.map.transferobject;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.bitmonlab.osiris.api.core.map.transferobject.GeometryDTO;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Feature")
public class FeatureDTO {
	
	@ApiModelProperty(value="Set of properties", required=false)
	private Map<String,String> properties;	
	
	@ApiModelProperty(value="Parents properties", required=false)
	private List<Map<String,String>> propertiesRelations;
	
	@ApiModelProperty(value="Id of feature", required=false)
	private String id;

	@Valid
	@NotNull
	@ApiModelProperty(value="Geometry of feature", required=true)
	private GeometryDTO geometryDTO;

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	
	public List<Map<String, String>> getPropertiesRelations() {
		return propertiesRelations;
	}

	public void setPropertiesRelations(List<Map<String, String>> propertiesRelations) {
		this.propertiesRelations = propertiesRelations;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GeometryDTO getGeometryDTO() {
		return geometryDTO;
	}

	public void setGeometryDTO(GeometryDTO geometryDTO) {
		this.geometryDTO = geometryDTO;
	}
	
		
		
}
