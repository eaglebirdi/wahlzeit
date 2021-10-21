package org.wahlzeit.model;

/**
 * A coordinate represents the coordinates of a position.
 */
public class Coordinate {
	protected double x;
	protected double y;
	protected double z;
	
	public Coordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * 
	 * @methodtype get
	 */
	public double getZ() {
		return this.z;
	}
}
