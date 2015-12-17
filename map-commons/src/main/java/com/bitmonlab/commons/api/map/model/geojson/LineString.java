package com.bitmonlab.commons.api.map.model.geojson;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import math.geom2d.Point2D;
import math.geom2d.polygon.LinearRing2D;


public class LineString extends Geometry
{  	
	private  List<List<Double>> coordinates;
	private final String type = "LineString";
	List<Double> centroid; 


	public  List<List<Double>>  getCoordinates() {
		return coordinates;
	}

	public void setCoordinates( List<List<Double>>  coordinatesList) {
		this.coordinates = coordinatesList;		
	}
	
	public List<Double> getCentroid() {
		return centroid;
	}

	public void setCentroid(List<Double> centroid) {
		this.centroid = centroid;
	}
	
		
	public void calculateCentroid(){
													
		
		/*if(!(coordinates.get(0).equals(coordinates.get(coordinates.size()-1))) ){
			return;
		}*/
		
		Collection<Point2D> points = new ArrayList<Point2D>();
				
		for(List<Double> coord : coordinates ){
			 Point2D point = new Point2D(coord.get(0), coord.get(1));	
			 points.add(point);
		}
	
		LinearRing2D linearRing2D = new LinearRing2D(points);
		List<BigDecimal> centroidPoints =  computeCentroid(linearRing2D);
			
		if(centroidPoints!=null){
			centroid = new ArrayList<Double>();		
			centroid.add(Double.valueOf(centroidPoints.get(0).toString()));
			centroid.add(Double.valueOf(centroidPoints.get(1).toString()));
		}
		
	}
	
	
	  private List<BigDecimal> computeCentroid(LinearRing2D ring)
	  {
		  		  
		 BigDecimal xc = new BigDecimal(0.0D);
		 BigDecimal yc = new BigDecimal(0.0D);

		 BigDecimal tmp = new BigDecimal(0.0D);

	    int n = ring.vertexNumber();

	    math.geom2d.Point2D prev = ring.vertex(n - 1);
	    BigDecimal xp = new BigDecimal(prev.x());
	    BigDecimal yp = new BigDecimal(prev.y());

	    for (math.geom2d.Point2D point : ring.vertices()) {
	      BigDecimal x = new BigDecimal(point.x());
	      BigDecimal y = new BigDecimal(point.y());
	      
	      tmp = xp.multiply(y).subtract(yp.multiply(x));
	      
	      xc = xc.add((x.add(xp)).multiply(tmp));
	      yc = yc.add((y.add(yp)).multiply(tmp));

	      prev = point;
	      xp = x;
	      yp = y;
	    }
	    
	    BigDecimal denom = computeArea(ring).multiply(new BigDecimal(6.0D));
	    
	    List<BigDecimal> centroid = null;
	    if (denom.compareTo(BigDecimal.ZERO) != 0){	    		    
	    	centroid = new ArrayList<BigDecimal>();
		    centroid.add(xc.divide(denom, 32, RoundingMode.HALF_UP));
		    centroid.add(yc.divide(denom, 32, RoundingMode.HALF_UP));
	    }
		    
	    return centroid;
	  }

	 
	  private BigDecimal computeArea(LinearRing2D ring)
	  {
	    BigDecimal area = new BigDecimal(0.0D);

	    int n = ring.vertexNumber();

	    math.geom2d.Point2D prev = ring.vertex(n - 1);

	    for (math.geom2d.Point2D point : ring.vertices()) {
	    	
	      BigDecimal prevx = new BigDecimal(prev.x());
	      BigDecimal prevy = new BigDecimal(prev.y());
	      BigDecimal pointx = new BigDecimal(point.x());
	      BigDecimal pointy = new BigDecimal(point.y());
	      	      	    	
	      area = area.add(prevx.multiply(pointy).subtract(prevy.multiply(pointx)));		      
	      prev = point;
	    }

	    return area.divide(new BigDecimal(2.0D), 4096, RoundingMode.HALF_UP);
	    
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
		LineString other = (LineString) obj;
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

	@Override
	public String toString() {
		return "LineString [coordinates=" + coordinates + ", type=" + type
				+ "]";
	}

	
}  




