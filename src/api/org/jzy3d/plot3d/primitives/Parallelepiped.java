package org.jzy3d.plot3d.primitives;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.IMultiColorable;
import org.jzy3d.colors.ISingleColorable;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.AbstractWireframeable;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.rendering.view.Camera;
import org.jzy3d.plot3d.transform.Transform;




/** 
 * A {@link Parallelepiped} is a parallelepiped rectangle that is Drawable 
 * and Wireframeable.
 * 
 * This class has been implemented for debugging purpose and inconsistency
 * of its input w.r.t other primitives should not be considered
 * (no setData function).
 */
public class Parallelepiped extends AbstractWireframeable implements ISingleColorable, IMultiColorable{
	
	/** Initialize a parallelepiped.*/
	public Parallelepiped(){
		super();
		bbox = new BoundingBox3d();		
	}
	
	/** Initialize a parallelepiped.*/
	public Parallelepiped(BoundingBox3d b){
		super();
		bbox = new BoundingBox3d();
		setData(b);	
	}
	
	/**********************************************************************/

	public void draw(GL gl, GLU glu, Camera cam){
		for(Polygon quad: quads)
			quad.draw(gl, glu, cam);	
	}
	
	public void setTransform(Transform transform){
		this.transform = transform;
		for(Polygon quad: quads)
			quad.setTransform(transform);
	}
		
	/**********************************************************************/

	public void setWireframeColor(Color color){
		for(Polygon quad: quads)
			quad.setWireframeColor(color);
	}
	
	public void setWireframeDisplayed(boolean status){
		for(Polygon quad: quads)
			quad.setWireframeDisplayed(status);
	}
	
	public void setWireframeWidth(float width){
		for(Polygon quad: quads)
			quad.setWireframeWidth(width);
	}
	
	public void setFaceDisplayed(boolean status){
		for(Polygon quad: quads)
			quad.setFaceDisplayed(status);
	}
	
	public Color getWireframeColor(){
		return quads[0].getWireframeColor();
	}
	
	public boolean getWireframeDisplayed(){
		return quads[0].getWireframeDisplayed();
	}
	
	public float getWireframeWidth(){
		return quads[0].getWireframeWidth();
	}

	public boolean getFaceDisplayed(){
		return quads[0].getFaceDisplayed();
	}
	
	/**********************************************************************/

	/** Set the parallelepiped data.*/
	public void setData(BoundingBox3d box){
		bbox.reset();
		bbox.add(box);
		
		quads = new Polygon[6];
		
		quads[0] = new Polygon();
		quads[0].add(new Point(new Coord3d(bbox.getXmax(), bbox.getYmin(), bbox.getZmax()))); 
		quads[0].add(new Point(new Coord3d(bbox.getXmax(), bbox.getYmin(), bbox.getZmin()))); 
		quads[0].add(new Point(new Coord3d(bbox.getXmax(), bbox.getYmax(), bbox.getZmin()))); 
		quads[0].add(new Point(new Coord3d(bbox.getXmax(), bbox.getYmax(), bbox.getZmax()))); 
		
		quads[1] = new Polygon();
		quads[1].add(new Point(new Coord3d(bbox.getXmin(), bbox.getYmax(), bbox.getZmax()))); 
		quads[1].add(new Point(new Coord3d(bbox.getXmin(), bbox.getYmax(), bbox.getZmin()))); 
		quads[1].add(new Point(new Coord3d(bbox.getXmin(), bbox.getYmin(), bbox.getZmin()))); 
		quads[1].add(new Point(new Coord3d(bbox.getXmin(), bbox.getYmin(), bbox.getZmax()))); 

		quads[2] = new Polygon();
		quads[2].add(new Point(new Coord3d(bbox.getXmax(), bbox.getYmax(), bbox.getZmax()))); 
		quads[2].add(new Point(new Coord3d(bbox.getXmax(), bbox.getYmax(), bbox.getZmin()))); 
		quads[2].add(new Point(new Coord3d(bbox.getXmin(), bbox.getYmax(), bbox.getZmin()))); 
		quads[2].add(new Point(new Coord3d(bbox.getXmin(), bbox.getYmax(), bbox.getZmax()))); 

		quads[3] = new Polygon();
		quads[3].add(new Point(new Coord3d(bbox.getXmin(), bbox.getYmin(), bbox.getZmax()))); 
		quads[3].add(new Point(new Coord3d(bbox.getXmin(), bbox.getYmin(), bbox.getZmin()))); 
		quads[3].add(new Point(new Coord3d(bbox.getXmax(), bbox.getYmin(), bbox.getZmin()))); 
		quads[3].add(new Point(new Coord3d(bbox.getXmax(), bbox.getYmin(), bbox.getZmax()))); 

		quads[4] = new Polygon();
		quads[4].add(new Point(new Coord3d(bbox.getXmin(), bbox.getYmin(), bbox.getZmax()))); 
		quads[4].add(new Point(new Coord3d(bbox.getXmax(), bbox.getYmin(), bbox.getZmax()))); 
		quads[4].add(new Point(new Coord3d(bbox.getXmax(), bbox.getYmax(), bbox.getZmax()))); 
		quads[4].add(new Point(new Coord3d(bbox.getXmin(), bbox.getYmax(), bbox.getZmax()))); 

		quads[5] = new Polygon();
		quads[5].add(new Point(new Coord3d(bbox.getXmax(), bbox.getYmin(), bbox.getZmin()))); 
		quads[5].add(new Point(new Coord3d(bbox.getXmin(), bbox.getYmin(), bbox.getZmin()))); 
		quads[5].add(new Point(new Coord3d(bbox.getXmin(), bbox.getYmax(), bbox.getZmin()))); 
		quads[5].add(new Point(new Coord3d(bbox.getXmax(), bbox.getYmax(), bbox.getZmin()))); 
	}
	
	/**********************************************************************/

	public void setColorMapper(ColorMapper mapper){
		this.mapper = mapper;
		
		for(Polygon quad:quads){
			quad.setColorMapper(mapper);
		}
	}
	
	public ColorMapper getColorMapper(){
		return mapper;
	}
	
	/*public void setColors(ColorMapper mapper){
		for(Polygon quad:quads){
			quad.setColors(mapper);
		}
	}*/

	public void setColor(Color color){
		this.color = color;
		
		for(Polygon quad: quads)
			quad.setColor(color);
	}
	
	public Color getColor(){
		return color;
	}
		
	
	/**********************************************************************/

	private ColorMapper mapper;
	private Polygon quads[];
	private Color color;
}
