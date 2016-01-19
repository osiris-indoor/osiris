package com.bitmonlab.batch.imports.map.model.osm;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "osm")
public class OSM {
	
	private String version;
	
	private Bounds bounds;
	
	private List<Node> nodes;	
		
	private List<Way> ways;
	
	private List<Relation> relations;
	
    @XmlElement(name = "node")
    public List<Node> getNodes() {
        return nodes;
    }
    
    public void setNodes(List<Node> nodes){
    	this.nodes = nodes;
    }
    
    @XmlElement(name = "way")
	public List<Way> getWays() {
		return ways;
	}

	public void setWays(List<Way> ways) {
		this.ways = ways;
	}

	@XmlElement(name = "relation")
	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}	
	
	public Bounds getBounds() {
		return bounds;
	}

	@XmlElement(name = "bounds")
	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}

	public int searchNode(final Node node) {

			int pos = -1;

			if (nodes != null && node != null) {
				pos = Collections.binarySearch(nodes, node);
			}

			return pos;
	}

	public int searchWay(final Way way) {

		int pos = -1;

		if (ways != null && way != null) {
				pos = Collections.binarySearch(ways, way);
		}

		return pos;
	}

	public int searchRelation(final Relation relation) {

		int pos = -1;
		
		if (relations != null && relation != null) {
			pos = Collections.binarySearch(relations, relation);
		}
		
		return pos;
	}

	public void sortNodes() {
	
		if (nodes != null) {
			 Collections.sort(nodes);
		}
	
	}

	public void sortWays() {
	
		if (ways != null) {
			 Collections.sort(ways);
		}
	
	}

	public void sortRelations() {
	
		if (relations != null) {
			 //Collections.sort(relations,Collections.reverseOrder());
			Collections.sort(relations);
		}
		
	}

	@Override
	public String toString() {
		return "OSM [version=" + version + ", bounds=" + bounds + ", nodes="
				+ nodes + ", ways=" + ways + ", relations=" + relations + "]";
	}


	
	
}


