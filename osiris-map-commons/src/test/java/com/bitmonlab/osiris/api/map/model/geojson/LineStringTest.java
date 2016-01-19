package com.bitmonlab.osiris.api.map.model.geojson;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import math.geom2d.Point2D;
import math.geom2d.polygon.LinearRing2D;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.bitmonlab.commons.api.map.model.geojson.LineString;

//@RunWith(PowerMockRunner.class)
@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({LineString.class, LinearRing2D.class, Collection.class, ArrayList.class, Point2D.class})
public class LineStringTest {
	
	@InjectMocks
	LineString lineString;
	
	@Mock
	ArrayList<Point2D> points;
	
	@Mock
	List<Double> coord;
	
	//@Mock
	//List<List<Double>> coordinates;
	
	@Mock
	Iterator<List<Double>> iteratorCoordinates;
	
	@Mock
	LinearRing2D linearRing2D;
	
	@Mock
	BigDecimal xc;
	@Mock
	BigDecimal yc;
	@Mock
	BigDecimal tmp;
	@Mock
	BigDecimal xp;
	@Mock
	BigDecimal yp;
	@Mock
	BigDecimal x;
	@Mock
	BigDecimal y;
	@Mock
	BigDecimal xpmuly;
	@Mock
	BigDecimal ypmuly;
	@Mock
	BigDecimal area;
	@Mock
	BigDecimal prevx;
	@Mock
    BigDecimal prevy;
	@Mock
    BigDecimal pointx;
	@Mock
    BigDecimal pointy;
	@Mock
	BigDecimal subtractxy;
	@Mock
	BigDecimal denom;
	@Mock
    BigDecimal xdivide;
	@Mock
    BigDecimal ydivide;
	
	@Mock
	Point2D prev;
	@Mock
	Point2D point;
	
	
	@Mock
	ArrayList<Point2D> vertices;	
	@Mock
	Iterator<Point2D> iteratorVertices;
	
	
			
	
	
	@Test
	public void calculateCentroid_centroidPointsNONull() throws Exception{
		
		
		
		ArrayList<Double> centroid=null;
		
		int n = 4;  //vertex number
		int m = n - 1; //previous vertex
		
		double dxprev = 2.0D;
		double dyprev = 1.0D;		
		double dxpoint = 1.0D;
		double dypoint = 2.0D;
		
		//double coord0 = 8.0D;
		//double coord1 = 3.0D;
				
		BigDecimal BDtwo = new BigDecimal(2.0D);
		BigDecimal BDsix = new BigDecimal(6.0D);
		
		
		List<List<Double>> coordinates = new ArrayList<List<Double>>();	
		List<Double> list1 = new ArrayList<Double>();
		list1.add(1.0D);
		list1.add(2.0D);
		coordinates.add(list1);
		List<Double> list2 = new ArrayList<Double>();
		list2.add(2.0D);
		list2.add(2.0D);
		coordinates.add(list2);
		List<Double> list3 = new ArrayList<Double>();
		list3.add(2.0D);
		list3.add(1.0D);
		coordinates.add(list3);
		List<Double> list4 = new ArrayList<Double>();
		list4.add(1.0D);
		list4.add(1.0D);
		coordinates.add(list4);
		
		lineString.setCoordinates(coordinates);
		
		//Fixture
		PowerMockito.whenNew(ArrayList.class).withNoArguments().thenReturn(points);
		
		//loop coordinates
		//Mockito.when(coordinates.iterator()).thenReturn(iteratorCoordinates);
		//Mockito.when(iteratorCoordinates.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
		//Mockito.when(iteratorCoordinates.next()).thenReturn(coord);
		//Mockito.when(coord.get(0)).thenReturn(coord0);
		//Mockito.when(coord.get(1)).thenReturn(coord1);		
		//PowerMockito.whenNew(Point2D.class).withArguments(coord0, coord1).thenReturn(point);
		
		PowerMockito.whenNew(LinearRing2D.class).withArguments(points).thenReturn(linearRing2D);
				
		
		//* computeCentroid */
			PowerMockito.whenNew(BigDecimal.class).withArguments(0.0D).thenReturn(xc);
			PowerMockito.whenNew(BigDecimal.class).withArguments(0.0D).thenReturn(yc);
			PowerMockito.whenNew(BigDecimal.class).withArguments(0.0D).thenReturn(tmp);
			
			Mockito.when(linearRing2D.vertexNumber()).thenReturn(n);
			Mockito.when(linearRing2D.vertex(m)).thenReturn(prev);
			
			Mockito.when(prev.x()).thenReturn(dxprev);
			Mockito.when(prev.y()).thenReturn(dyprev);
			PowerMockito.whenNew(BigDecimal.class).withArguments(dxprev).thenReturn(xp);
			PowerMockito.whenNew(BigDecimal.class).withArguments(dyprev).thenReturn(yp);
		
			//* loop vertex
			Mockito.when(vertices.iterator()).thenReturn(iteratorVertices);
			Mockito.when(iteratorVertices.hasNext()).thenReturn(true).thenReturn(false);
			Mockito.when(iteratorVertices.next()).thenReturn(point);
			Mockito.when(point.x()).thenReturn(dxpoint);
			Mockito.when(point.y()).thenReturn(dypoint);
			PowerMockito.whenNew(BigDecimal.class).withArguments(dxpoint).thenReturn(x);
			PowerMockito.whenNew(BigDecimal.class).withArguments(dypoint).thenReturn(y);
			Mockito.when(xp.multiply(x)).thenReturn(xpmuly);
			Mockito.when(yp.multiply(y)).thenReturn(ypmuly);
			
				//* compute area */
				PowerMockito.whenNew(BigDecimal.class).withArguments(0.0D).thenReturn(area);
				Mockito.when(linearRing2D.vertexNumber()).thenReturn(n);
				Mockito.when(linearRing2D.vertex(m)).thenReturn(prev);	
				//loop vertex
				Mockito.when(vertices.iterator()).thenReturn(iteratorVertices);
				Mockito.when(iteratorVertices.hasNext()).thenReturn(true).thenReturn(false);
				Mockito.when(iteratorVertices.next()).thenReturn(point);
				
				PowerMockito.whenNew(BigDecimal.class).withArguments(dxprev).thenReturn(prevx);
				PowerMockito.whenNew(BigDecimal.class).withArguments(dyprev).thenReturn(prevy);
				PowerMockito.whenNew(BigDecimal.class).withArguments(dxpoint).thenReturn(pointx);
				PowerMockito.whenNew(BigDecimal.class).withArguments(dypoint).thenReturn(pointy);
								
				Mockito.when(prevx.multiply(pointy)).thenReturn(xpmuly);
				Mockito.when(prevy.multiply(pointx)).thenReturn(ypmuly);
				Mockito.when(xpmuly.subtract(ypmuly)).thenReturn(subtractxy);
				Mockito.when(area.add(subtractxy)).thenReturn(area);
				
	 			 PowerMockito.whenNew(BigDecimal.class).withArguments(dxprev).thenReturn(prevx);	
				 Mockito.when(area.divide(BDtwo, 4096, RoundingMode.HALF_UP)).thenReturn(denom);
				 
				Mockito.when(denom.multiply(BDsix)).thenReturn(denom); 
				 
			//if area in computeCentroid
			Mockito.when(denom.compareTo(BigDecimal.ZERO)).thenReturn(1);
			PowerMockito.whenNew(ArrayList.class).withNoArguments().thenReturn(centroid);
			Mockito.when(xc.divide(denom, 32, RoundingMode.HALF_UP)).thenReturn(xdivide);
			Mockito.when(yc.divide(denom, 32, RoundingMode.HALF_UP)).thenReturn(ydivide);
						
		
			//if centroidPoint null?
			
		
		//Experimentation
		lineString.calculateCentroid();
		
		
		//Expectation
		Assert.assertTrue("Centroids points must be 1.5,1.5", lineString.getCentroid().get(0).equals(1.5D) && lineString.getCentroid().get(1).equals(1.5D));
		
	}
	
	
	@Test
	public void calculateCentroid_centroidPointsNull() throws Exception{
		
		
		
		int n = 4;  //vertex number
		int m = n - 1; //previous vertex
		
		double dxprev = 2.0D;
		double dyprev = 1.0D;		
		double dxpoint = 1.0D;
		double dypoint = 2.0D;
								
		BigDecimal BDtwo = new BigDecimal(2.0D);
		BigDecimal BDsix = new BigDecimal(6.0D);
		
		
		List<List<Double>> coordinates = new ArrayList<List<Double>>();	
		List<Double> list1 = new ArrayList<Double>();
		list1.add(1.0D);
		list1.add(1.0D);
		coordinates.add(list1);
		
		
		lineString.setCoordinates(coordinates);
		
		//Fixture
		PowerMockito.whenNew(ArrayList.class).withNoArguments().thenReturn(points);
	
		PowerMockito.whenNew(LinearRing2D.class).withArguments(points).thenReturn(linearRing2D);
				
		
		//* computeCentroid */
			PowerMockito.whenNew(BigDecimal.class).withArguments(0.0D).thenReturn(xc);
			PowerMockito.whenNew(BigDecimal.class).withArguments(0.0D).thenReturn(yc);
			PowerMockito.whenNew(BigDecimal.class).withArguments(0.0D).thenReturn(tmp);
			
			Mockito.when(linearRing2D.vertexNumber()).thenReturn(n);
			Mockito.when(linearRing2D.vertex(m)).thenReturn(prev);
			
			Mockito.when(prev.x()).thenReturn(dxprev);
			Mockito.when(prev.y()).thenReturn(dyprev);
			PowerMockito.whenNew(BigDecimal.class).withArguments(dxprev).thenReturn(xp);
			PowerMockito.whenNew(BigDecimal.class).withArguments(dyprev).thenReturn(yp);
		
			//* loop vertex
			Mockito.when(vertices.iterator()).thenReturn(iteratorVertices);
			Mockito.when(iteratorVertices.hasNext()).thenReturn(true).thenReturn(false);
			Mockito.when(iteratorVertices.next()).thenReturn(point);
			Mockito.when(point.x()).thenReturn(dxpoint);
			Mockito.when(point.y()).thenReturn(dypoint);
			PowerMockito.whenNew(BigDecimal.class).withArguments(dxpoint).thenReturn(x);
			PowerMockito.whenNew(BigDecimal.class).withArguments(dypoint).thenReturn(y);
			Mockito.when(xp.multiply(x)).thenReturn(xpmuly);
			Mockito.when(yp.multiply(y)).thenReturn(ypmuly);
			
				//* compute area */
				PowerMockito.whenNew(BigDecimal.class).withArguments(0.0D).thenReturn(area);
				Mockito.when(linearRing2D.vertexNumber()).thenReturn(n);
				Mockito.when(linearRing2D.vertex(m)).thenReturn(prev);	
				//loop vertex
				Mockito.when(vertices.iterator()).thenReturn(iteratorVertices);
				Mockito.when(iteratorVertices.hasNext()).thenReturn(true).thenReturn(false);
				Mockito.when(iteratorVertices.next()).thenReturn(point);
				
				PowerMockito.whenNew(BigDecimal.class).withArguments(dxprev).thenReturn(prevx);
				PowerMockito.whenNew(BigDecimal.class).withArguments(dyprev).thenReturn(prevy);
				PowerMockito.whenNew(BigDecimal.class).withArguments(dxpoint).thenReturn(pointx);
				PowerMockito.whenNew(BigDecimal.class).withArguments(dypoint).thenReturn(pointy);
								
				Mockito.when(prevx.multiply(pointy)).thenReturn(xpmuly);
				Mockito.when(prevy.multiply(pointx)).thenReturn(ypmuly);
				Mockito.when(xpmuly.subtract(ypmuly)).thenReturn(subtractxy);
				Mockito.when(area.add(subtractxy)).thenReturn(area);
				
	 			 PowerMockito.whenNew(BigDecimal.class).withArguments(dxprev).thenReturn(prevx);	
				 Mockito.when(area.divide(BDtwo, 4096, RoundingMode.HALF_UP)).thenReturn(denom);
				 
				Mockito.when(denom.multiply(BDsix)).thenReturn(denom); 
				 
			//if area in computeCentroid
			Mockito.when(denom.compareTo(BigDecimal.ZERO)).thenReturn(1);
						
		
			
		
		//Experimentation
		lineString.calculateCentroid();
		
		
		//Expectation
		Assert.assertTrue("Centroids points must be null", lineString.getCentroid() == null );
		
	}
	
	
	
}
