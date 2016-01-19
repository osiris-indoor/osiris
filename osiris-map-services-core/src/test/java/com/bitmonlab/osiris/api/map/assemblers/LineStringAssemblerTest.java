package com.bitmonlab.osiris.api.map.assemblers;

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

import com.bitmonlab.osiris.api.map.assemblers.LineStringAssemblerImpl;
import com.bitmonlab.osiris.api.map.transferobject.LineStringDTO;
import com.bitmonlab.osiris.api.map.transferobject.PointDTO;
import com.bitmonlab.commons.api.map.model.geojson.LineString;
import com.bitmonlab.commons.api.map.model.geojson.Point;
import com.bitmonlab.core.assembler.Assembler;
import com.bitmonlab.core.assembler.AssemblyException;

@RunWith(MockitoJUnitRunner.class)
public class LineStringAssemblerTest {
	
	@Mock
	private LineStringDTO lineStringDTO;
	
	@InjectMocks
	private LineStringAssemblerImpl lineStringAssemblerImpl;
	
	@Mock
	private LineString lineString1;
	
	@Mock
	private LineString lineString2;
	
	@Mock
	private PointDTO pointDTO1;
	
	@Mock
	private PointDTO pointDTO2;
	
	@Mock
	private Point point1;
	
	@Mock
	private Point point2;
	
	@Mock
	private Assembler<PointDTO,Point> pointAssembler;
	
	@Mock
	private List<Double> coordinates1;
	
	@Mock
	private List<Double> coordinates2;
	
	@Mock
	private PointDTO centroidDTO;
	
	@Mock
	private List<Double> centroid;
	
	@Mock
	private List<Double> centroid1;
	
	@Mock
	private List<Double> centroid2;
					

	@Test
	public void transformLineStringDTOtoLineString() throws AssemblyException{
		
		Collection<PointDTO> collectionPointDTO = new ArrayList<PointDTO>();
		collectionPointDTO.add(pointDTO1);
		collectionPointDTO.add(pointDTO2);
	
		Double longitude=1D;		
		Double latitude=2D;
		
		
		//Fixture			
		Mockito.when(lineStringDTO.getCollectionPointDTO()).thenReturn(collectionPointDTO);	
		Mockito.when(pointAssembler.createDomainObject(pointDTO1)).thenReturn(point1);
		Mockito.when(point1.getCoordinates()).thenReturn(coordinates1);
		Mockito.when(pointAssembler.createDomainObject(pointDTO2)).thenReturn(point2);
		Mockito.when(point2.getCoordinates()).thenReturn(coordinates2);
		
		Mockito.when(lineStringDTO.getCentroidDTO()).thenReturn(centroidDTO);
		Mockito.when(centroidDTO.getLongitude()).thenReturn(longitude);
		Mockito.when(centroidDTO.getLatitude()).thenReturn(latitude);
						
				
		//Experimentation
		LineString lineStringResponse = lineStringAssemblerImpl.createDomainObject(lineStringDTO);
		
		//Expectations
		List<List<Double>> coordinatesLineStringResponse=lineStringResponse.getCoordinates();
		assertEquals("LineString response must have two collections of coordinates", coordinatesLineStringResponse.size(), 2);
		Iterator<List<Double>> iteratorLineString = coordinatesLineStringResponse.iterator();
				
		Collection<Double> coordinates1Response=iteratorLineString.next();
		assertEquals("Coordinates1 must have two coordinates", coordinates1Response, coordinates1);
			
		Collection<Double> coordinates2Response=iteratorLineString.next();
		assertEquals("Coordinates2 must have two coordinates", coordinates2Response, coordinates2);
		
		List<Double> centroidResponse = lineStringResponse.getCentroid();		
		assertEquals("Longitudes must be equals", centroidResponse.get(0), longitude);				
		assertEquals("Latitudes must be equals", centroidResponse.get(1), latitude);
		
			
	}
		
	

	@Test
	public void transformLineStringToLineStringDTO() throws AssemblyException{
		
		List<List<Double>> collectionCoordinates=new ArrayList<List<Double>>();
		Double longitude=1D;		
		Double latitude=2D;
		
		collectionCoordinates.add(coordinates1);
		collectionCoordinates.add(coordinates2);
		
		Point point1=new Point();
		point1.setCoordinates(coordinates1);
		
		Point point2=new Point();
		point2.setCoordinates(coordinates2);
		
		//Fixture
		Mockito.when(lineString1.getCoordinates()).thenReturn(collectionCoordinates);
		Mockito.when(pointAssembler.createDataTransferObject(point1)).thenReturn(pointDTO1);
		Mockito.when(pointAssembler.createDataTransferObject(point2)).thenReturn(pointDTO2);
		Mockito.when(lineString1.getCentroid()).thenReturn(centroid);
		Mockito.when(centroid.get(0)).thenReturn(longitude);
		Mockito.when(centroid.get(1)).thenReturn(latitude);
		
						
		//Experimentation
		LineStringDTO lineStringDTOResponse = lineStringAssemblerImpl.createDataTransferObject(lineString1);
	
		//Experimentation
		Collection<PointDTO> collectionPointDTO=lineStringDTOResponse.getCollectionPointDTO();
		assertEquals("LineStringDTO response must have two collections of coordinates", collectionPointDTO.size(), 2);
		Iterator<PointDTO> iterator=collectionPointDTO.iterator();
		
		PointDTO pointDTO1Response=iterator.next();
		assertEquals("PointDTO1 must be the same", pointDTO1Response, pointDTO1);
		
		PointDTO pointDTO2Response=iterator.next();
		assertEquals("PointDTO2 must be the same", pointDTO2Response, pointDTO2);
		
		PointDTO centroidResponse = lineStringDTOResponse.getCentroidDTO();
		assertEquals("LongitudesDTO must be equals", centroidResponse.getLongitude(), longitude);				
		assertEquals("LatitudesDTO must be equals", centroidResponse.getLatitude() , latitude);
		
	}
	
	@Test
	public void transformCollectionLineStringsToCollectionLineStringsDTO() throws AssemblyException{
		
		Collection<LineString> lineStrings=new ArrayList<LineString>();
		lineStrings.add(lineString1);
		lineStrings.add(lineString2);
		
		List<List<Double>> collectionCoordinates1=new ArrayList<List<Double>>();
		collectionCoordinates1.add(coordinates1);
		
		List<List<Double>> collectionCoordinates2=new ArrayList<List<Double>>();
		collectionCoordinates2.add(coordinates2);
						
		Point point1=new Point();
		point1.setCoordinates(coordinates1);
		
		Point point2=new Point();
		point2.setCoordinates(coordinates2);
				
		Double longitude1=1D;		
		Double latitude1=2D;
		Double longitude2=1D;		
		Double latitude2=2D;
		
		
		//Fixture			
		Mockito.when(lineString1.getCoordinates()).thenReturn(collectionCoordinates1);
		Mockito.when(pointAssembler.createDataTransferObject(point1)).thenReturn(pointDTO1);
		Mockito.when(lineString1.getCentroid()).thenReturn(centroid1);
		Mockito.when(centroid1.get(0)).thenReturn(longitude1);
		Mockito.when(centroid1.get(1)).thenReturn(latitude1);
		Mockito.when(lineString2.getCoordinates()).thenReturn(collectionCoordinates2);
		Mockito.when(pointAssembler.createDataTransferObject(point2)).thenReturn(pointDTO2);
		Mockito.when(lineString2.getCentroid()).thenReturn(centroid1);
		Mockito.when(centroid1.get(0)).thenReturn(longitude2);
		Mockito.when(centroid1.get(1)).thenReturn(latitude2);
				
		
		//Experimentation
		Collection<LineStringDTO> response=lineStringAssemblerImpl.createDataTransferObjects(lineStrings);
		
		//Expectations
		assertEquals("CollectionLineStringDTO response must have two lineStringDTO", response.size(), 2);
		
		Iterator<LineStringDTO> iteratorCollectionLineStringDTO=response.iterator();
		
		LineStringDTO lineStringDTOResponse1=iteratorCollectionLineStringDTO.next();
		Collection<PointDTO> collectionPointDTO1Response=lineStringDTOResponse1.getCollectionPointDTO();
		assertEquals("CollectionPointDTO1Response must have a pointDTO", collectionPointDTO1Response.size(), 1);
		Iterator<PointDTO> iteratorLineStringDTO1Response=collectionPointDTO1Response.iterator();
		PointDTO pointDTO1Response=iteratorLineStringDTO1Response.next();
		assertEquals("PointDTO1 must be the same", pointDTO1Response, pointDTO1);
		PointDTO centroidResponse1 = lineStringDTOResponse1.getCentroidDTO();
		assertEquals("LongitudesDTO must be equals", centroidResponse1.getLongitude(), longitude1);				
		assertEquals("LatitudesDTO must be equals", centroidResponse1.getLatitude() , latitude1);
		
		
		LineStringDTO lineStringDTOResponse2=iteratorCollectionLineStringDTO.next();
		Collection<PointDTO> collectionPointDTO2Response=lineStringDTOResponse2.getCollectionPointDTO();
		assertEquals("CollectionPointDTO2Response must have a pointDTO", collectionPointDTO2Response.size(), 1);
		Iterator<PointDTO> iteratorLineStringDTO2Response=collectionPointDTO2Response.iterator();
		PointDTO pointDTO2Response=iteratorLineStringDTO2Response.next();
		assertEquals("PointDTO2 must be the same", pointDTO2Response, pointDTO2);
		PointDTO centroidResponse2 = lineStringDTOResponse2.getCentroidDTO();
		assertEquals("LongitudesDTO must be equals", centroidResponse2.getLongitude(), longitude2);				
		assertEquals("LatitudesDTO must be equals", centroidResponse2.getLatitude() , latitude2);
		
		
		
	}

}
