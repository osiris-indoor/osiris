package com.bitmonlab.osiris.api.map.model.geojson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.bitmonlab.commons.api.map.model.geojson.Feature;


//@RunWith(MockitoJUnitRunner.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest({Feature.class, List.class, Map.class, Iterator.class})
public class FeatureTest {
		
	@InjectMocks
	Feature feature;
		
		
	@Mock
	Iterator<Map<String, String>> it;
		
	
	@Mock
	Map<String, String> properties;
	
	
	
	@Test
	public void setPropertiesRelations(){
		
		Map<String,String> property = new HashMap<String,String>();
		property.put("property", "value");
		List<Map<String, String>> propertiesRelations = new ArrayList<Map<String, String>>();
		propertiesRelations.add(property);
						
		//Fixture
				
		//Experimentation
		feature.setPropertiesRelations(propertiesRelations);
		
		
		//Expectations
		Assert.assertEquals("Must be the same Property", propertiesRelations.get(0), feature.getPropertiesRelations().get(0));		
		
	
		
	}
	
	@Test
	public void updatePropertiesAlreadyExists(){
					
		
		List<Map<String,String>> pR = new ArrayList<Map<String,String>>();
		Map<String,String> property1 = new HashMap<String,String>();
		property1.put("@id", "value1");
		pR.add(property1);
		feature.setPropertiesRelations(pR);
		
		
		List<Map<String,String>> newInheritedProperties  = new ArrayList<Map<String,String>>();		
		newInheritedProperties.add(property1);
				
								
		//Fixture		
				
		//Experimentation
		feature.updateProperties(newInheritedProperties);
				
				
		//Expectations		
		Assert.assertEquals("Must be the same properties relations", newInheritedProperties, feature.getPropertiesRelations());
		
	}
	
	
	@Test
	public void updatePropertiesAlreadyNoExists(){
					
		
		List<Map<String,String>> pR = new ArrayList<Map<String,String>>();
		Map<String,String> property1 = new HashMap<String,String>();
		property1.put("@id", "value1");
		pR.add(property1);
		feature.setPropertiesRelations(pR);
		
		
		List<Map<String,String>> newInheritedProperties  = new ArrayList<Map<String,String>>();
		Map<String,String> property2 = new HashMap<String,String>();
		property2.put("@id", "value2");
		newInheritedProperties.add(property2);
				
								
		//Fixture		
				
		//Experimentation
		feature.updateProperties(newInheritedProperties);
							
		//Expectations		
		Assert.assertEquals("Must be add property", newInheritedProperties.get(0), feature.getPropertiesRelations().get(1));
		
	}
	
	@Test
	public void searchProperty_and_FounditinProperties(){
		
		String property="property";
		
		//Fixture
		Mockito.when(properties.containsKey(property)).thenReturn(true);
		
		//Experimentation
		boolean ret = feature.searchProperty(property);
		
		
		
		//Expectation
		Assert.assertTrue("Return must be true", ret);
		
		
	}
	
	@Test
	public void searchProperty_and_FounditPropertiesRelations(){
		
		String property="property";
		
		List<Map<String,String>> pR = new ArrayList<Map<String,String>>();
		Map<String,String> property1 = new HashMap<String,String>();
		property1.put("property", "value");
		pR.add(property1);
		feature.setPropertiesRelations(pR);
		
		
		//Fixture
		Mockito.when(properties.containsKey(property)).thenReturn(false);
		
		//Experimentation
		boolean ret = feature.searchProperty(property);
		
				
		//Expectation
		Assert.assertTrue("Return must be true", ret);
		
		
	}
	
	@Test
	public void searchProperty_and_NoFounditPropertiesRelations(){
		
		String property="property";
		
		List<Map<String,String>> pR = new ArrayList<Map<String,String>>();
		Map<String,String> property1 = new HashMap<String,String>();
		property1.put("otherproperty", "value");
		pR.add(property1);
		feature.setPropertiesRelations(pR);
		
		
		//Fixture
		Mockito.when(properties.containsKey(property)).thenReturn(false);
		
		//Experimentation
		boolean ret = feature.searchProperty(property);
		
				
		//Expectation
		Assert.assertTrue("Return must be false", !ret);
		
		
	}
	
	
	

}
