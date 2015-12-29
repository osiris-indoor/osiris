package com.bitmonlab.osiris.api.map.validations;

import javax.inject.Inject;
import javax.inject.Named;

import com.bitmonlab.osiris.api.map.dao.api.FeatureRepository;
import com.bitmonlab.osiris.api.map.exceptions.FeatureNotExistException;
import com.bitmonlab.commons.api.map.model.geojson.Feature;

@Named
public class ValidatorExistFeature {
	
	@Inject
	private FeatureRepository featureRepository;
	
	public void checkFeatureExist(String idApplication, String idFeature) throws FeatureNotExistException{
			
		Feature feature = featureRepository.findByApplicationIdentifierAndId(idApplication, idFeature);
		
		if (feature==null){
			throw new FeatureNotExistException();
		}
				
	}

}
