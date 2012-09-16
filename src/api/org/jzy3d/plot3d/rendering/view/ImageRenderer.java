package org.jzy3d.plot3d.rendering.view;

import java.nio.ByteBuffer;

import javax.media.opengl.GL;

public class ImageRenderer {
	public static void renderImage(GL gl, ByteBuffer image, int imageWidth, int imageHeight, int screenWidth, int screenHeight){
		renderImage(gl, image, imageWidth, imageHeight, screenWidth, screenHeight, 0.75f);
	}
	
	public static void renderImage(GL gl, ByteBuffer image, int imageWidth, int imageHeight, int screenWidth, int screenHeight, float z){
		float xratio = 1;
		float yratio = 1;
		int   xpict  = 0;
		int   ypict  = 0;
		
		if(imageWidth<screenWidth)
			xpict = (int)((float)screenWidth/2  - (float)imageWidth/2);
		else 
			xratio = ((float)screenWidth)/((float)imageWidth);
			
		if(imageHeight<screenHeight) 
			ypict = (int)((float)screenHeight/2 - (float)imageHeight/2);
		else 
			yratio = ((float)screenHeight)/((float)imageHeight);
		
		// Draw
		gl.glPixelZoom( xratio, yratio );
		gl.glRasterPos3f (xpict, ypict, z);
		
		synchronized(image){ // we don't want to draw image while it is being set by setImage
			gl.glDrawPixels(imageWidth, imageHeight, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, image);
		}
		
		// Copy elsewhere
		//gl.glPixelZoom(1.0f, 1.0f); // x-factor, y-factor
		//gl.glWindowPos2i(500, 0);
		//gl.glCopyPixels(400, 300, 500, 600, GL.GL_COLOR);
	}
}
