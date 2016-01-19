package com.bitmonlab.osiris.api.core.map.assemblers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bitmonlab.osiris.api.core.map.assemblers.PointAssemblerImpl;
import com.bitmonlab.osiris.api.core.map.exceptions.LatitudeValueException;
import com.bitmonlab.osiris.api.core.map.exceptions.LongitudeValueException;
import com.bitmonlab.osiris.api.core.map.transferobject.PointDTO;
import com.bitmonlab.osiris.commons.map.model.geojson.Point;
import com.bitmonlab.osiris.core.assembler.AssemblyException;

@RunWith(MockitoJUnitRunner.class)
public class PointAssemblerTest {
	
	@Mock
	private PointDTO pointDTO;
	
	@InjectMocks
	private PointAssemblerImpl pointAssemblerImpl;
	
	@Mock
	private Point point;
	
	@Mock
	private ArrayList<Double> coordinates;
	
	@Test
	public void transformPointDTOtoPoint() throws LongitudeValueException, LatitudeValueException{
		
		Double longitude1=1.0;
		Double latitude1=2.0;
		
		//Fixture			
		Mockito.when(pointDTO.getLongitude()).thenReturn(longitude1);	
		Mockito.when(pointDTO.getLatitude()).thenReturn(latitude1);	
				
		//Experimentation
		Point pointResponse = pointAssemblerImpl.createDomainObject(pointDTO);
		
		//Expectations
		List<Double> coordinates=pointResponse.getCoordinates();
		assertEquals("Point response must have two coordinates", coordinates.size(), 2);
		Iterator<Double> iterator=coordinates.iterator();
		
		
		assertEquals("Longitudes must be the same", iterator.next(), longitude1);
		assertEquals("Latitudes must be the same", iterator.next(), latitude1);
		
		
	}
	
	@Test(expected=LongitudeValueException.class)
	public void shouldThrowExcepcionWhenLongitudeLess() throws LongitudeValueException, LatitudeValueException{
		
		//Fixture			
		Mockito.when(pointDTO.getLongitude()).thenReturn(-190.0);	
					
		//Experimentation
		pointAssemblerImpl.createDomainObject(pointDTO);
	}
	
	@Test(expected=LongitudeValueException.class)
	public void shouldThrowExcepcionWhenLongitudeGreater() throws LongitudeValueException, LatitudeValueException{
		
		//Fixture			
		Mockito.when(pointDTO.getLongitude()).thenReturn(190.0);	
					
		//Experimentation
		pointAssemblerImpl.createDomainObject(pointDTO);
	}
	
	@Test(expected=LatitudeValueException.class)
	public void shouldThrowExcepcionWhenLatitudeLess() throws LongitudeValueException, LatitudeValueException{
		
		Double longitude1=1.0;
		
		//Fixture			
		Mockito.when(pointDTO.getLongitude()).thenReturn(longitude1);	
		Mockito.when(pointDTO.getLatitude()).thenReturn(-100.0);	
					
		//Experimentation
		pointAssemblerImpl.createDomainObject(pointDTO);
	}
	
	@Test(expected=LatitudeValueException.class)
	public void shouldThrowExcepcionWhenLatitudeGreater() throws LongitudeValueException, LatitudeValueException{
		
		Double longitude1=1.0;
		
		//Fixture			
		Mockito.when(pointDTO.getLongitude()).thenReturn(longitude1);	
		Mockito.when(pointDTO.getLatitude()).thenReturn(100.0);	
					
		//Experimentation
		pointAssemblerImpl.createDomainObject(pointDTO);
	}
	
	@Test
	public void transformPointToPointDTO(){
		
		Double longitude=1.0;
		Double latitude=2.0;
		
		//Fixture
		Mockito.when(point.getCoordinates()).thenReturn(coordinates);	
		Mockito.when(coordinates.get(0)).thenReturn(longitude);	
		Mockito.when(coordinates.get(1)).thenReturn(latitude);	
		
		//Experimentation
		PointDTO pointDTOResponse = pointAssemblerImpl.createDataTransferObject(point);
	
		//Experimentation
		assertEquals("Longitudes must be the same", pointDTOResponse.getLongitude(), longitude);
		assertEquals("Latitudes must be the same", pointDTOResponse.getLatitude(), latitude);
				
	}
	
	@Test
	public void transformCollectionPointsToCollectionPointsDTO() throws AssemblyException{
		
		Double longitude=1.0;
		Double latitude=2.0;
		Collection<Point> points = new ArrayList<Point>();
		points.add(point);
		
		//Fixture			
		Mockito.when(point.getCoordinates()).thenReturn(coordinates);	
		Mockito.when(coordinates.get(0)).thenReturn(longitude);	
		Mockito.when(coordinates.get(1)).thenReturn(latitude);		
		
		//Experimentation
		Collection<PointDTO> response=pointAssemblerImpl.createDataTransferObjects(points);
		
		//Expectations
		Iterator<PointDTO> iterator=response.iterator();
		PointDTO pointDTO=iterator.next();
		assertEquals("Longitudes must be the same", pointDTO.getLongitude(), longitude);
		assertEquals("Latitudes must be the same", pointDTO.getLatitude(), latitude);
		
	}
	
	
}
