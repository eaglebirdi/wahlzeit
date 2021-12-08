package org.wahlzeit.model;

import java.sql.*;
import java.util.*;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.MathUtil;

/**
 * A cartesian coordinate represents a position which is defined by the three values x, y and z in an orthogonal coordinate system.
 */
public class CartesianCoordinate extends AbstractCoordinate {
	protected double x;
	protected double y;
	protected double z;

	public CartesianCoordinate(double x, double y, double z) throws InvalidCoordinateException {
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
	protected void assertClassInvariants() throws InvalidCoordinateException {
		String validationErrorMessage = getValidationErrorMessage(this.x, this.y, this.z);
		if (validationErrorMessage != null) {
			throw new InvalidCoordinateException(validationErrorMessage);
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
	protected CartesianCoordinate doAsCartesianCoordinate() throws InvalidCoordinateException {
		CartesianCoordinate cartesian = new CartesianCoordinate(this.x, this.y, this.z);
		return cartesian;
	}

	@Override
	protected SphericCoordinate doAsSphericCoordinate() throws InvalidCoordinateException, ArithmeticException {
		final double EPSILON = 0.0001;
		CartesianCoordinate origin = new CartesianCoordinate(0, 0, 0);
		double radius = origin.getDistance(this);
		double theta, phi;
		if (Math.abs(radius) > EPSILON) {
			theta = Math.atan2(this.y, this.x);
			phi = Math.acos(this.z / radius);
		} else {
			theta = 0.0;
			phi = 0.0;
		}
		return new SphericCoordinate(radius, theta, phi);
	}

	@Override
	protected boolean doIsEqual(Coordinate other) throws InvalidCoordinateException {
		CartesianCoordinate otherCartesian = other.asCartesianCoordinate();
		return this.isEqual(otherCartesian);
	}

	private boolean isEqual(CartesianCoordinate other) {
		double diffX = MathUtil.round(this.x) - MathUtil.round(other.x);
		double diffY = MathUtil.round(this.y) - MathUtil.round(other.y);
		double diffZ = MathUtil.round(this.z) - MathUtil.round(other.z);
		return diffX == 0 && diffY == 0 && diffZ == 0;
	}

	/**
	 * 
	 */
	public void writeOn(ResultSet rset) throws SQLException, InvalidCoordinateException {
		this.assertClassInvariants();
		if (rset == null) {
			throw new IllegalArgumentException("rset must not be null.");
		}

		rset.updateDouble("coordinate_x", this.x);
		rset.updateDouble("coordinate_y", this.y);
		rset.updateDouble("coordinate_z", this.z);

		this.assertClassInvariants();
	}

	public double getDistance(CartesianCoordinate other) throws InvalidCoordinateException {
		this.assertClassInvariants();
		this.assertArgumentIsNotNull(other);

		double diffX = this.x - other.x;
		double diffY = this.y - other.y;
		double diffZ = this.z - other.z;
		double sum = diffX * diffX + diffY * diffY + diffZ * diffZ;
		double sqrt;

		try {
			sqrt = Math.sqrt(sum);
		} catch (ArithmeticException ex) {
			throw new InvalidCoordinateException(ex);
		}

		this.assertResultIsNotNaN(sqrt);
		this.assertResultIsNotNegative(sqrt);
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
		
		// equals should not throw an exception
		try {
			return this.isEqual(other);
		} catch (InvalidCoordinateException ex) {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(MathUtil.round(this.x), MathUtil.round(this.y),MathUtil.round(this.z));
	}
}