package com.bitmonlab.osiris.api.map.assemblers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.bitmonlab.osiris.api.map.transferobject.LineStringDTO;
import com.bitmonlab.osiris.api.map.transferobject.PolygonDTO;
import com.bitmonlab.commons.api.map.model.geojson.LineString;
import com.bitmonlab.commons.api.map.model.geojson.Polygon;
import com.bitmonlab.core.assembler.Assembler;
import com.bitmonlab.core.assembler.AssemblyException;
import com.bitmonlab.core.assembler.SimpleAssembler;

@Named("PolygonAssembler")
public class PolygonAssemblerImpl extends SimpleAssembler<PolygonDTO, Polygon>{
	
	@Inject 
	@Named("LineStringAssembler")
	private Assembler<LineStringDTO, LineString> lineStringAssembler;
	
	public PolygonAssemblerImpl() {
		super(PolygonDTO.class,Polygon.class);
	}
	
	@Override
	public Polygon createDomainObject(PolygonDTO polygonDTO) throws AssemblyException{
		
		Polygon polygon = new Polygon();
		
		Collection<List<List<Double>>> coordinates=new ArrayList<List<List<Double>>>();
		
		Collection<LineStringDTO> collectionLineString=polygonDTO.getCollectionLineStringDTO();
		
		for(LineStringDTO lineStringDTO: collectionLineString){
			LineString lineString=lineStringAssembler.createDomainObject(lineStringDTO);
			List<List<Double>> coordinatesLineString=lineString.getCoordinates();
			coordinates.add(coordinatesLineString);
		}
		
		polygon.setCoordinates(coordinates);
		
		return polygon;
	}
	
	@Override
	public PolygonDTO createDataTransferObject(Polygon polygon) throws AssemblyException{
		
		PolygonDTO polygonDTO = new PolygonDTO();
		List<LineStringDTO> collectionLineStringDTO = new ArrayList<LineStringDTO>();
		
		Collection<List<List<Double>>> collectionCoordinates=polygon.getCoordinates();
		
		for(List<List<Double>> coordinates: collectionCoordinates){
			LineString lineString=new LineString();
			lineString.setCoordinates(coordinates);
			LineStringDTO lineStringDTO=lineStringAssembler.createDataTransferObject(lineString);
			collectionLineStringDTO.add(lineStringDTO);
		}
		
		polygonDTO.setCollectionLineStringDTO(collectionLineStringDTO);
		
		return polygonDTO;
		
	}
	

	@Override
	public Collection<PolygonDTO> createDataTransferObjects(Collection<Polygon> collectionPolygon) throws AssemblyException{
		
		Collection<PolygonDTO> collectionPolygonDTO = new ArrayList<PolygonDTO>();
		
		for(Polygon polygon:collectionPolygon){
			collectionPolygonDTO.add(createDataTransferObject(polygon));
		}
				
		return collectionPolygonDTO;
		
	}
}
