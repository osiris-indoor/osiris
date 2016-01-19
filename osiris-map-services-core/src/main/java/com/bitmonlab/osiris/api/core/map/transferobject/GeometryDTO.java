package com.bitmonlab.osiris.api.core.map.transferobject;

import com.bitmonlab.osiris.api.core.map.transferobject.LineStringDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.PointDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.PolygonDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wordnik.swagger.annotations.ApiModel;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({
	@Type(value=PointDTO.class, name="point"),
	@Type(value=LineStringDTO.class, name="lineString"),
	@Type(value=PolygonDTO.class, name="polygon"),
})
@ApiModel(description = "Geometry",subTypes={PointDTO.class,LineStringDTO.class,PolygonDTO.class})
public class GeometryDTO {

}
