package com.bitmonlab.osiris.api.map.assemblers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Named;

import com.bitmonlab.osiris.api.map.exceptions.LatitudeValueException;
import com.bitmonlab.osiris.api.map.exceptions.LongitudeValueException;
import com.bitmonlab.osiris.api.map.transferobject.PointDTO;
import com.bitmonlab.commons.api.map.model.geojson.Point;
import com.bitmonlab.core.assembler.SimpleAssembler;

@Named("PointAssembler")
public class PointAssemblerImpl extends SimpleAssembler<PointDTO, Point>{
	
	final private Double LONGITUDE_MIN = -180.0;
	final private Double LONGITUDE_MAX = 180.0;
	final private Double LATITUDE_MIN = -90.0;
	final private Double LATITUDE_MAX = 90.0;
	
	public PointAssemblerImpl() {
		super(PointDTO.class,Point.class);
	}
	
	@Override
	public Point createDomainObject(PointDTO pointDTO) throws LongitudeValueException,LatitudeValueException{
		
		Point point = new Point();
		
		List<Double> coordinates = new ArrayList<Double>();
		
		if(pointDTO.getLongitude()< LONGITUDE_MIN || pointDTO.getLongitude() > LONGITUDE_MAX){
			throw new LongitudeValueException();
		}
		
		if(pointDTO.getLatitude()< LATITUDE_MIN || pointDTO.getLatitude() > LATITUDE_MAX){
			throw new LatitudeValueException();
		}
		
		coordinates.add(pointDTO.getLongitude());
		coordinates.add(pointDTO.getLatitude());
		
		point.setCoordinates(coordinates);
		
		return point;		
	}
	
	
	@Override
	public PointDTO createDataTransferObject(Point point){
		
		PointDTO pointDTO = new PointDTO();
		
		List<Double> coordinates=point.getCoordinates();
		
		pointDTO.setLongitude(coordinates.get(0));
		pointDTO.setLatitude(coordinates.get(1));
		
		return pointDTO;
		
	}
	
	@Override
	public Collection<PointDTO> createDataTransferObjects(Collection<Point> points){
		
		Collection<PointDTO> pointsDTO = new ArrayList<PointDTO>();
		
		for(Point point:points){
			pointsDTO.add(createDataTransferObject(point));
		}
				
		return pointsDTO;
		
	}
	
	
}
