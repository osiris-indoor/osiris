package com.bitmonlab.osiris.api.core.map.validations;

import javax.inject.Inject;
import javax.inject.Named;

import com.bitmonlab.osiris.api.core.map.dao.api.FeatureRepository;
import com.bitmonlab.osiris.api.core.map.exceptions.FeatureNotExistException;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;

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
