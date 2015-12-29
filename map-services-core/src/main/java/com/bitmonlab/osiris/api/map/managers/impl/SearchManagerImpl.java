package com.bitmonlab.osiris.api.map.managers.impl;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import com.bitmonlab.osiris.api.map.dao.api.FeatureRepository;
import com.bitmonlab.osiris.api.map.dao.api.MapRepository;
import com.bitmonlab.osiris.api.map.exceptions.QueryException;
import com.bitmonlab.osiris.api.map.managers.api.SearchManager;
import com.bitmonlab.osiris.api.map.transferobject.LayerDTO;
import com.bitmonlab.commons.api.map.model.geojson.Feature;

@Named
public class SearchManagerImpl implements SearchManager{
	
	@Inject
	private FeatureRepository featureRepository;
	
	@Inject
	private MapRepository mapRepository;
	
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


}
