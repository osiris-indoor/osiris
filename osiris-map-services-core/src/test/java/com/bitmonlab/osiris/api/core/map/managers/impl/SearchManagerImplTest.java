package com.bitmonlab.osiris.api.core.map.managers.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import math.geom2d.Point2D;
import math.geom2d.polygon.SimplePolygon2D;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.bitmonlab.osiris.api.core.map.dao.api.FeatureRepository;
import com.bitmonlab.osiris.api.core.map.dao.api.MapRepository;
import com.bitmonlab.osiris.api.core.map.exceptions.QueryException;
import com.bitmonlab.osiris.api.core.map.exceptions.RoomNotFoundException;
import com.bitmonlab.osiris.api.core.map.managers.impl.SearchManagerImpl;
import com.bitmonlab.osiris.api.core.map.transferobject.LayerDTO;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;
import com.bitmonlab.osiris.commons.map.model.geojson.LineString;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest({SearchManagerImpl.class, Integer.class,  Point2D.class})
public class SearchManagerImplTest {

	@InjectMocks
	private SearchManagerImpl searchManagerImpl;
	
	@Mock 
	private FeatureRepository featureRepository;
	
	@Mock
	private MapRepository mapRepository;
	
	@Mock
	private Collection<Feature> collectionFeatures;
	
	@Mock
	private Collection<Feature> collectionMap;
	
	@Mock
	private SimplePolygon2D simplePolygon2DRoom1;
	
	@Mock
	private SimplePolygon2D simplePolygon2DRoom2;
	
	@Mock
	private SimplePolygon2D simplePolygon2DRoom2Complement;
	
	@Mock
	private LineString geometry1;
	
	@Mock
	private LineString geometry2;
	
	@Mock
	private List<Double> coordinatesRoom11;
	
	@Mock
	private List<Double> coordinatesRoom12;
	
	@Mock
	private List<Double> coordinatesRoom13;
	
	@Mock
	private List<Double> coordinatesRoom14;
	
	@Mock
	private List<Double> coordinatesRoom21;
	
	@Mock
	private List<Double> coordinatesRoom22;
	
	@Mock
	private List<Double> coordinatesRoom23;
	
	@Mock
	private List<Double> coordinatesRoom24;
	
	@Mock
	private Point2D point11;
	
	@Mock
	private Point2D point12;
	
	@Mock
	private Point2D point13;
	
	@Mock
	private Point2D point21;
	
	@Mock
	private Point2D point22;
	
	@Mock
	private Point2D point23;
	
	@Mock
	private Feature featureRoom1;
	
	@Mock
	private Feature featureRoom2;

	
	@Test
	public void getFeaturesByQueryAllTest() throws QueryException{
		
		 String idApplication = "9";  
		 String query ="query";
		 Integer pageIndex=5;
		 Integer pageSize= 20;
		 LayerDTO layerDTO=LayerDTO.ALL;
		 
		//Fixture
		Mockito.when(featureRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize)).thenReturn(collectionFeatures);
		Mockito.when(mapRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize)).thenReturn(collectionMap);
		 
		//Experimentation
		searchManagerImpl.getFeaturesByQuery(idApplication, query, layerDTO, pageIndex, pageSize);
		
		//Expectation
		Mockito.verify(featureRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize);
		Mockito.verify(mapRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize);
		Mockito.verify(collectionFeatures).addAll(collectionMap);		
	}
	
	@Test
	public void getFeaturesByQueryFeaturesTest() throws QueryException{
		
		 String idApplication = "9";  
		 String query ="query";
		 Integer pageIndex=5;
		 Integer pageSize= 20;
		 LayerDTO layerDTO=LayerDTO.FEATURES;
		 
		//Fixture
		Mockito.when(featureRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize)).thenReturn(collectionFeatures);
				 
		//Experimentation
		searchManagerImpl.getFeaturesByQuery(idApplication, query, layerDTO, pageIndex, pageSize);
		
		//Expectation
		Mockito.verify(featureRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize);
	}
	
	@Test
	public void getFeaturesByQueryMapTest() throws QueryException{
		
		String idApplication = "9";  
		String query ="query";
		Integer pageIndex=5;
		Integer pageSize= 20;
		LayerDTO layerDTO=LayerDTO.MAP;
				 
		//Fixture
		Mockito.when(mapRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize)).thenReturn(collectionFeatures);
				 
		//Experimentation
		searchManagerImpl.getFeaturesByQuery(idApplication, query, layerDTO, pageIndex, pageSize);
		
		//Expectation
		Mockito.verify(mapRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize);
	}
	
	
	
	@Test
	public void getFeaturesByQueryAllWithOrderTest() throws QueryException{
		
		String idApplication = "9";  
		String query ="query";
		Integer pageIndex=5;
		Integer pageSize= 20;
		LayerDTO layerDTO=LayerDTO.ALL;
		String orderField="field";
	    String order="ASC";
		
		//Fixture
		Mockito.when(featureRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize, orderField, order)).thenReturn(collectionFeatures);
		Mockito.when(mapRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize, orderField, order)).thenReturn(collectionMap);
		 
		//Experimentation
		searchManagerImpl.getFeaturesByQuery(idApplication, query, layerDTO, pageIndex, pageSize, orderField, order);
		
		//Expectation
		Mockito.verify(featureRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize, orderField, order);
		Mockito.verify(mapRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize, orderField, order);
		Mockito.verify(collectionFeatures).addAll(collectionMap);		
	}
	
	@Test
	public void getFeaturesByQueryFeaturesWithOrderTest() throws QueryException{
		
		String idApplication = "9";  
		String query ="query";
		Integer pageIndex=5;
		Integer pageSize= 20;
		LayerDTO layerDTO=LayerDTO.FEATURES;
		String orderField="field";
	    String order="ASC";
		 
		//Fixture
		Mockito.when(featureRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize, orderField, order)).thenReturn(collectionFeatures);
				 
		//Experimentation
		searchManagerImpl.getFeaturesByQuery(idApplication, query, layerDTO, pageIndex, pageSize, orderField, order);
		
		//Expectation
		Mockito.verify(featureRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize, orderField, order);
	}
	
	@Test
	public void getFeaturesByQueryMapWithOrderTest() throws QueryException{
		
		String idApplication = "9";  
		String query ="query";
		Integer pageIndex=5;
		Integer pageSize= 20;
		LayerDTO layerDTO=LayerDTO.MAP;
		String orderField="field";
		String order="ASC";
		 
		//Fixture
		Mockito.when(mapRepository.searchIDAppAndQuery(idApplication,query, pageIndex, pageSize, orderField, order)).thenReturn(collectionFeatures);
				 
		//Experimentation
		searchManagerImpl.getFeaturesByQuery(idApplication, query, layerDTO, pageIndex, pageSize, orderField, order);
		
		//Expectation
		Mockito.verify(mapRepository).searchIDAppAndQuery(idApplication, query, pageIndex, pageSize, orderField, order);
	}
	
	@Test
	public void getRoomByLocationInOrderTest() throws Exception {
		//Fixture
		String appIdentifier="appIdentifier";
		Double longitude=1.0;
		Double latitude=2.0;
		Integer floor=3;
		
		List<Feature> featureRooms=new ArrayList<Feature>();
		featureRooms.add(featureRoom1);
		featureRooms.add(featureRoom2);
		
		List<List<Double>> coordinatesRoom1=new ArrayList<List<Double>>();
		coordinatesRoom1.add(coordinatesRoom11);
		coordinatesRoom1.add(coordinatesRoom12);
		coordinatesRoom1.add(coordinatesRoom13);
		coordinatesRoom1.add(coordinatesRoom14);
		
		Double longitudeCoordinateRoom11=4.0;
		Double latitudeCoordinateRoom11=5.0;
		Double longitudeCoordinateRoom12=6.0;
		Double latitudeCoordinateRoom12=7.0;
		Double longitudeCoordinateRoom13=8.0;
		Double latitudeCoordinateRoom13=9.0;
		Double areaRoom1=70.0;
		boolean isContained1=true;
		
		List<List<Double>> coordinatesRoom2=new ArrayList<List<Double>>();
		coordinatesRoom2.add(coordinatesRoom21);
		coordinatesRoom2.add(coordinatesRoom22);
		coordinatesRoom2.add(coordinatesRoom23);
		coordinatesRoom2.add(coordinatesRoom24);
		
		Double longitudeCoordinateRoom21=10.0;
		Double latitudeCoordinateRoom21=11.0;
		Double longitudeCoordinateRoom22=12.0;
		Double latitudeCoordinateRoom22=13.0;
		Double longitudeCoordinateRoom23=14.0;
		Double latitudeCoordinateRoom23=15.0;
		Double areaRoom2=80.0;
					
		Mockito.when(mapRepository.searchByLocation(appIdentifier, longitude, latitude, floor)).thenReturn(featureRooms);
	
		PowerMockito.whenNew(SimplePolygon2D.class).withNoArguments().thenReturn(simplePolygon2DRoom1,simplePolygon2DRoom2);
		
		Mockito.when(featureRoom1.getGeometry()).thenReturn(geometry1);
		Mockito.when(geometry1.getCoordinates()).thenReturn(coordinatesRoom1);
		Mockito.when(coordinatesRoom11.get(0)).thenReturn(longitudeCoordinateRoom11);
		Mockito.when(coordinatesRoom11.get(1)).thenReturn(latitudeCoordinateRoom11);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom11,latitudeCoordinateRoom11).thenReturn(point11);
		Mockito.when(coordinatesRoom12.get(0)).thenReturn(longitudeCoordinateRoom12);
		Mockito.when(coordinatesRoom12.get(1)).thenReturn(latitudeCoordinateRoom12);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom12,latitudeCoordinateRoom12).thenReturn(point12);
		Mockito.when(coordinatesRoom13.get(0)).thenReturn(longitudeCoordinateRoom13);
		Mockito.when(coordinatesRoom13.get(1)).thenReturn(latitudeCoordinateRoom13);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom13,latitudeCoordinateRoom13).thenReturn(point13);
		Mockito.when(simplePolygon2DRoom1.area()).thenReturn(areaRoom1);
				
		Mockito.when(featureRoom2.getGeometry()).thenReturn(geometry2);
		Mockito.when(geometry2.getCoordinates()).thenReturn(coordinatesRoom2);
		Mockito.when(coordinatesRoom21.get(0)).thenReturn(longitudeCoordinateRoom21);
		Mockito.when(coordinatesRoom21.get(1)).thenReturn(latitudeCoordinateRoom21);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom21,latitudeCoordinateRoom21).thenReturn(point21);
		Mockito.when(coordinatesRoom22.get(0)).thenReturn(longitudeCoordinateRoom22);
		Mockito.when(coordinatesRoom22.get(1)).thenReturn(latitudeCoordinateRoom22);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom22,latitudeCoordinateRoom22).thenReturn(point22);
		Mockito.when(coordinatesRoom23.get(0)).thenReturn(longitudeCoordinateRoom23);
		Mockito.when(coordinatesRoom23.get(1)).thenReturn(latitudeCoordinateRoom23);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom23,latitudeCoordinateRoom23).thenReturn(point23);
		Mockito.when(simplePolygon2DRoom2.area()).thenReturn(areaRoom2);
		
		Mockito.when(simplePolygon2DRoom1.contains(longitude,latitude)).thenReturn(isContained1);
					
		//Experimentation
		Feature feature = searchManagerImpl.getRoomByLocation(appIdentifier, longitude, latitude, floor);
					
		//Expectation		
		Mockito.verify(mapRepository).searchByLocation(appIdentifier,longitude,latitude,floor);
		
		Mockito.verify(featureRoom1).getGeometry();
		Mockito.verify(geometry1).getCoordinates();
		Mockito.verify(simplePolygon2DRoom1).addVertex(point11);
		Mockito.verify(simplePolygon2DRoom1).addVertex(point12);
		Mockito.verify(simplePolygon2DRoom1).addVertex(point13);
		
		Mockito.verify(featureRoom2).getGeometry();
		Mockito.verify(geometry2).getCoordinates();
		Mockito.verify(simplePolygon2DRoom2).addVertex(point21);
		Mockito.verify(simplePolygon2DRoom2).addVertex(point22);
		Mockito.verify(simplePolygon2DRoom2).addVertex(point23);		
				
		Mockito.verify(simplePolygon2DRoom1).contains(longitude,latitude);
		
		assertNotNull("Feature must not be null",feature);
		assertEquals("Feature must be the same", featureRoom1, feature);
	}
	
	@Test
	public void getRoomByLocationWithoutOrderTest() throws Exception {
		//Fixture
		String appIdentifier="appIdentifier";
		Double longitude=1.0;
		Double latitude=2.0;
		Integer floor=3;
		
		List<Feature> featureRooms=new ArrayList<Feature>();
		featureRooms.add(featureRoom1);
		featureRooms.add(featureRoom2);
		
		List<List<Double>> coordinatesRoom1=new ArrayList<List<Double>>();
		coordinatesRoom1.add(coordinatesRoom11);
		coordinatesRoom1.add(coordinatesRoom12);
		coordinatesRoom1.add(coordinatesRoom13);
		coordinatesRoom1.add(coordinatesRoom14);
		
		Double longitudeCoordinateRoom11=4.0;
		Double latitudeCoordinateRoom11=5.0;
		Double longitudeCoordinateRoom12=6.0;
		Double latitudeCoordinateRoom12=7.0;
		Double longitudeCoordinateRoom13=8.0;
		Double latitudeCoordinateRoom13=9.0;
		Double areaRoom1=90.0;
		
		List<List<Double>> coordinatesRoom2=new ArrayList<List<Double>>();
		coordinatesRoom2.add(coordinatesRoom21);
		coordinatesRoom2.add(coordinatesRoom22);
		coordinatesRoom2.add(coordinatesRoom23);
		coordinatesRoom2.add(coordinatesRoom24);
		
		Double longitudeCoordinateRoom21=10.0;
		Double latitudeCoordinateRoom21=11.0;
		Double longitudeCoordinateRoom22=12.0;
		Double latitudeCoordinateRoom22=13.0;
		Double longitudeCoordinateRoom23=14.0;
		Double latitudeCoordinateRoom23=15.0;
		Double areaRoom2=80.0;
		boolean isContained2=true;
					
		Mockito.when(mapRepository.searchByLocation(appIdentifier, longitude, latitude, floor)).thenReturn(featureRooms);
	
		PowerMockito.whenNew(SimplePolygon2D.class).withNoArguments().thenReturn(simplePolygon2DRoom1,simplePolygon2DRoom2);
		
		Mockito.when(featureRoom1.getGeometry()).thenReturn(geometry1);
		Mockito.when(geometry1.getCoordinates()).thenReturn(coordinatesRoom1);
		Mockito.when(coordinatesRoom11.get(0)).thenReturn(longitudeCoordinateRoom11);
		Mockito.when(coordinatesRoom11.get(1)).thenReturn(latitudeCoordinateRoom11);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom11,latitudeCoordinateRoom11).thenReturn(point11);
		Mockito.when(coordinatesRoom12.get(0)).thenReturn(longitudeCoordinateRoom12);
		Mockito.when(coordinatesRoom12.get(1)).thenReturn(latitudeCoordinateRoom12);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom12,latitudeCoordinateRoom12).thenReturn(point12);
		Mockito.when(coordinatesRoom13.get(0)).thenReturn(longitudeCoordinateRoom13);
		Mockito.when(coordinatesRoom13.get(1)).thenReturn(latitudeCoordinateRoom13);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom13,latitudeCoordinateRoom13).thenReturn(point13);
		Mockito.when(simplePolygon2DRoom1.area()).thenReturn(areaRoom1);
				
		Mockito.when(featureRoom2.getGeometry()).thenReturn(geometry2);
		Mockito.when(geometry2.getCoordinates()).thenReturn(coordinatesRoom2);
		Mockito.when(coordinatesRoom21.get(0)).thenReturn(longitudeCoordinateRoom21);
		Mockito.when(coordinatesRoom21.get(1)).thenReturn(latitudeCoordinateRoom21);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom21,latitudeCoordinateRoom21).thenReturn(point21);
		Mockito.when(coordinatesRoom22.get(0)).thenReturn(longitudeCoordinateRoom22);
		Mockito.when(coordinatesRoom22.get(1)).thenReturn(latitudeCoordinateRoom22);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom22,latitudeCoordinateRoom22).thenReturn(point22);
		Mockito.when(coordinatesRoom23.get(0)).thenReturn(longitudeCoordinateRoom23);
		Mockito.when(coordinatesRoom23.get(1)).thenReturn(latitudeCoordinateRoom23);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom23,latitudeCoordinateRoom23).thenReturn(point23);
		Mockito.when(simplePolygon2DRoom2.area()).thenReturn(areaRoom2);
		
		Mockito.when(simplePolygon2DRoom2.contains(longitude,latitude)).thenReturn(isContained2);
					
		//Experimentation
		Feature feature = searchManagerImpl.getRoomByLocation(appIdentifier, longitude, latitude, floor);
					
		//Expectation		
		Mockito.verify(mapRepository).searchByLocation(appIdentifier,longitude,latitude,floor);
		
		Mockito.verify(featureRoom1).getGeometry();
		Mockito.verify(geometry1).getCoordinates();
		Mockito.verify(simplePolygon2DRoom1).addVertex(point11);
		Mockito.verify(simplePolygon2DRoom1).addVertex(point12);
		Mockito.verify(simplePolygon2DRoom1).addVertex(point13);
		
		Mockito.verify(featureRoom2).getGeometry();
		Mockito.verify(geometry2).getCoordinates();
		Mockito.verify(simplePolygon2DRoom2).addVertex(point21);
		Mockito.verify(simplePolygon2DRoom2).addVertex(point22);
		Mockito.verify(simplePolygon2DRoom2).addVertex(point23);		
				
		Mockito.verify(simplePolygon2DRoom2).contains(longitude,latitude);
		
		assertNotNull("Feature must not be null",feature);
		assertEquals("Feature must be the same", featureRoom2, feature);
	}
	
	@Test
	public void getRoomByLocationWithoutOrderNegativeAreaTest() throws Exception {
		//Fixture
		String appIdentifier="appIdentifier";
		Double longitude=1.0;
		Double latitude=2.0;
		Integer floor=3;
		
		List<Feature> featureRooms=new ArrayList<Feature>();
		featureRooms.add(featureRoom1);
		featureRooms.add(featureRoom2);
		
		List<List<Double>> coordinatesRoom1=new ArrayList<List<Double>>();
		coordinatesRoom1.add(coordinatesRoom11);
		coordinatesRoom1.add(coordinatesRoom12);
		coordinatesRoom1.add(coordinatesRoom13);
		coordinatesRoom1.add(coordinatesRoom14);
		
		Double longitudeCoordinateRoom11=4.0;
		Double latitudeCoordinateRoom11=5.0;
		Double longitudeCoordinateRoom12=6.0;
		Double latitudeCoordinateRoom12=7.0;
		Double longitudeCoordinateRoom13=8.0;
		Double latitudeCoordinateRoom13=9.0;
		Double areaRoom1=90.0;
		
		List<List<Double>> coordinatesRoom2=new ArrayList<List<Double>>();
		coordinatesRoom2.add(coordinatesRoom21);
		coordinatesRoom2.add(coordinatesRoom22);
		coordinatesRoom2.add(coordinatesRoom23);
		coordinatesRoom2.add(coordinatesRoom24);
		
		Double longitudeCoordinateRoom21=10.0;
		Double latitudeCoordinateRoom21=11.0;
		Double longitudeCoordinateRoom22=12.0;
		Double latitudeCoordinateRoom22=13.0;
		Double longitudeCoordinateRoom23=14.0;
		Double latitudeCoordinateRoom23=15.0;
		Double areaRoom2=-80.0;
		boolean isContained2=true;
					
		Mockito.when(mapRepository.searchByLocation(appIdentifier, longitude, latitude, floor)).thenReturn(featureRooms);
	
		PowerMockito.whenNew(SimplePolygon2D.class).withNoArguments().thenReturn(simplePolygon2DRoom1,simplePolygon2DRoom2);
		
		Mockito.when(featureRoom1.getGeometry()).thenReturn(geometry1);
		Mockito.when(geometry1.getCoordinates()).thenReturn(coordinatesRoom1);
		Mockito.when(coordinatesRoom11.get(0)).thenReturn(longitudeCoordinateRoom11);
		Mockito.when(coordinatesRoom11.get(1)).thenReturn(latitudeCoordinateRoom11);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom11,latitudeCoordinateRoom11).thenReturn(point11);
		Mockito.when(coordinatesRoom12.get(0)).thenReturn(longitudeCoordinateRoom12);
		Mockito.when(coordinatesRoom12.get(1)).thenReturn(latitudeCoordinateRoom12);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom12,latitudeCoordinateRoom12).thenReturn(point12);
		Mockito.when(coordinatesRoom13.get(0)).thenReturn(longitudeCoordinateRoom13);
		Mockito.when(coordinatesRoom13.get(1)).thenReturn(latitudeCoordinateRoom13);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom13,latitudeCoordinateRoom13).thenReturn(point13);
		Mockito.when(simplePolygon2DRoom1.area()).thenReturn(areaRoom1);
				
		Mockito.when(featureRoom2.getGeometry()).thenReturn(geometry2);
		Mockito.when(geometry2.getCoordinates()).thenReturn(coordinatesRoom2);
		Mockito.when(coordinatesRoom21.get(0)).thenReturn(longitudeCoordinateRoom21);
		Mockito.when(coordinatesRoom21.get(1)).thenReturn(latitudeCoordinateRoom21);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom21,latitudeCoordinateRoom21).thenReturn(point21);
		Mockito.when(coordinatesRoom22.get(0)).thenReturn(longitudeCoordinateRoom22);
		Mockito.when(coordinatesRoom22.get(1)).thenReturn(latitudeCoordinateRoom22);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom22,latitudeCoordinateRoom22).thenReturn(point22);
		Mockito.when(coordinatesRoom23.get(0)).thenReturn(longitudeCoordinateRoom23);
		Mockito.when(coordinatesRoom23.get(1)).thenReturn(latitudeCoordinateRoom23);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom23,latitudeCoordinateRoom23).thenReturn(point23);
		Mockito.when(simplePolygon2DRoom2.area()).thenReturn(areaRoom2);
		Mockito.when(simplePolygon2DRoom2.complement()).thenReturn(simplePolygon2DRoom2Complement);
		Mockito.when(simplePolygon2DRoom2Complement.area()).thenReturn(Math.abs(areaRoom2));
		
		Mockito.when(simplePolygon2DRoom2Complement.contains(longitude,latitude)).thenReturn(isContained2);
					
		//Experimentation
		Feature feature = searchManagerImpl.getRoomByLocation(appIdentifier, longitude, latitude, floor);
					
		//Expectation		
		Mockito.verify(mapRepository).searchByLocation(appIdentifier,longitude,latitude,floor);
		
		Mockito.verify(featureRoom1).getGeometry();
		Mockito.verify(geometry1).getCoordinates();
		Mockito.verify(simplePolygon2DRoom1).addVertex(point11);
		Mockito.verify(simplePolygon2DRoom1).addVertex(point12);
		Mockito.verify(simplePolygon2DRoom1).addVertex(point13);
		
		Mockito.verify(featureRoom2).getGeometry();
		Mockito.verify(geometry2).getCoordinates();
		Mockito.verify(simplePolygon2DRoom2).addVertex(point21);
		Mockito.verify(simplePolygon2DRoom2).addVertex(point22);
		Mockito.verify(simplePolygon2DRoom2).addVertex(point23);
		Mockito.verify(simplePolygon2DRoom2).complement();
				
		Mockito.verify(simplePolygon2DRoom2Complement).contains(longitude,latitude);
		
		assertNotNull("Feature must not be null",feature);
		assertEquals("Feature must be the same", featureRoom2, feature);
	}
	
	@Test
	public void getRoomByLocationInOrderWithNoContainTest() throws Exception {
		//Fixture
		String appIdentifier="appIdentifier";
		Double longitude=1.0;
		Double latitude=2.0;
		Integer floor=3;
		
		List<Feature> featureRooms=new ArrayList<Feature>();
		featureRooms.add(featureRoom1);
		featureRooms.add(featureRoom2);
		
		List<List<Double>> coordinatesRoom1=new ArrayList<List<Double>>();
		coordinatesRoom1.add(coordinatesRoom11);
		coordinatesRoom1.add(coordinatesRoom12);
		coordinatesRoom1.add(coordinatesRoom13);
		coordinatesRoom1.add(coordinatesRoom14);
		
		Double longitudeCoordinateRoom11=4.0;
		Double latitudeCoordinateRoom11=5.0;
		Double longitudeCoordinateRoom12=6.0;
		Double latitudeCoordinateRoom12=7.0;
		Double longitudeCoordinateRoom13=8.0;
		Double latitudeCoordinateRoom13=9.0;
		Double areaRoom1=70.0;
		boolean isContained1=false;
		
		List<List<Double>> coordinatesRoom2=new ArrayList<List<Double>>();
		coordinatesRoom2.add(coordinatesRoom21);
		coordinatesRoom2.add(coordinatesRoom22);
		coordinatesRoom2.add(coordinatesRoom23);
		coordinatesRoom2.add(coordinatesRoom24);
		
		Double longitudeCoordinateRoom21=10.0;
		Double latitudeCoordinateRoom21=11.0;
		Double longitudeCoordinateRoom22=12.0;
		Double latitudeCoordinateRoom22=13.0;
		Double longitudeCoordinateRoom23=14.0;
		Double latitudeCoordinateRoom23=15.0;
		Double areaRoom2=80.0;
		boolean isContained2=true;
					
		Mockito.when(mapRepository.searchByLocation(appIdentifier, longitude, latitude, floor)).thenReturn(featureRooms);
	
		PowerMockito.whenNew(SimplePolygon2D.class).withNoArguments().thenReturn(simplePolygon2DRoom1,simplePolygon2DRoom2);
		
		Mockito.when(featureRoom1.getGeometry()).thenReturn(geometry1);
		Mockito.when(geometry1.getCoordinates()).thenReturn(coordinatesRoom1);
		Mockito.when(coordinatesRoom11.get(0)).thenReturn(longitudeCoordinateRoom11);
		Mockito.when(coordinatesRoom11.get(1)).thenReturn(latitudeCoordinateRoom11);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom11,latitudeCoordinateRoom11).thenReturn(point11);
		Mockito.when(coordinatesRoom12.get(0)).thenReturn(longitudeCoordinateRoom12);
		Mockito.when(coordinatesRoom12.get(1)).thenReturn(latitudeCoordinateRoom12);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom12,latitudeCoordinateRoom12).thenReturn(point12);
		Mockito.when(coordinatesRoom13.get(0)).thenReturn(longitudeCoordinateRoom13);
		Mockito.when(coordinatesRoom13.get(1)).thenReturn(latitudeCoordinateRoom13);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom13,latitudeCoordinateRoom13).thenReturn(point13);
		Mockito.when(simplePolygon2DRoom1.area()).thenReturn(areaRoom1);
				
		Mockito.when(featureRoom2.getGeometry()).thenReturn(geometry2);
		Mockito.when(geometry2.getCoordinates()).thenReturn(coordinatesRoom2);
		Mockito.when(coordinatesRoom21.get(0)).thenReturn(longitudeCoordinateRoom21);
		Mockito.when(coordinatesRoom21.get(1)).thenReturn(latitudeCoordinateRoom21);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom21,latitudeCoordinateRoom21).thenReturn(point21);
		Mockito.when(coordinatesRoom22.get(0)).thenReturn(longitudeCoordinateRoom22);
		Mockito.when(coordinatesRoom22.get(1)).thenReturn(latitudeCoordinateRoom22);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom22,latitudeCoordinateRoom22).thenReturn(point22);
		Mockito.when(coordinatesRoom23.get(0)).thenReturn(longitudeCoordinateRoom23);
		Mockito.when(coordinatesRoom23.get(1)).thenReturn(latitudeCoordinateRoom23);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom23,latitudeCoordinateRoom23).thenReturn(point23);
		Mockito.when(simplePolygon2DRoom2.area()).thenReturn(areaRoom2);
		
		Mockito.when(simplePolygon2DRoom1.contains(longitude,latitude)).thenReturn(isContained1);
		Mockito.when(simplePolygon2DRoom2.contains(longitude,latitude)).thenReturn(isContained2);
					
		//Experimentation
		Feature feature = searchManagerImpl.getRoomByLocation(appIdentifier, longitude, latitude, floor);
					
		//Expectation		
		Mockito.verify(mapRepository).searchByLocation(appIdentifier,longitude,latitude,floor);
		
		Mockito.verify(featureRoom1).getGeometry();
		Mockito.verify(geometry1).getCoordinates();
		Mockito.verify(simplePolygon2DRoom1).addVertex(point11);
		Mockito.verify(simplePolygon2DRoom1).addVertex(point12);
		Mockito.verify(simplePolygon2DRoom1).addVertex(point13);
		
		Mockito.verify(featureRoom2).getGeometry();
		Mockito.verify(geometry2).getCoordinates();
		Mockito.verify(simplePolygon2DRoom2).addVertex(point21);
		Mockito.verify(simplePolygon2DRoom2).addVertex(point22);
		Mockito.verify(simplePolygon2DRoom2).addVertex(point23);		
				
		Mockito.verify(simplePolygon2DRoom1).contains(longitude,latitude);
		Mockito.verify(simplePolygon2DRoom2).contains(longitude,latitude);
		
		assertNotNull("Feature must not be null",feature);
		assertEquals("Feature must be the same", featureRoom2, feature);
	}
	
	@Test
	public void getRoomByLocationWithoutOrderWithNoContainTest() throws Exception {
		//Fixture
		String appIdentifier="appIdentifier";
		Double longitude=1.0;
		Double latitude=2.0;
		Integer floor=3;
		
		List<Feature> featureRooms=new ArrayList<Feature>();
		featureRooms.add(featureRoom1);
		featureRooms.add(featureRoom2);
		
		List<List<Double>> coordinatesRoom1=new ArrayList<List<Double>>();
		coordinatesRoom1.add(coordinatesRoom11);
		coordinatesRoom1.add(coordinatesRoom12);
		coordinatesRoom1.add(coordinatesRoom13);
		coordinatesRoom1.add(coordinatesRoom14);
		
		Double longitudeCoordinateRoom11=4.0;
		Double latitudeCoordinateRoom11=5.0;
		Double longitudeCoordinateRoom12=6.0;
		Double latitudeCoordinateRoom12=7.0;
		Double longitudeCoordinateRoom13=8.0;
		Double latitudeCoordinateRoom13=9.0;
		Double areaRoom1=90.0;
		boolean isContained1=true;
		
		List<List<Double>> coordinatesRoom2=new ArrayList<List<Double>>();
		coordinatesRoom2.add(coordinatesRoom21);
		coordinatesRoom2.add(coordinatesRoom22);
		coordinatesRoom2.add(coordinatesRoom23);
		coordinatesRoom2.add(coordinatesRoom24);
		
		Double longitudeCoordinateRoom21=10.0;
		Double latitudeCoordinateRoom21=11.0;
		Double longitudeCoordinateRoom22=12.0;
		Double latitudeCoordinateRoom22=13.0;
		Double longitudeCoordinateRoom23=14.0;
		Double latitudeCoordinateRoom23=15.0;
		Double areaRoom2=80.0;
		boolean isContained2=false;
					
		Mockito.when(mapRepository.searchByLocation(appIdentifier, longitude, latitude, floor)).thenReturn(featureRooms);
	
		PowerMockito.whenNew(SimplePolygon2D.class).withNoArguments().thenReturn(simplePolygon2DRoom1,simplePolygon2DRoom2);
		
		Mockito.when(featureRoom1.getGeometry()).thenReturn(geometry1);
		Mockito.when(geometry1.getCoordinates()).thenReturn(coordinatesRoom1);
		Mockito.when(coordinatesRoom11.get(0)).thenReturn(longitudeCoordinateRoom11);
		Mockito.when(coordinatesRoom11.get(1)).thenReturn(latitudeCoordinateRoom11);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom11,latitudeCoordinateRoom11).thenReturn(point11);
		Mockito.when(coordinatesRoom12.get(0)).thenReturn(longitudeCoordinateRoom12);
		Mockito.when(coordinatesRoom12.get(1)).thenReturn(latitudeCoordinateRoom12);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom12,latitudeCoordinateRoom12).thenReturn(point12);
		Mockito.when(coordinatesRoom13.get(0)).thenReturn(longitudeCoordinateRoom13);
		Mockito.when(coordinatesRoom13.get(1)).thenReturn(latitudeCoordinateRoom13);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom13,latitudeCoordinateRoom13).thenReturn(point13);
		Mockito.when(simplePolygon2DRoom1.area()).thenReturn(areaRoom1);
				
		Mockito.when(featureRoom2.getGeometry()).thenReturn(geometry2);
		Mockito.when(geometry2.getCoordinates()).thenReturn(coordinatesRoom2);
		Mockito.when(coordinatesRoom21.get(0)).thenReturn(longitudeCoordinateRoom21);
		Mockito.when(coordinatesRoom21.get(1)).thenReturn(latitudeCoordinateRoom21);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom21,latitudeCoordinateRoom21).thenReturn(point21);
		Mockito.when(coordinatesRoom22.get(0)).thenReturn(longitudeCoordinateRoom22);
		Mockito.when(coordinatesRoom22.get(1)).thenReturn(latitudeCoordinateRoom22);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom22,latitudeCoordinateRoom22).thenReturn(point22);
		Mockito.when(coordinatesRoom23.get(0)).thenReturn(longitudeCoordinateRoom23);
		Mockito.when(coordinatesRoom23.get(1)).thenReturn(latitudeCoordinateRoom23);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom23,latitudeCoordinateRoom23).thenReturn(point23);
		Mockito.when(simplePolygon2DRoom2.area()).thenReturn(areaRoom2);
		
		Mockito.when(simplePolygon2DRoom1.contains(longitude,latitude)).thenReturn(isContained1);
		Mockito.when(simplePolygon2DRoom2.contains(longitude,latitude)).thenReturn(isContained2);
					
		//Experimentation
		Feature feature = searchManagerImpl.getRoomByLocation(appIdentifier, longitude, latitude, floor);
					
		//Expectation		
		Mockito.verify(mapRepository).searchByLocation(appIdentifier,longitude,latitude,floor);
		
		Mockito.verify(featureRoom1).getGeometry();
		Mockito.verify(geometry1).getCoordinates();
		Mockito.verify(simplePolygon2DRoom1).addVertex(point11);
		Mockito.verify(simplePolygon2DRoom1).addVertex(point12);
		Mockito.verify(simplePolygon2DRoom1).addVertex(point13);
		
		Mockito.verify(featureRoom2).getGeometry();
		Mockito.verify(geometry2).getCoordinates();
		Mockito.verify(simplePolygon2DRoom2).addVertex(point21);
		Mockito.verify(simplePolygon2DRoom2).addVertex(point22);
		Mockito.verify(simplePolygon2DRoom2).addVertex(point23);		
			
		Mockito.verify(simplePolygon2DRoom1).contains(longitude,latitude);
		Mockito.verify(simplePolygon2DRoom2).contains(longitude,latitude);
		
		assertNotNull("Feature must not be null",feature);
		assertEquals("Feature must be the same", featureRoom1, feature);
	}
	
	@Test
	public void getRoomByLocationWithoutOrderNegativeAreaWithNoContainTest() throws Exception {
		//Fixture
		String appIdentifier="appIdentifier";
		Double longitude=1.0;
		Double latitude=2.0;
		Integer floor=3;
		
		List<Feature> featureRooms=new ArrayList<Feature>();
		featureRooms.add(featureRoom1);
		featureRooms.add(featureRoom2);
		
		List<List<Double>> coordinatesRoom1=new ArrayList<List<Double>>();
		coordinatesRoom1.add(coordinatesRoom11);
		coordinatesRoom1.add(coordinatesRoom12);
		coordinatesRoom1.add(coordinatesRoom13);
		coordinatesRoom1.add(coordinatesRoom14);
		
		Double longitudeCoordinateRoom11=4.0;
		Double latitudeCoordinateRoom11=5.0;
		Double longitudeCoordinateRoom12=6.0;
		Double latitudeCoordinateRoom12=7.0;
		Double longitudeCoordinateRoom13=8.0;
		Double latitudeCoordinateRoom13=9.0;
		Double areaRoom1=90.0;
		boolean isContained1=false;
		
		List<List<Double>> coordinatesRoom2=new ArrayList<List<Double>>();
		coordinatesRoom2.add(coordinatesRoom21);
		coordinatesRoom2.add(coordinatesRoom22);
		coordinatesRoom2.add(coordinatesRoom23);
		coordinatesRoom2.add(coordinatesRoom24);
		
		Double longitudeCoordinateRoom21=10.0;
		Double latitudeCoordinateRoom21=11.0;
		Double longitudeCoordinateRoom22=12.0;
		Double latitudeCoordinateRoom22=13.0;
		Double longitudeCoordinateRoom23=14.0;
		Double latitudeCoordinateRoom23=15.0;
		Double areaRoom2=-95.0;
		boolean isContained2=true;
					
		Mockito.when(mapRepository.searchByLocation(appIdentifier, longitude, latitude, floor)).thenReturn(featureRooms);
	
		PowerMockito.whenNew(SimplePolygon2D.class).withNoArguments().thenReturn(simplePolygon2DRoom1,simplePolygon2DRoom2);
		
		Mockito.when(featureRoom1.getGeometry()).thenReturn(geometry1);
		Mockito.when(geometry1.getCoordinates()).thenReturn(coordinatesRoom1);
		Mockito.when(coordinatesRoom11.get(0)).thenReturn(longitudeCoordinateRoom11);
		Mockito.when(coordinatesRoom11.get(1)).thenReturn(latitudeCoordinateRoom11);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom11,latitudeCoordinateRoom11).thenReturn(point11);
		Mockito.when(coordinatesRoom12.get(0)).thenReturn(longitudeCoordinateRoom12);
		Mockito.when(coordinatesRoom12.get(1)).thenReturn(latitudeCoordinateRoom12);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom12,latitudeCoordinateRoom12).thenReturn(point12);
		Mockito.when(coordinatesRoom13.get(0)).thenReturn(longitudeCoordinateRoom13);
		Mockito.when(coordinatesRoom13.get(1)).thenReturn(latitudeCoordinateRoom13);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom13,latitudeCoordinateRoom13).thenReturn(point13);
		Mockito.when(simplePolygon2DRoom1.area()).thenReturn(areaRoom1);
				
		Mockito.when(featureRoom2.getGeometry()).thenReturn(geometry2);
		Mockito.when(geometry2.getCoordinates()).thenReturn(coordinatesRoom2);
		Mockito.when(coordinatesRoom21.get(0)).thenReturn(longitudeCoordinateRoom21);
		Mockito.when(coordinatesRoom21.get(1)).thenReturn(latitudeCoordinateRoom21);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom21,latitudeCoordinateRoom21).thenReturn(point21);
		Mockito.when(coordinatesRoom22.get(0)).thenReturn(longitudeCoordinateRoom22);
		Mockito.when(coordinatesRoom22.get(1)).thenReturn(latitudeCoordinateRoom22);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom22,latitudeCoordinateRoom22).thenReturn(point22);
		Mockito.when(coordinatesRoom23.get(0)).thenReturn(longitudeCoordinateRoom23);
		Mockito.when(coordinatesRoom23.get(1)).thenReturn(latitudeCoordinateRoom23);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom23,latitudeCoordinateRoom23).thenReturn(point23);
		Mockito.when(simplePolygon2DRoom2.area()).thenReturn(areaRoom2);
		Mockito.when(simplePolygon2DRoom2.complement()).thenReturn(simplePolygon2DRoom2Complement);
		Mockito.when(simplePolygon2DRoom2Complement.area()).thenReturn(Math.abs(areaRoom2));
		
		Mockito.when(simplePolygon2DRoom1.contains(longitude,latitude)).thenReturn(isContained1);
		Mockito.when(simplePolygon2DRoom2Complement.contains(longitude,latitude)).thenReturn(isContained2);
					
		//Experimentation
		Feature feature = searchManagerImpl.getRoomByLocation(appIdentifier, longitude, latitude, floor);
					
		//Expectation		
		Mockito.verify(mapRepository).searchByLocation(appIdentifier,longitude,latitude,floor);
		
		Mockito.verify(featureRoom1).getGeometry();
		Mockito.verify(geometry1).getCoordinates();
		Mockito.verify(simplePolygon2DRoom1).addVertex(point11);
		Mockito.verify(simplePolygon2DRoom1).addVertex(point12);
		Mockito.verify(simplePolygon2DRoom1).addVertex(point13);
		
		Mockito.verify(featureRoom2).getGeometry();
		Mockito.verify(geometry2).getCoordinates();
		Mockito.verify(simplePolygon2DRoom2).addVertex(point21);
		Mockito.verify(simplePolygon2DRoom2).addVertex(point22);
		Mockito.verify(simplePolygon2DRoom2).addVertex(point23);
		Mockito.verify(simplePolygon2DRoom2).complement();
			
		Mockito.verify(simplePolygon2DRoom1).contains(longitude,latitude);
		Mockito.verify(simplePolygon2DRoom2Complement).contains(longitude,latitude);
		
		assertNotNull("Feature must not be null",feature);
		assertEquals("Feature must be the same", featureRoom2, feature);
	}
	
	@Test(expected=RoomNotFoundException.class)
	public void getRoomByLocationRoomNotFoundExceptionTest() throws Exception {
		//Fixture
		String appIdentifier="appIdentifier";
		Double longitude=1.0;
		Double latitude=2.0;
		Integer floor=3;
		
		List<Feature> featureRooms=new ArrayList<Feature>();
		featureRooms.add(featureRoom1);
		featureRooms.add(featureRoom2);
		
		List<List<Double>> coordinatesRoom1=new ArrayList<List<Double>>();
		coordinatesRoom1.add(coordinatesRoom11);
		coordinatesRoom1.add(coordinatesRoom12);
		coordinatesRoom1.add(coordinatesRoom13);
		coordinatesRoom1.add(coordinatesRoom14);
		
		Double longitudeCoordinateRoom11=4.0;
		Double latitudeCoordinateRoom11=5.0;
		Double longitudeCoordinateRoom12=6.0;
		Double latitudeCoordinateRoom12=7.0;
		Double longitudeCoordinateRoom13=8.0;
		Double latitudeCoordinateRoom13=9.0;
		Double areaRoom1=70.0;
		boolean isContained1=false;
		
		List<List<Double>> coordinatesRoom2=new ArrayList<List<Double>>();
		coordinatesRoom2.add(coordinatesRoom21);
		coordinatesRoom2.add(coordinatesRoom22);
		coordinatesRoom2.add(coordinatesRoom23);
		coordinatesRoom2.add(coordinatesRoom24);
		
		Double longitudeCoordinateRoom21=10.0;
		Double latitudeCoordinateRoom21=11.0;
		Double longitudeCoordinateRoom22=12.0;
		Double latitudeCoordinateRoom22=13.0;
		Double longitudeCoordinateRoom23=14.0;
		Double latitudeCoordinateRoom23=15.0;
		Double areaRoom2=-60.0;
		boolean isContained2=false;
					
		Mockito.when(mapRepository.searchByLocation(appIdentifier, longitude, latitude, floor)).thenReturn(featureRooms);
	
		PowerMockito.whenNew(SimplePolygon2D.class).withNoArguments().thenReturn(simplePolygon2DRoom1,simplePolygon2DRoom2);
		
		Mockito.when(featureRoom1.getGeometry()).thenReturn(geometry1);
		Mockito.when(geometry1.getCoordinates()).thenReturn(coordinatesRoom1);
		Mockito.when(coordinatesRoom11.get(0)).thenReturn(longitudeCoordinateRoom11);
		Mockito.when(coordinatesRoom11.get(1)).thenReturn(latitudeCoordinateRoom11);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom11,latitudeCoordinateRoom11).thenReturn(point11);
		Mockito.when(coordinatesRoom12.get(0)).thenReturn(longitudeCoordinateRoom12);
		Mockito.when(coordinatesRoom12.get(1)).thenReturn(latitudeCoordinateRoom12);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom12,latitudeCoordinateRoom12).thenReturn(point12);
		Mockito.when(coordinatesRoom13.get(0)).thenReturn(longitudeCoordinateRoom13);
		Mockito.when(coordinatesRoom13.get(1)).thenReturn(latitudeCoordinateRoom13);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom13,latitudeCoordinateRoom13).thenReturn(point13);
		Mockito.when(simplePolygon2DRoom1.area()).thenReturn(areaRoom1);
				
		Mockito.when(featureRoom2.getGeometry()).thenReturn(geometry2);
		Mockito.when(geometry2.getCoordinates()).thenReturn(coordinatesRoom2);
		Mockito.when(coordinatesRoom21.get(0)).thenReturn(longitudeCoordinateRoom21);
		Mockito.when(coordinatesRoom21.get(1)).thenReturn(latitudeCoordinateRoom21);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom21,latitudeCoordinateRoom21).thenReturn(point21);
		Mockito.when(coordinatesRoom22.get(0)).thenReturn(longitudeCoordinateRoom22);
		Mockito.when(coordinatesRoom22.get(1)).thenReturn(latitudeCoordinateRoom22);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom22,latitudeCoordinateRoom22).thenReturn(point22);
		Mockito.when(coordinatesRoom23.get(0)).thenReturn(longitudeCoordinateRoom23);
		Mockito.when(coordinatesRoom23.get(1)).thenReturn(latitudeCoordinateRoom23);
		PowerMockito.whenNew(Point2D.class).withArguments(longitudeCoordinateRoom23,latitudeCoordinateRoom23).thenReturn(point23);
		Mockito.when(simplePolygon2DRoom2.area()).thenReturn(areaRoom2);
		Mockito.when(simplePolygon2DRoom2.complement()).thenReturn(simplePolygon2DRoom2Complement);
		Mockito.when(simplePolygon2DRoom2Complement.area()).thenReturn(Math.abs(areaRoom2));
		
		Mockito.when(simplePolygon2DRoom1.contains(longitude,latitude)).thenReturn(isContained1);
		Mockito.when(simplePolygon2DRoom2Complement.contains(longitude,latitude)).thenReturn(isContained2);
					
		//Experimentation
		searchManagerImpl.getRoomByLocation(appIdentifier, longitude, latitude, floor);
					
		//Expectation		
		Mockito.verify(mapRepository).searchByLocation(appIdentifier,longitude,latitude,floor);
		
		Mockito.verify(featureRoom1).getGeometry();
		Mockito.verify(geometry1).getCoordinates();
		Mockito.verify(simplePolygon2DRoom1).addVertex(point11);
		Mockito.verify(simplePolygon2DRoom1).addVertex(point12);
		Mockito.verify(simplePolygon2DRoom1).addVertex(point13);
		
		Mockito.verify(featureRoom2).getGeometry();
		Mockito.verify(geometry2).getCoordinates();
		Mockito.verify(simplePolygon2DRoom2).addVertex(point21);
		Mockito.verify(simplePolygon2DRoom2).addVertex(point22);
		Mockito.verify(simplePolygon2DRoom2).addVertex(point23);		
				
		Mockito.verify(simplePolygon2DRoom1).contains(longitude,latitude);
		Mockito.verify(simplePolygon2DRoom2Complement).contains(longitude,latitude);
	}
		
	
	
	
	
}
