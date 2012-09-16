package org.jzy3d.plot3d.primitives.graphs.impl;


import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import org.jzy3d.colors.Color;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord2d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.graphs.AbstractDrawableGraph2d;
import org.jzy3d.plot3d.rendering.view.Camera;
import org.jzy3d.plot3d.text.align.Halign;
import org.jzy3d.plot3d.text.align.Valign;

public class DefaultDrawableGraph2d<V,E>  extends AbstractDrawableGraph2d<V, E>  {
	public DefaultDrawableGraph2d(){
		super();		
		bbox = new BoundingBox3d();
		labelScreenOffset = new Coord2d(0,0);
		labelSceneOffset = new Coord3d(0,0,0);
	}
	
	/*******************************************************/

	protected void drawVertices(GL gl, GLU glu, Camera cam){
		gl.glPointSize(formatter.getVertexWidth());      
        gl.glBegin(GL.GL_POINTS);
		for(V v: graph.getVertices()){
			if(highlights.get(v))
				drawVertexNode(gl, glu, cam, v, layout.get(v), formatter.getHighlightedVertexColor());
			else
				drawVertexNode(gl, glu, cam, v, layout.get(v), formatter.getVertexColor());
		}
		gl.glEnd();
	}
	
	protected void drawVertexLabels(GL gl, GLU glu, Camera cam){
		for(V v: graph.getVertices()){
			if(highlights.get(v))
				drawVertexLabel(gl, glu, cam, v, layout.get(v), formatter.getHighlightedVertexColor());
			else
				drawVertexLabel(gl, glu, cam, v, layout.get(v), formatter.getVertexLabelColor());
		}
	}
	
	protected void drawEdges(GL gl, GLU glu, Camera cam){
		for(E e: graph.getEdges()){
			V v1 = graph.getEdgeStartVertex(e);
			V v2 = graph.getEdgeStopVertex(e);
			drawEdge(gl, glu, cam, e, layout.get(v1), layout.get(v2), formatter.getEdgeColor());
		}
	}
	
	/*******************************************************/
	
	protected void drawVertexNode(GL gl, GLU glu, Camera cam, V v, Coord2d coord, Color color){
		gl.glColor4f(color.r, color.g, color.b, color.a);
        gl.glVertex3f(coord.x, coord.y, Z);
	}
	
	protected void drawVertexLabel(GL gl, GLU glu, Camera cam, V v, Coord2d coord, Color color){
		Coord3d textPosition = new Coord3d(coord, Z);
        txt.drawText(gl, glu, cam, v.toString(), textPosition, Halign.CENTER, Valign.BOTTOM, color, labelScreenOffset, labelSceneOffset);
	}
	
	protected void drawEdge(GL gl, GLU glu, Camera cam, E e, Coord2d c1, Coord2d c2, Color color){
		gl.glBegin(GL.GL_LINE_STRIP);	
	        gl.glColor4f(color.r, color.g, color.b, color.a);
	        gl.glVertex3f(c1.x, c1.y, Z);
	        gl.glVertex3f(c2.x, c2.y, Z);
		gl.glEnd();
	}
}
