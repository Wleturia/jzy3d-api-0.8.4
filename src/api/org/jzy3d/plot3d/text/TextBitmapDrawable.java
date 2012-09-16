package org.jzy3d.plot3d.text;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import org.jzy3d.colors.Color;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.rendering.view.Camera;
import org.jzy3d.plot3d.text.align.Halign;
import org.jzy3d.plot3d.text.align.Valign;

import com.sun.opengl.util.GLUT;

/**
 * EXPERIMENTAL
 * 
 * A {@link TextBitmapDrawable} is a Text renderer that may be inserted into a scene graph.
 * 
 * WARNING: THE BBOX IS ONLY AVAILABLE AFTER DRAWING!!!
 * 
 * @author Martin Pernollet
 */
public class TextBitmapDrawable extends AbstractDrawable{
	
	public TextBitmapDrawable(){	
		glut = new GLUT();
		font = GLUT.BITMAP_HELVETICA_10;
		fontHeight = 10;
	}
	
	public TextBitmapDrawable(String txt, Coord3d position, Color color){	
		this();
		setData(txt, position, color);
	}
	
	public void dispose(){
		super.dispose();
		glut = null;
	}
	
	/*******************************************************************************************/
	
	public void draw(GL gl, GLU glu, Camera cam){
		bbox = drawText(gl, glu, cam, txt, position, halign, valign, color);
	}
	
	public BoundingBox3d getBounds(){
		return bbox;//new BoundingBox3d(0,0,0,0,0,0); //;
	}
	
	public void setData(String txt, Coord3d position, Color color){
		setData(txt);
		setPosition(position);
		setColor(color);
	}
	
	public String getText(){
		return txt;
	}
	
	public void setData(String txt){
		this.txt = txt;
	}
	
	public void setPosition(Coord3d position){
		this.position = position;
		this.halign   = Halign.CENTER;
		this.valign   = Valign.CENTER;	
	}
	
	public Coord3d getPosition(){
		return this.position;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	/*******************************************************************************************/
	
	/** Draw a string at the specified position and compute the 3d volume occupied by the string 
	 * according to the current Camera configuration.*/
	private BoundingBox3d drawText(GL gl, GLU glu, Camera cam, String s, Coord3d position, Halign halign, Valign valign, Color color){
		gl.glColor3f(color.r, color.g, color.b);
		
		Coord3d posScreen = cam.modelToScreen(gl, glu, position);

		// compute a corrected position according to layout
        float  strlen = glut.glutBitmapLength(font, s);
		float  x      = 0.0f;
        float  y      = 0.0f;
        
        if(halign==Halign.RIGHT)
        	x = posScreen.x;
        else if(halign==Halign.CENTER)
			x = posScreen.x - strlen/2;
        else if(halign==Halign.LEFT)
			x = posScreen.x - strlen;
        
        if(valign==Valign.TOP)
			y = posScreen.y;
        else if(valign==Valign.GROUND)
        	y = posScreen.y;
        else if(valign==Valign.CENTER)
			y = posScreen.y - fontHeight/2;
        else if(valign==Valign.BOTTOM)
			y = posScreen.y - fontHeight;
        
        Coord3d posScreenShifted = new Coord3d(x, y, posScreen.z);
        Coord3d posReal = cam.screenToModel(gl, glu, posScreenShifted);
		
        // Draws actual string
		gl.glRasterPos3f(posReal.x, posReal.y, posReal.z);
        glut.glutBitmapString(font, s);

        
        // Compute bounds of text
		Coord3d botLeft   = new Coord3d();
		Coord3d topRight  = new Coord3d();
		
		botLeft.x  = posScreenShifted.x;
		botLeft.y  = posScreenShifted.y;
		botLeft.z  = posScreenShifted.z;		
		topRight.x = botLeft.x + strlen;
		topRight.y = botLeft.y + fontHeight;
		topRight.z = botLeft.z;		
		
		BoundingBox3d txtBounds = new BoundingBox3d();
		txtBounds.add( cam.screenToModel(gl, glu, botLeft) );
		txtBounds.add( cam.screenToModel(gl, glu, topRight) );
		
		return txtBounds;
	}
	
	public String toString(){
		return "(TextBitmapDrawable) \""+ txt +"\" at " + position.toString() + " halign=" + halign + " valign=" + valign;
	}
	
	/********************************************************************/
	
	private int  fontHeight;  
	private GLUT glut;
	private int font;
	
	private String  txt;
	private Coord3d position;
	private Halign  halign;
	private Valign  valign;	
	
	private Color   color;
}
