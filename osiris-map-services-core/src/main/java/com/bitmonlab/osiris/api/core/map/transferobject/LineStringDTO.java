package com.bitmonlab.osiris.api.core.map.transferobject;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.bitmonlab.osiris.api.core.map.transferobject.GeometryDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.PointDTO;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "LineString", parent=GeometryDTO.class)
public class LineStringDTO extends GeometryDTO{

	@Valid
	@NotNull
	@ApiModelProperty(value="Collection of points", required=true)
	private Collection<PointDTO> collectionPointDTO;	
	
	@Valid
	@ApiModelProperty(value="Centroid of lineString", required=false)
	private PointDTO centroidDTO; 
	
	
	public Collection<PointDTO> getCollectionPointDTO() {
		return collectionPointDTO;
	}

	public void setCollectionPointDTO(Collection<PointDTO> collectionPointDTO) {
		this.collectionPointDTO = collectionPointDTO;
	}
	
	public PointDTO getCentroidDTO() {
		return centroidDTO;
	}

	public void setCentroidDTO(PointDTO centroidDTO) {
		this.centroidDTO = centroidDTO;
	}
	
}
