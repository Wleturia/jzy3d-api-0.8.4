package org.jzy3d.plot3d.primitives.interactive;

import java.awt.Polygon;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.builder.concrete.SphereScatterGenerator;
import org.jzy3d.plot3d.pipelines.NotImplementedException;
import org.jzy3d.plot3d.primitives.Sphere;
import org.jzy3d.plot3d.rendering.view.Camera;


public class InteractiveSphere extends Sphere implements IInteractive{
	public InteractiveSphere(){
		this(Coord3d.ORIGIN, 10f, 15, Color.BLACK);	
	}
	
	public InteractiveSphere(Coord3d position, float radius, int slicing, Color color){
		super(position, radius, slicing, color);
		buildAnchors();
	}
	
	public void draw(GL gl, GLU glu, Camera cam){
		super.draw(gl, glu, cam);
		gl.glBegin(GL.GL_POINTS);
		gl.glColor4f(Color.RED.r, Color.RED.g, Color.RED.b, Color.RED.a);
		for(Coord3d a: anchors)	
			gl.glVertex3f(a.x, a.y, a.z);
		gl.glEnd();
	}
		
	@Override
	public void project(GL gl, GLU glu, Camera cam) {
		projection = cam.modelToScreen(gl, glu, anchors);
	}
	
	public List<Coord3d> getProjection() {
		return projection;
	}
	
	public void setPosition(Coord3d position){
		super.setPosition(position);
		buildAnchors();
	}
	
	public void setVolume(float radius){
		super.setVolume(radius);
		buildAnchors();
	}
	
	protected void buildAnchors(){
		anchors = buildAnchors(position, radius);
	}
	
	protected List<Coord3d> buildAnchors(Coord3d position, float radius){
		return SphereScatterGenerator.generate(position,radius,PRECISION,false);
	}
	
	@Override
	public Polygon getHull2d() {
		throw new NotImplementedException();
	}

	@Override
	public List<Coord3d> getLastProjection() {
		throw new NotImplementedException();
	}
	
	/*********************************************/
	
	public void setHighlighted(boolean value){
		isHighlighted = value;
	}
	
	public boolean isHighlighted(){
		return isHighlighted;
	}
	
	public void resetHighlighting(){
		this.isHighlighted = false;
	}
	
	protected List<Coord3d> anchors;
	protected int PRECISION = 10;
	protected boolean isHighlighted = false;
	
	protected List<Coord3d> projection;
}
