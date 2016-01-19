package com.bitmonlab.osiris.commons.map.model.geojson;

import java.util.Collection;
import java.util.List;

public class Polygon extends Geometry {
	
	private Collection<List<List<Double>>> coordinates;
	private final String type = "Polygon";
	
		
	public Collection<List<List<Double>>> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Collection<List<List<Double>>> coordinates) {				
		this.coordinates = coordinates;
	}

	public String getType() {
		return type;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((coordinates == null) ? 0 : coordinates.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Polygon other = (Polygon) obj;
		if (coordinates == null) {
			if (other.coordinates != null)
				return false;
		} else if (!coordinates.equals(other.coordinates))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	

}
