package org.jzy3d.colors;

import java.util.Random;


/**
 * {@link Color} provides a representation of a color.
 * 
 * The color representation relies on a set of float components
 * with values in the range [0;1]. When using the integer constructor
 * of {@link Color}, values are expected to stand in [0;255] and are
 * scaled to be maintained in [0;1] afterward.
 * 
 * Beside being a convenient container, {@link Color} also:
 * <ul>
 * <li>can easily provide its {@link negative()}
 * <li>can easily provide its representation as a float array through {@link toArray()}
 * <li>can provide its hexadecimal string representation with {@link toHex()}
 * <li>can return an AWT equivalent with {@link awt()}, and be built with a constructor supporting an AWT color.
 * <li>can generate {@link random()} colors.
 * </ul>
 * @author Martin Pernollet
 */
public class Color {
	public static final Color BLACK   = new Color(0.0f, 0.0f, 0.0f);
	public static final Color WHITE   = new Color(1.0f, 1.0f, 1.0f);
	public static final Color GRAY    = new Color(0.5f, 0.5f, 0.5f);
	public static final Color RED     = new Color(1.0f, 0.0f, 0.0f);
	public static final Color GREEN   = new Color(0.0f, 1.0f, 0.0f);
	public static final Color BLUE    = new Color(0.0f, 0.0f, 1.0f);
	public static final Color YELLOW  = new Color(1.0f, 1.0f, 0.0f);
	public static final Color MAGENTA = new Color(1.0f, 0.0f, 1.0f);
	public static final Color CYAN    = new Color(0.0f, 1.0f, 1.0f);
	
	/*************************************************************/

	/** Initialize a color with an alpha channel set to 1.*/
	public Color(float r, float g, float b){
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1.0f;
	}
	
	/** Initialize a color with an alpha channel set to 255.*/
	public Color(int r, int g, int b){
		this.r = (float)r/255;
		this.g = (float)g/255;
		this.b = (float)b/255;
		this.a = 1.0f;
	}
	
	/** Initialize a color with values between 0 and 1.*/
	public Color(float r, float g, float b, float a){
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	/** Initialize a color with values between 0 and 255.*/
	public Color(int r, int g, int b, int a){
		this.r = (float)r/255;
		this.g = (float)g/255;
		this.b = (float)b/255;
		this.a = (float)a/255;
	}
	
	public void mul(Color factor){
		this.r *= factor.r;
		this.g *= factor.g;
		this.b *= factor.b;
		this.a *= factor.a;
	}
	
	public Color alphaSelf(float alpha){
		this.a = alpha;
		return this;
	}
	
	/** Return the hexadecimal representation of this color.*/
	public String toHex(){
		String hexa = "#";
		hexa += Integer.toHexString((int)r*255);
		hexa += Integer.toHexString((int)g*255);
		hexa += Integer.toHexString((int)b*255);
		return hexa;
	}
	
	public String toString(){
		return new String("(Color) r=" + r + " g=" + g + " b=" + b + " a=" + a);
	}
	
	public Color clone(){
		return new Color(r, g, b, a);
	}
	
	public float[] toArray(){
		float array[] = { r, g, b, a };
		return array;
	}
	
	public Color negative(){
		return new Color(1 - r, 1 - g, 1 - b);
	}
	
	public static Color random(){
		return new Color(rng.nextFloat(), rng.nextFloat(), rng.nextFloat());
	}
	
	protected static Random rng = new Random();
	
	/*************************************************************/

	public java.awt.Color awt(){
		return new java.awt.Color(r, g, b, a);
	}
	
	
	public Color(java.awt.Color c){
		this.r = c.getRed();
		this.g = c.getGreen();
		this.b = c.getBlue();
		this.a = c.getAlpha();
	}
	
	public float r;
	public float g;
	public float b;
	public float a;
	
}
