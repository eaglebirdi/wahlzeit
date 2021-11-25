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

	public CartesianCoordinate(double x, double y, double z) throws IllegalStateException, IllegalArgumentException {
		this.assertValidArguments(x, y, z);

		this.x = x;
		this.y = y;
		this.z = z;

		this.assertClassInvariants();
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
	protected void assertClassInvariants() throws IllegalStateException {
		String validationErrorMessage = getValidationErrorMessage(this.x, this.y, this.z);
		if (validationErrorMessage != null) {
			throw new IllegalStateException(validationErrorMessage);
		}
	}
	
	protected void assertValidArguments(double x, double y, double z) throws IllegalArgumentException {
		String validationErrorMessage = getValidationErrorMessage(x, y, z);
		if (validationErrorMessage != null) {
			throw new IllegalArgumentException(validationErrorMessage);
		}
	}
	
	private static String getValidationErrorMessage(double x, double y, double z) {
		if (Double.isNaN(x)) {
			return "x must not be NaN.";
		}
		if (Double.isNaN(y)) {
			return "y must not be NaN.";
		}
		if (Double.isNaN(z)) {
			return "z must not be NaN.";
		}
		return null;
	}

	@Override
	protected CartesianCoordinate doAsCartesianCoordinate() {
		CartesianCoordinate cartesian = new CartesianCoordinate(this.x, this.y, this.z);
		return cartesian;
	}

	@Override
	protected SphericCoordinate doAsSphericCoordinate() throws ArithmeticException {
		double radius = ORIGIN.getDistance(this);
		double theta = Math.atan2(this.y, this.x);
		double phi = Math.acos(this.z / radius);
		return new SphericCoordinate(radius, theta, phi);
	}

	@Override
	protected boolean doIsEqual(Coordinate other) {
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
	public void writeOn(ResultSet rset) throws SQLException, IllegalStateException, IllegalArgumentException {
		this.assertClassInvariants();
		if (rset == null) {
			throw new IllegalArgumentException("rset must not be null.");
		}

		rset.updateDouble("coordinate_x", this.x);
		rset.updateDouble("coordinate_y", this.y);
		rset.updateDouble("coordinate_z", this.z);

		this.assertClassInvariants();
	}

	public double getDistance(CartesianCoordinate other) throws ArithmeticException, IllegalStateException, IllegalArgumentException {
		this.assertClassInvariants();
		this.assertArgumentIsNotNull(other);

		double diffX = this.x - other.x;
		double diffY = this.y - other.y;
		double diffZ = this.z - other.z;
		double sum = diffX * diffX + diffY * diffY + diffZ * diffZ;
		double sqrt = Math.sqrt(sum);

		this.assertArgumentIsNotNaN(sqrt);
		this.assertClassInvariants();

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