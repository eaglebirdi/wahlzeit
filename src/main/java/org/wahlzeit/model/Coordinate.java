package org.wahlzeit.model;

import java.sql.*;

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

	/**
	 * 
	 */
	public void writeOn(ResultSet rset) throws SQLException {
		rset.updateDouble("coordinate_x", this.x);
		rset.updateDouble("coordinate_y", this.y);
		rset.updateDouble("coordinate_z", this.z);
	}

	public double getDistance(Coordinate other) {
		double diffX = this.x - other.x;
		double diffY = this.y - other.y;
		double diffZ = this.z - other.z;
		double sum = diffX * diffX + diffY * diffY + diffZ * diffZ;
		double sqrt = Math.sqrt(sum);
		return sqrt;
	}

	public boolean isEqual(Coordinate other) {
		final double EPSILON = 0.0001;
		double diffX = Math.abs(this.x - other.x);
		double diffY = Math.abs(this.y - other.y);
		double diffZ = Math.abs(this.z - other.z);
	
		return diffX < EPSILON && diffY < EPSILON && diffZ < EPSILON;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		Coordinate other = (Coordinate) obj;
		return this.isEqual(other);
	}	
}
