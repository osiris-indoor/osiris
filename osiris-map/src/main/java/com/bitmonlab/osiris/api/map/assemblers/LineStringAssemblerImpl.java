package com.bitmonlab.osiris.api.map.assemblers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.bitmonlab.osiris.api.map.transferobject.LineStringDTO;
import com.bitmonlab.osiris.api.map.transferobject.PointDTO;
import com.bitmonlab.commons.api.map.model.geojson.LineString;
import com.bitmonlab.commons.api.map.model.geojson.Point;
import com.bitmonlab.core.assembler.Assembler;
import com.bitmonlab.core.assembler.AssemblyException;
import com.bitmonlab.core.assembler.SimpleAssembler;

@Named("LineStringAssembler")
public class LineStringAssemblerImpl extends SimpleAssembler<LineStringDTO, LineString>{
	
	@Inject 
	@Named("PointAssembler")
	private Assembler<PointDTO, Point> pointAssembler;
	
	public LineStringAssemblerImpl() {
		super(LineStringDTO.class,LineString.class);
	}
	
	@Override
	public LineString createDomainObject(LineStringDTO lineStringDTO) throws AssemblyException{
		
		LineString lineString = new LineString();
		
		List<List<Double>> collectionCoordinates = new ArrayList<List<Double>>();		
		
		Collection<PointDTO> collectionPointDTO=lineStringDTO.getCollectionPointDTO();
		
		for(PointDTO pointDTO: collectionPointDTO){
			Point point=pointAssembler.createDomainObject(pointDTO);
			List<Double> coordinates=point.getCoordinates();
			collectionCoordinates.add(coordinates);
		}
		
		lineString.setCoordinates(collectionCoordinates);
		
		if(lineStringDTO.getCentroidDTO()!=null){
			List<Double> centroid = new ArrayList<Double>();			
			centroid.add(lineStringDTO.getCentroidDTO().getLongitude());
			centroid.add(lineStringDTO.getCentroidDTO().getLatitude());		
			lineString.setCentroid(centroid);
		}
		
		
		
		return lineString;
	}
	
	@Override
	public LineStringDTO createDataTransferObject(LineString lineString) throws AssemblyException{
		
		LineStringDTO lineStringDTO = new LineStringDTO();
		List<PointDTO> collectionPointDTO = new ArrayList<PointDTO>();
		
		List<List<Double>> collectionCoordinates=lineString.getCoordinates();
		
		for(List<Double> coordinates: collectionCoordinates){
			Point point=new Point();
			point.setCoordinates(coordinates);
			PointDTO pointDTO=pointAssembler.createDataTransferObject(point);
			collectionPointDTO.add(pointDTO);
		}
		
		lineStringDTO.setCollectionPointDTO(collectionPointDTO);
				
		if(lineString.getCentroid()!=null){
			PointDTO centroidPointDTO=new PointDTO();				
			centroidPointDTO.setLongitude(lineString.getCentroid().get(0));
			centroidPointDTO.setLatitude(lineString.getCentroid().get(1));
				
			lineStringDTO.setCentroidDTO(centroidPointDTO);
		}
		
		return lineStringDTO;
		
	}
	

	@Override
	public Collection<LineStringDTO> createDataTransferObjects(Collection<LineString> lineStrings) throws AssemblyException{
		
		Collection<LineStringDTO> lineStringsDTO = new ArrayList<LineStringDTO>();
		
		for(LineString lineString:lineStrings){
			lineStringsDTO.add(createDataTransferObject(lineString));			
		}
				
		return lineStringsDTO;
		
	}
}
