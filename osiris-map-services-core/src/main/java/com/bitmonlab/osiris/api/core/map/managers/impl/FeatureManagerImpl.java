package com.bitmonlab.osiris.api.core.map.managers.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.bitmonlab.osiris.api.core.map.dao.api.FeatureRepository;
import com.bitmonlab.osiris.api.core.map.exceptions.FeatureNotExistException;
import com.bitmonlab.osiris.api.core.map.exceptions.MongoGeospatialException;
import com.bitmonlab.osiris.api.core.map.managers.api.FeatureManager;
import com.bitmonlab.osiris.api.core.map.validations.ValidatorExistFeature;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;

@Named
public class FeatureManagerImpl implements FeatureManager{

	@Inject
	private FeatureRepository featureRepository;
	
	@Inject
	private ValidatorExistFeature validatorExistFeature;
	
	@Override
	public Feature storeFeature(String appId, Feature feature) throws MongoGeospatialException {
		// TODO Auto-generated method stub		
						
		return featureRepository.save(appId,feature);	
		
	}

	@Override
	public void deleteFeature(String appIdentifier, String idFeature) throws FeatureNotExistException {
		// TODO Auto-generated method stub
		
		validatorExistFeature.checkFeatureExist(appIdentifier, idFeature);
		featureRepository.delete(appIdentifier,idFeature);
		
	}

	@Override
	public Feature updateFeature(String appIdentifier, String idFeature, Feature feature) throws FeatureNotExistException, MongoGeospatialException {
		// TODO Auto-generated method stub
		
		validatorExistFeature.checkFeatureExist(appIdentifier, idFeature);
				
		feature.setId(idFeature);		
		
		return featureRepository.save(appIdentifier,feature);		
		
	}	

	@Override
	public Feature getFeatureByID(String appIdentifier, String idFeature) throws FeatureNotExistException {
		// TODO Auto-generated method stub
		
		validatorExistFeature.checkFeatureExist(appIdentifier, idFeature);

		return featureRepository.findByApplicationIdentifierAndId(appIdentifier, idFeature);
				
	}	

}
