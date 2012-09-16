package org.jzy3d.plot3d.primitives;

import java.util.ArrayList;
import java.util.List;

import org.jzy3d.maths.Coord3d;


public class FlatLine2d extends AbstractComposite{
	public FlatLine2d(){
	}
	
	public FlatLine2d(float[] x, float[] y, float depth){
		setData(x, y, 0, depth);
	}
	
	public FlatLine2d(float[] x, float[] y, float zmin, float zmax){
		setData(x, y, zmin, zmax);
	}
	
	public void setData(float[] x, float[] y, float zmin, float zmax){
		if(x.length!=y.length)
			throw new IllegalArgumentException("x and y must have equal length");
		List<Quad> quads = new ArrayList<Quad>(x.length*y.length);
		for(int i=0; i<x.length-1; i++)
			quads.add( getLineElement(x[i], x[i+1], y[i], y[i+1], zmin, zmax) );
		add(quads);
	}
	
	protected Quad getLineElement(float x1, float x2, float y1, float y2, float depth){
		return getLineElement(x1, x2, y1, y2, 0, depth);
	}
	
	protected Quad getLineElement(float x1, float x2, float y1, float y2, float z1, float z2){
		Quad q = new Quad();
		q.add( new Point( new Coord3d( z1, x1, y1 ) ) );
		q.add( new Point( new Coord3d( z1, x2, y2 ) ) );
		q.add( new Point( new Coord3d( z2, x2, y2 ) ) );
		q.add( new Point( new Coord3d( z2, x1, y1 ) ) );
		return q;
	}
}
