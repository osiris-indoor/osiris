package com.bitmonlab.osiris.api.core.map.assemblers;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import com.bitmonlab.osiris.api.core.map.exceptions.InvalidGeometry;
import com.bitmonlab.osiris.api.core.map.transferobject.FeatureDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.GeometryDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.LineStringDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.PointDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.PolygonDTO;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;
import com.bitmonlab.osiris.commons.map.model.geojson.Geometry;
import com.bitmonlab.osiris.commons.map.model.geojson.LineString;
import com.bitmonlab.osiris.commons.map.model.geojson.Point;
import com.bitmonlab.osiris.commons.map.model.geojson.Polygon;
import com.bitmonlab.osiris.core.assembler.Assembler;
import com.bitmonlab.osiris.core.assembler.AssemblyException;
import com.bitmonlab.osiris.core.assembler.SimpleAssembler;

@Named("FeatureAssembler")
public class FeatureAssemblerImpl extends SimpleAssembler<FeatureDTO, Feature>{
	
	@Inject 
	@Named("LineStringAssembler")
	private Assembler<LineStringDTO, LineString> lineStringAssembler;
	
	@Inject 
	@Named("PointAssembler")
	private Assembler<PointDTO, Point> pointAssembler;
	
	@Inject 
	@Named("PolygonAssembler")
	private Assembler<PolygonDTO, Polygon> polygonAssembler;

	public FeatureAssemblerImpl() {
		super(FeatureDTO.class,Feature.class);
	}
	
	@Override
	public Feature createDomainObject(FeatureDTO featureDTO) throws AssemblyException{
		
		Feature feature = new Feature();		
		
		feature.setProperties(featureDTO.getProperties());
		
		feature.setPropertiesRelations(featureDTO.getPropertiesRelations());
		
		GeometryDTO geometryDTO=featureDTO.getGeometryDTO();
		
		Geometry geometry=null;
		
		if(geometryDTO instanceof PointDTO){
			geometry=pointAssembler.createDomainObject((PointDTO)geometryDTO);
		}
		
		if(geometryDTO instanceof LineStringDTO){
			geometry=lineStringAssembler.createDomainObject((LineStringDTO)geometryDTO);
		}
		
		if(geometryDTO instanceof PolygonDTO){
			geometry=polygonAssembler.createDomainObject((PolygonDTO)geometryDTO);
		}
		
		if(geometry==null){
			throw new InvalidGeometry();
		}
							
		feature.setGeometry(geometry);	
		
		return feature;		
	}
	
	@Override
	public FeatureDTO createDataTransferObject(Feature feature) throws AssemblyException{
		
		FeatureDTO featureDTO = new FeatureDTO();
		featureDTO.setId(feature.getId());
		featureDTO.setProperties(feature.getProperties());
		featureDTO.setPropertiesRelations(feature.getPropertiesRelations());
		
		
		Geometry geometry=feature.getGeometry();		
		
		GeometryDTO geometryDTO=null;
		
		if(geometry instanceof Point){
			Point point=(Point)geometry;
			geometryDTO=pointAssembler.createDataTransferObject(point);
		}
		if(geometry instanceof LineString){
			LineString lineString=(LineString)geometry;
			geometryDTO=lineStringAssembler.createDataTransferObject(lineString);
		}
		if(geometry instanceof Polygon){
			Polygon polygon=(Polygon)geometry;
			geometryDTO=polygonAssembler.createDataTransferObject(polygon);
		}
		
		
		featureDTO.setGeometryDTO(geometryDTO);
		return featureDTO;
	}
	
	@Override
	public Collection<FeatureDTO> createDataTransferObjects(Collection<Feature> features) throws AssemblyException{
		
		Collection<FeatureDTO> featuresDTO = new ArrayList<FeatureDTO>();
		
		for(Feature feature:features){
			featuresDTO.add(createDataTransferObject(feature));
		}
				
		return featuresDTO;
		
	}	
	
	
}
