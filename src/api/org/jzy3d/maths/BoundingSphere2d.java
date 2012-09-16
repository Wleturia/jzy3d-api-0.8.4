package org.jzy3d.maths;

public class BoundingSphere2d {
	public BoundingSphere2d() {
		this(new Coord2d(),0);
	}
	public BoundingSphere2d(Coord2d coord, float radius) {
		this.coord = coord;
		this.radius = radius;
	}
	public Coord2d getCoord() {
		return coord;
	}
	public void setCoord(Coord2d coord) {
		this.coord = coord;
	}
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	protected Coord2d coord;
	protected float radius;
}
