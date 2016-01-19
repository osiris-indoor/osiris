package com.bitmonlab.batch.imports.map.model.osm;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.bitmonlab.batch.imports.map.model.osm.Node;
import com.bitmonlab.batch.imports.map.model.osm.Tag;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({Node.class})
public class NodeTest {
	
	
	@InjectMocks
	Node node;
	
	
	@Test
	public void existTag_true(){
		
		String eTag = "tag";
		
		Tag tag = new Tag();
		tag.setK(eTag);		
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(tag);		
		node.setTags(tags);
		
		//Fixture
		
		
		//Experimentation
		boolean ret = node.existTag(eTag);
		
		
		//Expectation
		Assert.assertTrue("Exist tag must be true", ret);
		
	}
	
	@Test
	public void existTag_false(){
		
		String eTag = "tag";			
		
		Tag tag = new Tag();
		tag.setK("otherTag");		
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(tag);
		node.setTags(tags);
		
		//Fixture
		
		
		//Experimentation
		boolean ret = node.existTag(eTag);
		
		
		//Expectation
		Assert.assertTrue("Exist tag must be false", !ret);
		
	}
	
	
	
}
