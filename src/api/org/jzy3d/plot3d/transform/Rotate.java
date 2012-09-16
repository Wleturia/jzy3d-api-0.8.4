package org.jzy3d.plot3d.transform;

import javax.media.opengl.GL;

import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.pipelines.NotImplementedException;


/**
 * Rotate is a {@link Transformer} that stores the angle and rotate values 
 * required to perform the effective OpenGL rotation in the 
 * ModelView Matrix.
 * @author Martin Pernollet
 */
public class Rotate implements Transformer {

	/**
	 * Initialize a Rotation.
	 * @param angle
	 * @param rotate
	 */
	public Rotate(float angle, Coord3d rotate){
		this.angle = angle;
		this.rotate = rotate;
	}
	
	public Rotate(double angle, Coord3d rotate){
		this.angle = (float)angle;
		this.rotate = rotate;
	}
	
	public void execute(GL gl){
		gl.glRotatef(angle, rotate.x, rotate.y, rotate.z);
	}
	
	public Coord3d compute(Coord3d input) {
		throw new NotImplementedException();
	}	
	
	public String toString(){
		return "(Rotate)a=" + angle + " " + rotate;
	}
	
	/**************************************************/
	
	private float angle;
	private Coord3d rotate;
}
