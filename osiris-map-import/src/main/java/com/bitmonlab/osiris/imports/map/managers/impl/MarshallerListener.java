package com.bitmonlab.osiris.imports.map.managers.impl;

import javax.xml.bind.Marshaller;

import com.bitmonlab.osiris.imports.map.model.osm.Bounds;
import com.bitmonlab.osiris.imports.map.model.osm.Node;

public class MarshallerListener extends Marshaller.Listener {
	
	private Bounds boundsMap = new Bounds();
	
	public void beforeMarshal(Object source){
		
		if(source instanceof Node){
			
			 Node node = (Node)source;
						
			 if(Double.valueOf(boundsMap.getMaxlat()) < Double.valueOf(node.getLat())){				 
				 boundsMap.setMaxlat(node.getLat());				 
			 }
			 if(Double.valueOf(boundsMap.getMaxlon()) < Double.valueOf(node.getLon())){				 
				 boundsMap.setMaxlon(node.getLon());				 
			 }
			 if(Double.valueOf(boundsMap.getMinlat()) > Double.valueOf(node.getLat())){				 
				 boundsMap.setMinlat(node.getLat());				 
			 }
			 if(Double.valueOf(boundsMap.getMinlon()) > Double.valueOf(node.getLon())){				 
				 boundsMap.setMinlon(node.getLon());				 
			 }
			 			 			 								
		}
													
	}
	

	public Bounds getBoundsMap() {
		return boundsMap;
	}
	
	
	
	
	
}
