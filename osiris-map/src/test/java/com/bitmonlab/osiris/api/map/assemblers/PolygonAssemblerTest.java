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

import com.bitmonlab.osiris.api.core.map.assemblers.PolygonAssemblerImpl;
import com.bitmonlab.osiris.api.core.map.transferobject.LineStringDTO;
import com.bitmonlab.osiris.api.core.map.transferobject.PolygonDTO;
import com.bitmonlab.osiris.commons.map.model.geojson.LineString;
import com.bitmonlab.osiris.commons.map.model.geojson.Polygon;
import com.bitmonlab.osiris.core.assembler.Assembler;
import com.bitmonlab.osiris.core.assembler.AssemblyException;

@RunWith(MockitoJUnitRunner.class)
public class PolygonAssemblerTest {
	
	
	@InjectMocks
	private PolygonAssemblerImpl polygonAssemblerImpl;
	
	@Mock
	private PolygonDTO polygonDTO;
	
	@Mock
	private LineStringDTO lineStringDTO1;

	@Mock
	private LineStringDTO lineStringDTO2;
	
	@Mock
	private Assembler<LineStringDTO,LineString> lineStringAssembler;
	
	@Mock
	private LineString lineString1;
	
	@Mock
	private LineString lineString2;
	
	@Mock
	private List<List<Double>> coordinatesLineString1;
	
	@Mock
	private List<List<Double>> coordinatesLineString2;
	
	@Mock
	private Polygon polygon1;
	
	@Mock
	private Polygon polygon2;
	
	@Mock
	private List<List<Double>> coordinates1;
	
	@Mock
	private List<List<Double>> coordinates2;
	
	
	@Test
	public void transformPolygonDTOtoPolygon() throws AssemblyException{
		
		Collection<LineStringDTO> collectionLineStringDTO=new ArrayList<LineStringDTO>();
		collectionLineStringDTO.add(lineStringDTO1);
		collectionLineStringDTO.add(lineStringDTO2);
		
		//Fixture			
		Mockito.when(polygonDTO.getCollectionLineStringDTO()).thenReturn(collectionLineStringDTO);	
		Mockito.when(lineStringAssembler.createDomainObject(lineStringDTO1)).thenReturn(lineString1);	
		Mockito.when(lineString1.getCoordinates()).thenReturn(coordinatesLineString1);
		Mockito.when(lineStringAssembler.createDomainObject(lineStringDTO2)).thenReturn(lineString2);	
		Mockito.when(lineString2.getCoordinates()).thenReturn(coordinatesLineString2);
		
		//Experimentation
		Polygon polygonResponse = polygonAssemblerImpl.createDomainObject(polygonDTO);
		
		//Expectations
		Collection<List<List<Double>>> collectionLineString=polygonResponse.getCoordinates();
		assertEquals("Polygon response must have two lineString", collectionLineString.size(), 2);
		Iterator<List<List<Double>>> iteratorPolygon = collectionLineString.iterator();

		List<List<Double>> lineString1Response=iteratorPolygon.next();
		assertEquals("LineString1 must be the same", coordinatesLineString1, lineString1Response);
		
		List<List<Double>> lineString2Response=iteratorPolygon.next();
		assertEquals("LineString2 must be the same", coordinatesLineString2, lineString2Response);
	}
	
	@Test
	public void transformPolygonToPolygonDTO() throws AssemblyException{
		
		Collection<List<List<Double>>> collectionCoordinates=new ArrayList<List<List<Double>>>();
		collectionCoordinates.add(coordinates1);
		collectionCoordinates.add(coordinates2);
		
		LineString lineString1=new LineString();
		lineString1.setCoordinates(coordinates1);
	
		LineString lineString2=new LineString();
		lineString2.setCoordinates(coordinates2);
		
		//Fixture			
		Mockito.when(polygon1.getCoordinates()).thenReturn(collectionCoordinates);
		Mockito.when(lineStringAssembler.createDataTransferObject(lineString1)).thenReturn(lineStringDTO1);
		Mockito.when(lineStringAssembler.createDataTransferObject(lineString2)).thenReturn(lineStringDTO2);
				
		//Experimentation
		PolygonDTO polygonDTOResponse=polygonAssemblerImpl.createDataTransferObject(polygon1);
		
		//Expectations
		Collection<LineStringDTO> collectionLineStringDTO=polygonDTOResponse.getCollectionLineStringDTO();
		assertEquals("PolygonDTO response must have two lineStringDTO", collectionLineStringDTO.size(), 2);
		Iterator<LineStringDTO> iterator=collectionLineStringDTO.iterator();
		
		LineStringDTO lineStringDTO1Response=iterator.next();
		assertEquals("LineStringDTO1 must be the same", lineStringDTO1Response, lineStringDTO1);
		
		LineStringDTO lineStringDTO2Response=iterator.next();
		assertEquals("LineStringDTO2 must be the same", lineStringDTO2Response, lineStringDTO2);
	}

	@Test
	public void transformCollectionLineStringsToCollectionLineStringsDTO() throws AssemblyException{
		
		Collection<Polygon> polygons=new ArrayList<Polygon>();
		polygons.add(polygon1);
		polygons.add(polygon2);
		
		Collection<List<List<Double>>> collectionCoordinates1=new ArrayList<List<List<Double>>>();
		collectionCoordinates1.add(coordinates1);
		
		LineString lineString1=new LineString();
		lineString1.setCoordinates(coordinates1);
		
		Collection<List<List<Double>>> collectionCoordinates2=new ArrayList<List<List<Double>>>();
		collectionCoordinates2.add(coordinates2);
		
		LineString lineString2=new LineString();
		lineString2.setCoordinates(coordinates2);

		//Fixture			
		Mockito.when(polygon1.getCoordinates()).thenReturn(collectionCoordinates1);
		Mockito.when(lineStringAssembler.createDataTransferObject(lineString1)).thenReturn(lineStringDTO1);
		Mockito.when(polygon2.getCoordinates()).thenReturn(collectionCoordinates2);
		Mockito.when(lineStringAssembler.createDataTransferObject(lineString2)).thenReturn(lineStringDTO2);
		
		//Experimentation
		Collection<PolygonDTO> response=polygonAssemblerImpl.createDataTransferObjects(polygons);
	
		//Expectations
		assertEquals("CollectionPolygonDTO response must have two polygonDTO", response.size(), 2);
	
		Iterator<PolygonDTO> iteratorCollectionPolygonDTO=response.iterator();
	
		PolygonDTO polygonDTOResponse1=iteratorCollectionPolygonDTO.next();
		Collection<LineStringDTO> collectionLineStringDTO1Response=polygonDTOResponse1.getCollectionLineStringDTO();
		assertEquals("CollectionLineStringDTO1Response must have a pointDTO", collectionLineStringDTO1Response.size(), 1);
		Iterator<LineStringDTO> iteratorLineStringDTO1Response=collectionLineStringDTO1Response.iterator();
		LineStringDTO lineStringDTO1Response=iteratorLineStringDTO1Response.next();
		assertEquals("LineStringDTO1 must be the same", lineStringDTO1Response, lineStringDTO1);
	
		PolygonDTO polygonDTOResponse2=iteratorCollectionPolygonDTO.next();
		Collection<LineStringDTO> collectionLineStringDTO2Response=polygonDTOResponse2.getCollectionLineStringDTO();
		assertEquals("CollectionLineStringDTO2Response must have a pointDTO", collectionLineStringDTO2Response.size(), 1);
		Iterator<LineStringDTO> iteratorLineStringDTO2Response=collectionLineStringDTO2Response.iterator();
		LineStringDTO lineStringDTO2Response=iteratorLineStringDTO2Response.next();
		assertEquals("LineStringDTO2 must be the same", lineStringDTO2Response, lineStringDTO2);
	}

}
