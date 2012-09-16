package org.jzy3d.global;

import javax.media.opengl.GLCapabilities;

import org.jzy3d.plot3d.rendering.canvas.ICanvas;


/** {@link Settings} is a singleton that holds general settings that configure Imaging classes instantiation.*/
public class Settings {

	private Settings(){} // disable default public constructor
	
	/** Return the single allowed instance of Settings. Multithreading is handled through
	 * the synchronized definition of getInstance().*/
	public static synchronized Settings getInstance(){
		if(instance==null){
			instance = new Settings();
			instance.setHardwareAccelerated(false);
		}
		return instance;
	}
	
	/**************************************************/

	/** Modifies the acceleration status for all {@link ICanvas.Canvas} instantiations.
	 * This doesn't modify the status of canvases that have allready been instantiated.*/
	public void setHardwareAccelerated(boolean hardwareAccelerated) {
		glCapabilities.setHardwareAccelerated(hardwareAccelerated);
	}

	/** Returns true if hardware acceleration is used for 3d graphics.*/
	public boolean isHardwareAccelerated() {
		return glCapabilities.getHardwareAccelerated();
	}

	/** Returns a copy of the current GL capabilities.*/
	public GLCapabilities getGLCapabilities() {
		return (GLCapabilities)glCapabilities.clone();
	}
	
	/**************************************************/
	
	public String toString(){
		return "HardwareAcceleration = " + isHardwareAccelerated() + "\n";
	}
	
	/**************************************************/
	
	private static Settings instance;

	// members
	private GLCapabilities glCapabilities = new GLCapabilities();
}
