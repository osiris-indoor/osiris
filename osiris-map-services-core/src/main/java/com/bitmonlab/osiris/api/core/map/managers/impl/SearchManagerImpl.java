package com.bitmonlab.osiris.api.core.map.managers.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import math.geom2d.polygon.SimplePolygon2D;
import math.geom2d.Point2D;

import com.bitmonlab.osiris.api.core.map.dao.api.FeatureRepository;
import com.bitmonlab.osiris.api.core.map.dao.api.MapRepository;
import com.bitmonlab.osiris.api.core.map.exceptions.QueryException;
import com.bitmonlab.osiris.api.core.map.exceptions.RoomNotFoundException;
import com.bitmonlab.osiris.api.core.map.managers.api.SearchManager;
import com.bitmonlab.osiris.api.core.map.transferobject.LayerDTO;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;
import com.bitmonlab.osiris.commons.map.model.geojson.LineString;

@Named
public class SearchManagerImpl implements SearchManager{
	
	@Inject
	private FeatureRepository featureRepository;
	
	@Inject
	private MapRepository mapRepository;
	
	@Override
	public Feature getRoomByLocation(String appIdentifier, Double longitude, Double latitude, Integer floor) throws RoomNotFoundException {
		// TODO Auto-generated method stub
		List<Feature> featureRooms = mapRepository.searchByLocation(appIdentifier,longitude,latitude,floor);
		
		List<SimplePolygon2D> simplePolygon2DRooms=transformFeatureRoomsInSimplePolygon2DRooms(featureRooms);		
		List<SimplePolygon2D> simplePolygon2DRoomsSortedArea=new ArrayList<SimplePolygon2D>(simplePolygon2DRooms);
		
		sortSimplePolygon2dRoomsByArea(simplePolygon2DRoomsSortedArea);
		
		SimplePolygon2D simplePolygon2DRoomByLocation=getSimplePolygon2DRoomIsContainedAndLessArea(longitude,latitude,simplePolygon2DRoomsSortedArea);
				
		if(simplePolygon2DRoomByLocation==null){
			throw new RoomNotFoundException();
		}
		
		Integer positionRoomByLocation=simplePolygon2DRooms.indexOf(simplePolygon2DRoomByLocation);
		Feature roomByLocation=featureRooms.get(positionRoomByLocation);
		
		return roomByLocation;		
	}
	
	
	@Override
	public Collection<Feature> getFeaturesByQuery(String appIdentifier,
			String query, LayerDTO layer, Integer pageIndex, Integer pageSize) throws QueryException {
		// TODO Auto-generated method stub
		Collection<Feature> collectionFeatures=null;
		switch (layer) {
			case ALL: {
				collectionFeatures=featureRepository.searchIDAppAndQuery(appIdentifier, query, pageIndex, pageSize);
				Collection<Feature> collectionMap=mapRepository.searchIDAppAndQuery(appIdentifier, query, pageIndex, pageSize);
				collectionFeatures.addAll(collectionMap);
				break;
			}
			case FEATURES: {
				collectionFeatures=featureRepository.searchIDAppAndQuery(appIdentifier, query, pageIndex, pageSize);
				break;
			}
			case MAP: {
				collectionFeatures=mapRepository.searchIDAppAndQuery(appIdentifier, query, pageIndex, pageSize);
				break;
			}
		}
		return collectionFeatures;
	}


	@Override
	public Collection<Feature> getFeaturesByQuery(String appIdentifier,
			String query, LayerDTO layer, Integer pageIndex, Integer pageSize,
			String orderField, String order) throws QueryException {
		Collection<Feature> collectionFeatures=null;
		switch (layer) {
			case ALL: {
				collectionFeatures=featureRepository.searchIDAppAndQuery(appIdentifier, query, pageIndex, pageSize, orderField, order);
				Collection<Feature> collectionMap=mapRepository.searchIDAppAndQuery(appIdentifier, query, pageIndex, pageSize, orderField, order);
				collectionFeatures.addAll(collectionMap);
				break;
			}
			case FEATURES: {
				collectionFeatures=featureRepository.searchIDAppAndQuery(appIdentifier, query, pageIndex, pageSize, orderField, order);
				break;
			}
			case MAP: {
				collectionFeatures=mapRepository.searchIDAppAndQuery(appIdentifier, query, pageIndex, pageSize, orderField, order);
				break;
			}
		}
		return collectionFeatures;
	}
	
	
	private List<SimplePolygon2D> transformFeatureRoomsInSimplePolygon2DRooms(Collection<Feature> featureRooms){
		List<SimplePolygon2D> simplePolygon2DRooms=new ArrayList<SimplePolygon2D>();
		for(Feature featureRoom:featureRooms){
			SimplePolygon2D simplePolygon2DRoom=transformFeatureRoomInSimplePolygon2DRoom(featureRoom);
			simplePolygon2DRooms.add(simplePolygon2DRoom);
		}
		return simplePolygon2DRooms;
	}
	
	private SimplePolygon2D transformFeatureRoomInSimplePolygon2DRoom(Feature featureRoom){
		SimplePolygon2D simplePolygon2DRoom=new SimplePolygon2D();
		LineString geometry=(LineString)featureRoom.getGeometry();
		List<List<Double>> coordinatesRoom=geometry.getCoordinates();
		for(int i=0;i<coordinatesRoom.size()-1;i++){
			List<Double> coordinateRoom=coordinatesRoom.get(i);
			Double longitudeCoordinateRoom=coordinateRoom.get(0);
			Double latitudeCoordinateRoom=coordinateRoom.get(1);
			Point2D point=new Point2D(longitudeCoordinateRoom, latitudeCoordinateRoom);
			simplePolygon2DRoom.addVertex(point);
		}
		if(simplePolygon2DRoom.area()<0){
			simplePolygon2DRoom=simplePolygon2DRoom.complement();
		}
		return simplePolygon2DRoom;
	}
	
	private void sortSimplePolygon2dRoomsByArea(List<SimplePolygon2D> simplePolygon2DRoomsSortedArea){
		Collections.sort(simplePolygon2DRoomsSortedArea, new Comparator<SimplePolygon2D>() {
			@Override
			public int compare(SimplePolygon2D simplePolygon2D1, SimplePolygon2D simplePolygon2D2) {
				// TODO Auto-generated method stub
				Double area1=simplePolygon2D1.area();
		    	Double area2=simplePolygon2D2.area();
		    	return area1.compareTo(area2);
			}
		});
	}
	
	private SimplePolygon2D getSimplePolygon2DRoomIsContainedAndLessArea(Double longitude, Double latitude,List<SimplePolygon2D> simplePolygon2DRoomsSortedArea){
		SimplePolygon2D simplePolygon2DRoomByLocation=null;
		for(SimplePolygon2D simplePolygon2DRoomSortedArea:simplePolygon2DRoomsSortedArea){
			if(simplePolygon2DRoomSortedArea.contains(longitude,latitude)){
				simplePolygon2DRoomByLocation=simplePolygon2DRoomSortedArea;
				break;
			}
		}
		return simplePolygon2DRoomByLocation;
	}



}
