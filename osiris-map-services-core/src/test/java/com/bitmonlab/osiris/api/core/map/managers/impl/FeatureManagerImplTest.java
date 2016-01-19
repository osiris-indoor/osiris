package com.bitmonlab.osiris.api.core.map.managers.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bitmonlab.osiris.api.core.map.dao.api.FeatureRepository;
import com.bitmonlab.osiris.api.core.map.exceptions.FeatureNotExistException;
import com.bitmonlab.osiris.api.core.map.exceptions.MongoGeospatialException;
import com.bitmonlab.osiris.api.core.map.managers.impl.FeatureManagerImpl;
import com.bitmonlab.osiris.api.core.map.validations.ValidatorExistFeature;
import com.bitmonlab.osiris.commons.map.model.geojson.Feature;

@RunWith(MockitoJUnitRunner.class)
public class FeatureManagerImplTest {

	@InjectMocks
	private FeatureManagerImpl featureManagerImpl;
	
	@Mock
	private Feature feature;
	
	@Mock 
	private FeatureRepository featureRepository;
	
	@Mock
	private ValidatorExistFeature validatorExistFeature;
		
	@Test
	public void storeFeatureManagerTest() throws MongoGeospatialException{
		 
		 String idApplication = "1";  
		 
		//Fixture		
							
		//Experimentation
		featureManagerImpl.storeFeature(idApplication, feature);
				
		//Expectations			
		Mockito.verify(featureRepository).save(idApplication,feature);
				
								
	 }
	
	@Test
	public void deleteFeatureManagerTest() throws FeatureNotExistException{
		 
		 String idApplication = "9";  
		 String idFeature = "1";
		 
		//Fixture		
			
							
		//Experimentation
		featureManagerImpl.deleteFeature(idApplication, idFeature);
				
		//Expectations		
		Mockito.verify(validatorExistFeature).checkFeatureExist(idApplication,idFeature);		
		Mockito.verify(featureRepository).delete(idApplication,idFeature);
								
	 }
	
	@Test
	public void updateFeatureManagerTest() throws FeatureNotExistException, MongoGeospatialException{
		
		 String idApplication = "9";  
		 String idFeature = "1";
		
		//Fixture
		 
		
		//Experimentation
		 featureManagerImpl.updateFeature(idApplication, idFeature, feature);
		
		//Expectation
		Mockito.verify(validatorExistFeature).checkFeatureExist(idApplication,idFeature);
		Mockito.verify(feature).setId(idFeature);		
		Mockito.verify(featureRepository).save(idApplication,feature);
		
	}
	
	@Test
	public void getFeatureWithIDTest() throws FeatureNotExistException{
		
		 String idApplication = "9";
		 String idFeature = "1"; 
		 
		//Fixture
		
		//Experimentation
		 featureManagerImpl.getFeatureByID(idApplication, idFeature);
		
		//Expectation
		Mockito.verify(validatorExistFeature).checkFeatureExist(idApplication, idFeature);
		Mockito.verify(featureRepository).findByApplicationIdentifierAndId(idApplication,idFeature);
		
		
	}
	
}
