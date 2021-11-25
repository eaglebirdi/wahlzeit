package org.wahlzeit.model;

import java.sql.*;
import java.util.*;

import org.wahlzeit.services.*;

/**
 * A cartesian coordinate represents a position which is defined by the three values x, y and z in an orthogonal coordinate system.
 */
public class CartesianCoordinate extends AbstractCoordinate {
	private static final CartesianCoordinate ORIGIN = new CartesianCoordinate(0, 0, 0);

	protected double x;
	protected double y;
	protected double z;

	public CartesianCoordinate(double x, double y, double z) {
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

	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		return new CartesianCoordinate(this.x, this.y, this.z);
	}

	@Override
	public SphericCoordinate asSphericCoordinate() throws ArithmeticException {
		double radius = ORIGIN.getDistance(this);
		double theta = Math.atan2(this.y, this.x);
		double phi = Math.acos(this.z / radius);
		return new SphericCoordinate(radius, theta, phi);
	}

	@Override
	public boolean isEqual(Coordinate other) {
		CartesianCoordinate otherCartesian = other.asCartesianCoordinate();
		return this.isEqual(otherCartesian);
	}

	private boolean isEqual(CartesianCoordinate other) {
		final double EPSILON = 0.0001;
		double diffX = Math.abs(this.x - other.x);
		double diffY = Math.abs(this.y - other.y);
		double diffZ = Math.abs(this.z - other.z);

		return diffX < EPSILON && diffY < EPSILON && diffZ < EPSILON;
	}

	/**
	 * 
	 */
	public void writeOn(ResultSet rset) throws SQLException {
		rset.updateDouble("coordinate_x", this.x);
		rset.updateDouble("coordinate_y", this.y);
		rset.updateDouble("coordinate_z", this.z);
	}

	public double getDistance(CartesianCoordinate other) throws ArithmeticException {
		double diffX = this.x - other.x;
		double diffY = this.y - other.y;
		double diffZ = this.z - other.z;
		double sum = diffX * diffX + diffY * diffY + diffZ * diffZ;
		double sqrt = Math.sqrt(sum);
		return sqrt;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof Coordinate)) { 
			return false;
		}

		Coordinate other = (Coordinate) obj;
		return this.isEqual(other);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.x, this.y, this.z);
	}
}