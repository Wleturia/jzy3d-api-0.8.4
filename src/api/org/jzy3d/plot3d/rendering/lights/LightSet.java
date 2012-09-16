package org.jzy3d.plot3d.rendering.lights;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import org.jzy3d.maths.Coord3d;


public class LightSet {	
	public LightSet(){
		this.lights = new ArrayList<Light>();
	}
	
	public LightSet(List<Light> lights){
		this.lights = lights;
	}
	
	public void init(GL gl){
		gl.glEnable(GL.GL_COLOR_MATERIAL);
	}
	
	public void apply(GL gl, Coord3d scale){
		if(lazyLightInit){
			initLight(gl);
			for(Light light: lights)
				LightSwitch.enable(gl, light.getId());
			lazyLightInit = false;
		}
		for(Light light: lights)
			light.apply(gl, scale);
	}
	
	public void enableLightIfThereAreLights(GL gl){
		enable(gl, true);
	}
	
	public void enable(GL gl, boolean onlyIfAtLeastOneLight){
		if(onlyIfAtLeastOneLight){
			if( lights.size() > 0 )
            	gl.glEnable(GL.GL_LIGHTING);
		}
		else
			gl.glEnable(GL.GL_LIGHTING);
	}
	
	public void disable(GL gl){
		gl.glDisable(GL.GL_LIGHTING);
	}
	
	public Light get(int id){
		return lights.get(id);
	}
	
	public void add(Light light){
		if(lights.size()==0)
			queryLazyLightInit();
		lights.add(light);
	}
	
	public void remove(Light light){
		lights.remove(light);
	}
	
	/***********************************/
	
	protected void queryLazyLightInit(){
		lazyLightInit = true;
	}
	
	//http://www.sjbaker.org/steve/omniv/opengl_lighting.html
	protected void initLight(GL gl){
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glEnable(GL.GL_LIGHTING);
		
		// Light model
		gl.glLightModeli(GL.GL_LIGHT_MODEL_TWO_SIDE, GL.GL_TRUE);
        //gl.glLightModeli(GL.GL_LIGHT_MODEL_LOCAL_VIEWER, GL.GL_TRUE);
		//gl.glLightModeli(GL.GL_LIGHT_MODEL_LOCAL_VIEWER, GL.GL_FALSE);
	}
		
	/***********************************/
	
	protected List<Light> lights;
	protected boolean lazyLightInit = false;
}
