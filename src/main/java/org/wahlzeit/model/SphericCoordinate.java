package org.wahlzeit.model;

import java.util.*;

import org.wahlzeit.services.*;

/**
 * A spheric coordinate represents a position which is defined by a radial distance, a polar angle and an azimuthal angle.
 */
public class SphericCoordinate extends AbstractCoordinate {
	/**
	 * Radial distance
	 */
	private double radius;

	/**
	 * Polar angle
	 */
	private double theta;

	/**
	 * Azimuthal angle
	 */
	private double phi;

	public SphericCoordinate(double radius, double theta, double phi) throws InvalidCoordinateException {
		this.assertValidArguments(radius, theta, phi);

		this.radius = radius;
		this.theta = theta;
		this.phi = phi;

		this.assertClassInvariants();
	}

	/**
	 * 
	 * @methodtype get
	 */
	public double getRadius() {
		return this.radius;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public double getTheta() {
		return this.theta;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public double getPhi() {
		return this.phi;
	}

	@Override
	protected void assertClassInvariants() throws InvalidCoordinateException {
		String validationErrorMessage = getValidationErrorMessage(this.radius, this.theta, this.phi);
		if (validationErrorMessage != null) {
			throw new InvalidCoordinateException(validationErrorMessage);
		}
	}
	
	protected void assertValidArguments(double radius, double theta, double phi) throws IllegalArgumentException {
		String validationErrorMessage = getValidationErrorMessage(radius, theta, phi);
		if (validationErrorMessage != null) {
			throw new IllegalArgumentException(validationErrorMessage);
		}
	}
	
	private String getValidationErrorMessage(double radius, double theta, double phi) {
		final double EPSILON = 0.0001;
		if (radius < 0) {
			return "radius must not be negative.";
		}
		if (Double.isNaN(radius)) {
			return "radius must not be NaN.";
		}
		if (Double.isNaN(theta)) {
			return "theta must not be NaN.";
		}
		if (Double.isNaN(phi)) {
			return "phi must not be NaN.";
		}
		if (!isAngleInRange(theta)) {
			return "theta must be between minus PI and PI.";
		}
		if (!isAngleInRange(phi)) {
			return "phi must be between minus PI and PI.";
		}
		if (Math.abs(theta) < EPSILON && Math.abs(phi) < EPSILON && radius > EPSILON) {
			return "theta and phi must not be zero at the same time given radius is not zero.";
		}
		return null;
	}

	@Override
	protected CartesianCoordinate doAsCartesianCoordinate() throws InvalidCoordinateException, ArithmeticException {
		if (this.radius == 0) {
			return new CartesianCoordinate(0, 0, 0);
		}

		double x = this.radius * Math.sin(this.phi) * Math.cos(this.theta);
		double y = this.radius * Math.sin(this.phi) * Math.sin(this.theta);
		double z = this.radius * Math.cos(this.phi);
		return new CartesianCoordinate(x, y, z);
	}

	@Override
	protected SphericCoordinate doAsSphericCoordinate() throws InvalidCoordinateException {
		return new SphericCoordinate(this.radius, this.theta, this.phi);
	}

	@Override
	protected boolean doIsEqual(Coordinate other) throws InvalidCoordinateException {
		SphericCoordinate otherSpheric = other.asSphericCoordinate();
		return this.isEqual(otherSpheric);
	}

	private boolean isEqual(SphericCoordinate other) {
		final double EPSILON = 0.0001;
		double diffRadius = Math.abs(this.radius - other.radius);
		double diffTheta = Math.abs(this.theta - other.theta);
		double diffPhi = Math.abs(this.phi - other.phi);

		return diffRadius < EPSILON && diffTheta < EPSILON && diffPhi < EPSILON;
	}

	public double getAngleTo(SphericCoordinate other) throws InvalidCoordinateException {
		this.assertClassInvariants();
		this.assertArgumentIsNotNull(other);

		double angle;
		try {
			angle = Math.acos(Math.sin(this.phi) * Math.sin(other.phi) + Math.cos(this.phi) * Math.cos(other.phi) * Math.cos(this.theta - other.theta));
		} catch (ArithmeticException ex) {
			throw new InvalidCoordinateException(ex);
		}

		this.assertResultIsNotNaN(angle);
		this.assertResultIsInAngleRange(angle);
		this.assertClassInvariants();

		return angle;
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
		return Objects.hash(this.radius, this.theta, this.phi);
	}
}