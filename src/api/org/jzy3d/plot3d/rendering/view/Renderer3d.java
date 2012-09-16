package org.jzy3d.plot3d.rendering.view;
import java.awt.image.BufferedImage;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.TraceGL;
import javax.media.opengl.glu.GLU;

import org.jzy3d.plot3d.rendering.canvas.ICanvas;
import org.jzy3d.plot3d.rendering.scene.Scene;

import com.sun.opengl.util.Screenshot;



/**
 * The Renderer object is a GLEventListener,
 * that makes openGL calls necessary to initialize and render 
 * a {@link Scene} for its parent {@link ICanvas}. 
 * A Renderer is directly embedded and controlled by a Canvas3d and 
 * should not be controlled by the user in normal uses.
 *
 * However, since the Renderer implements the GLEventListener 
 * interface, it may be incorporated into one of these 
 * GLAutoDrawable provided by JOGL:
 * <ul>
 * <li>javax.media.opengl.GLCanvas for AWT or SWT display
 * <li>javax.media.opengl.GLJPanel for Swing display
 * </ul> 
 */
public class Renderer3d implements GLEventListener{
	
	/** Initialize a Renderer attached to the given View.*/
	public Renderer3d(View view){
		this(view, false, false);
	}
	
	/** Initialize a Renderer attached to the given View, and activate GL trace and errors to console.*/
	public Renderer3d(View view, boolean traceGL, boolean debugGL){
		this.view = view;
		this.glu  = new GLU();
		this.traceGL = traceGL;
		this.debugGL = debugGL;
	}
	
	public void dispose(){
		view = null;
		glu  = null;
	}

	/***************************************************************/
	
	/** 
	 * Called when the GLDrawable is rendered for the first time.
	 * When one calls Scene.init() function, this function is called and makes 
	 * the OpenGL buffers initialization.
	 * 
	 * Note: in this implementation, GL Exceptions are not triggered.
	 * To do so, make te following call at the beginning of the init()
	 * body:
	 * <code>
	 * canvas.setGL( new DebugGL(canvas.getGL()) );
	 * </code>
	 */
	public void init(GLAutoDrawable canvas){
		view.init(canvas.getGL());
		if(debugGL) canvas.setGL( new DebugGL(canvas.getGL()) );
        if(traceGL) canvas.setGL( new TraceGL(canvas.getGL(), System.out) );
    }

	/** 
	 * Called when the GLDrawable requires a rendering. All call to
	 * rendering methods should appear here.
	 */
	public void display(GLAutoDrawable canvas){
		GL gl = canvas.getGL();
		view.clear(gl);

		// gl render
		if(view!=null)
			view.render(gl, glu); // seems that actual GL orders are executed latter

		if(doScreenshotAtNextDisplay){
			image = Screenshot.readToBufferedImage(width, height);
			doScreenshotAtNextDisplay = false;
		}
	}
	
	/** Called when the GLDrawable is resized.*/
	public void reshape(GLAutoDrawable canvas, int x, int y, int width, int height){
		this.width  = width;
		this.height = height;
		view.dimensionDirty = true;
	}
	
	/** Called when the device or display mode change.
	 * Currently throw a RuntimeException: "Renderer: displayChanged not implemented.".
	 */
	public void displayChanged(GLAutoDrawable canvas, boolean modeChanged, boolean deviceChanged){
		throw new RuntimeException("Renderer: displayChanged not implemented.");
	}
	
	
	/***************************************************************/	

	public void nextDisplayUpdateScreenshot(){
		doScreenshotAtNextDisplay = true;
	}
	
	public BufferedImage getLastScreenshot(){
		return image;
	}
	
	/***************************************************************/	
	
	/** Return the width that was given after the last resize event.*/
	public int getWidth(){
		return width;
	}
	
	/** Return the height that was given after the last resize event.*/
	public int getHeight(){
		return height;
	}
	
	/***************************************************************/	
	
	private GLU     	  glu;
	private View    	  view;
	private int     	  width     = 0; 
	private int     	  height    = 0;
	private boolean 	  doScreenshotAtNextDisplay = false;
	private BufferedImage image = null;
	private boolean 	  traceGL = false;
	private boolean 	  debugGL = false;
}
