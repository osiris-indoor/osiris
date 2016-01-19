package com.bitmonlab.osiris.api.core.map.assemblers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bitmonlab.osiris.api.core.map.assemblers.FeatureAssemblerImpl;
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


@RunWith(MockitoJUnitRunner.class)
public class FeatureAssemblerTest {
	
	@InjectMocks
	private FeatureAssemblerImpl featureAssemblerImpl;
	
	@Mock
	private Map<String,String> properties;
	
	@Mock
	private List<Map<String,String>> propertiesRelations;
	
	@Mock
	private PointDTO pointDTO;
	
	@Mock
	private Assembler<PointDTO, Point> pointAssembler;
	
	@Mock
	private Assembler<LineStringDTO, LineString> lineStringAssembler;
	
	@Mock
	private Assembler<PolygonDTO, Polygon> polygonAssembler;
	
	@Mock
	private Point point;
	
	@Mock
	private LineStringDTO lineStringDTO;
	
	@Mock
	private LineString lineString;
	
	@Mock
	private FeatureDTO featureDTO;
	
	@Mock
	private GeometryDTO geometryDTO;
	
	@Mock
	private Feature feature;
	
	@Mock
	private PolygonDTO polygonDTO;
	
	@Mock
	private Polygon polygon;
	
	@Mock
	private Geometry geometry;
	
	
	@Mock
	private Map<String, String> property;
	
	@Test
	public void transformFeatureDTOtoPointFeature() throws AssemblyException{		
		
		List<Map<String, String>> propertiesRelations = new ArrayList<Map<String,String>>();		
		propertiesRelations.add(property);
		
		//Fixture			
		Mockito.when(featureDTO.getProperties()).thenReturn(properties);
		Mockito.when(featureDTO.getPropertiesRelations()).thenReturn(propertiesRelations);
		Mockito.when(featureDTO.getGeometryDTO()).thenReturn(pointDTO);
		Mockito.when(pointAssembler.createDomainObject(pointDTO)).thenReturn(point);
				
		//Experimentation
		Feature featureResponse = featureAssemblerImpl.createDomainObject(featureDTO);
		
		//Expectations
		assertEquals("Properties must be the same", featureResponse.getProperties(), properties);
		assertEquals("PropertiesRelations must be the same", featureResponse.getPropertiesRelations(), propertiesRelations);
		assertEquals("Geometry must be the same", featureResponse.getGeometry(), point);
		
	}
	
	@Test
	public void transformFeatureDTOtoLineStringFeature() throws AssemblyException{		
		
		List<Map<String, String>> propertiesRelations = new ArrayList<Map<String,String>>();		
		propertiesRelations.add(property);
								
		
		//Fixture			
		Mockito.when(featureDTO.getProperties()).thenReturn(properties);
		Mockito.when(featureDTO.getPropertiesRelations()).thenReturn(propertiesRelations);
		Mockito.when(featureDTO.getGeometryDTO()).thenReturn(lineStringDTO);
		Mockito.when(lineStringAssembler.createDomainObject(lineStringDTO)).thenReturn(lineString);
				
		//Experimentation
		Feature featureResponse = featureAssemblerImpl.createDomainObject(featureDTO);
		
		//Expectations
		assertEquals("Properties must be the same", featureResponse.getProperties(), properties);
		assertEquals("PropertiesRelations must be the same", featureResponse.getPropertiesRelations(), propertiesRelations);
		assertEquals("Geometry must be the same", featureResponse.getGeometry(), lineString);
		
	}
	
	@Test
	public void transformFeatureDTOtoPolygonFeature() throws Exception{		
		
		List<Map<String, String>> propertiesRelations = new ArrayList<Map<String,String>>();		
		propertiesRelations.add(property);
								
		
		//Fixture				
		Mockito.when(featureDTO.getProperties()).thenReturn(properties);		
		Mockito.when(featureDTO.getPropertiesRelations()).thenReturn(propertiesRelations);		
		Mockito.when(featureDTO.getGeometryDTO()).thenReturn(polygonDTO);
		Mockito.when(polygonAssembler.createDomainObject(polygonDTO)).thenReturn(polygon);
		
				
		//Experimentation
		Feature featureResponse = featureAssemblerImpl.createDomainObject(featureDTO);
					
		
		//Expectations
		assertEquals("Properties must be the same", featureResponse.getProperties(), properties);
		assertEquals("PropertiesRelations must be the same", featureResponse.getPropertiesRelations(), propertiesRelations);
		assertEquals("Geometry must be the same", featureResponse.getGeometry(), polygon);
				
		
	}
	
	@Test(expected=InvalidGeometry.class)
	public void shouldThrowExcepcionWhenInvalidGeometryConvertFeature() throws AssemblyException{
		
		List<Map<String, String>> propertiesRelations = new ArrayList<Map<String,String>>();		
		propertiesRelations.add(property);
								

		//Fixture			
		Mockito.when(featureDTO.getProperties()).thenReturn(properties);
		Mockito.when(featureDTO.getPropertiesRelations()).thenReturn(propertiesRelations);
		Mockito.when(featureDTO.getGeometryDTO()).thenReturn(geometryDTO);
				
		//Experimentation
		featureAssemblerImpl.createDomainObject(featureDTO);
	}
	
	@Test
	public void transformFeatureToPointDTO() throws AssemblyException{
		
		String id="id";
		
		
		//Fixture			
		Mockito.when(feature.getId()).thenReturn(id);
		Mockito.when(feature.getProperties()).thenReturn(properties);
		Mockito.when(feature.getPropertiesRelations()).thenReturn(propertiesRelations);
		Mockito.when(feature.getGeometry()).thenReturn(point);		
		Mockito.when(pointAssembler.createDataTransferObject(point)).thenReturn(pointDTO);
		
		//Experimentation
		FeatureDTO featureDTOResponse = featureAssemblerImpl.createDataTransferObject(feature);
		
		//Expectations
		assertEquals("Ids must be the same", featureDTOResponse.getId(), id);
		assertEquals("Properties must be the same", featureDTOResponse.getProperties(), properties);
		assertEquals("propertiesRelations must be the same", featureDTOResponse.getPropertiesRelations(), propertiesRelations);
		assertEquals("GeometryDTO must be the same", featureDTOResponse.getGeometryDTO(), pointDTO);
	}
	
	@Test
	public void transformFeatureToLineStringDTO() throws AssemblyException{
		
		String id="id";
		
		
		//Fixture			
		Mockito.when(feature.getId()).thenReturn(id);
		Mockito.when(feature.getProperties()).thenReturn(properties);
		Mockito.when(feature.getPropertiesRelations()).thenReturn(propertiesRelations);
		Mockito.when(feature.getGeometry()).thenReturn(lineString);		
		Mockito.when(lineStringAssembler.createDataTransferObject(lineString)).thenReturn(lineStringDTO);
		
		//Experimentation
		FeatureDTO featureDTOResponse = featureAssemblerImpl.createDataTransferObject(feature);
		
		//Expectations
		assertEquals("Ids must be the same", featureDTOResponse.getId(), id);
		assertEquals("Properties must be the same", featureDTOResponse.getProperties(), properties);
		assertEquals("propertiesRelations must be the same", featureDTOResponse.getPropertiesRelations(), propertiesRelations);
		assertEquals("LineStringDTO must be the same", featureDTOResponse.getGeometryDTO(), lineStringDTO);
	}
	
	@Test
	public void transformFeatureToPolygonDTO() throws AssemblyException{
		
		String id="id";
		
		
		//Fixture			
		Mockito.when(feature.getId()).thenReturn(id);
		Mockito.when(feature.getProperties()).thenReturn(properties);
		Mockito.when(feature.getPropertiesRelations()).thenReturn(propertiesRelations);
		Mockito.when(feature.getGeometry()).thenReturn(polygon);		
		Mockito.when(polygonAssembler.createDataTransferObject(polygon)).thenReturn(polygonDTO);
		
		//Experimentation
		FeatureDTO featureDTOResponse = featureAssemblerImpl.createDataTransferObject(feature);
		
		//Expectations
		assertEquals("Ids must be the same", featureDTOResponse.getId(), id);
		assertEquals("Properties must be the same", featureDTOResponse.getProperties(), properties);
		assertEquals("propertiesRelations must be the same", featureDTOResponse.getPropertiesRelations(), propertiesRelations);
		assertEquals("LineStringDTO must be the same", featureDTOResponse.getGeometryDTO(), polygonDTO);
	}
	
	@Test
	public void transformCollectionFeaturesToCollectionFeaturesDTO() throws AssemblyException{
		
		String id="id";		
		Collection<Feature> features = new ArrayList<Feature>();
		features.add(feature);
		
		//Fixture			
		Mockito.when(feature.getId()).thenReturn(id);
		Mockito.when(feature.getProperties()).thenReturn(properties);
		Mockito.when(feature.getPropertiesRelations()).thenReturn(propertiesRelations);
		Mockito.when(feature.getGeometry()).thenReturn(point);		
		Mockito.when(pointAssembler.createDataTransferObject(point)).thenReturn(pointDTO);
		
		//Experimentation
		Collection<FeatureDTO> response=featureAssemblerImpl.createDataTransferObjects(features);
		
		//Expectations
		Iterator<FeatureDTO> iterator=response.iterator();
		FeatureDTO featureDTO=iterator.next();
		assertEquals("Ids must be the same", featureDTO.getId(), id);
		assertEquals("Properties must be the same", featureDTO.getProperties(), properties);
		assertEquals("propertiesRelations must be the same", featureDTO.getPropertiesRelations(), propertiesRelations);
		assertEquals("GeometryDTO must be the same", featureDTO.getGeometryDTO(), pointDTO);
		
	}	
	
}
