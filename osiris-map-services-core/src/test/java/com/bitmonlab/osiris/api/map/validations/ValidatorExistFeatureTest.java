package com.bitmonlab.osiris.api.map.validations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bitmonlab.osiris.api.map.dao.api.FeatureRepository;
import com.bitmonlab.osiris.api.map.exceptions.FeatureNotExistException;
import com.bitmonlab.osiris.api.map.validations.ValidatorExistFeature;
import com.bitmonlab.commons.api.map.model.geojson.Feature;

@RunWith(MockitoJUnitRunner.class)
public class ValidatorExistFeatureTest {
	
	@InjectMocks
	private ValidatorExistFeature validatorExistFeature;
	
	@Mock
	private FeatureRepository featureRepository;
	
	@Mock
	private Feature feature;
	
	@Test
	public void checkFeatureExist() throws FeatureNotExistException{	
		
		 String idApplication = "9";  
		 String idFeature = "1";
		
		//Fixture	
		Mockito.when(featureRepository.findByApplicationIdentifierAndId(idApplication, idFeature)).thenReturn(feature);
			
		//Experimentation
		validatorExistFeature.checkFeatureExist(idApplication, idFeature);	
		
		//Expectations
		Mockito.verify(featureRepository).findByApplicationIdentifierAndId(idApplication, idFeature);
	}
	
	@Test(expected=FeatureNotExistException.class)
	public void checkFeatureNotExist() throws FeatureNotExistException{	
		
		 String idApplication = "9";  
		 String idFeature = "1";
		
		//Fixture	
		Mockito.when(featureRepository.findByApplicationIdentifierAndId(idApplication, idFeature)).thenReturn(null);
			
		//Experimentation
		validatorExistFeature.checkFeatureExist(idApplication, idFeature);	
		
		//Expectations
		Mockito.verify(featureRepository).findByApplicationIdentifierAndId(idApplication, idFeature);
	}  

}
