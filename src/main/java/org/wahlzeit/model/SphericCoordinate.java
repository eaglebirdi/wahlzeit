package org.wahlzeit.model;

import java.util.*;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.MathUtil;

/**
 * A spheric coordinate represents a position which is defined by a radial distance, a polar angle and an azimuthal angle.
 */
@PatternInstance(
	patternName = "Value Object",
	participants = { "ValueObject" }
)
public class SphericCoordinate extends AbstractCoordinate {
	protected static final ValueObjectRepository<SphericCoordinate> repository = new ValueObjectRepository<SphericCoordinate>();

	/**
	 * Radial distance
	 */
	protected final double radius;

	/**
	 * Polar angle
	 */
	protected final double theta;

	/**
	 * Azimuthal angle
	 */
	protected final double phi;

	private SphericCoordinate(double radius, double theta, double phi) throws InvalidCoordinateException {
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

	public static SphericCoordinate create(double radius, double theta, double phi) throws InvalidCoordinateException {
		SphericCoordinate coordinate = new SphericCoordinate(radius, theta, phi);
		return repository.getOrPut(coordinate);
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
			return CartesianCoordinate.create(0, 0, 0);
		}

		double x = this.radius * Math.sin(this.phi) * Math.cos(this.theta);
		double y = this.radius * Math.sin(this.phi) * Math.sin(this.theta);
		double z = this.radius * Math.cos(this.phi);
		return CartesianCoordinate.create(x, y, z);
	}

	@Override
	protected SphericCoordinate doAsSphericCoordinate() throws InvalidCoordinateException {
		return this;
	}

	@Override
	protected boolean doIsEqual(Coordinate other) throws InvalidCoordinateException {
		SphericCoordinate otherSpheric = other.asSphericCoordinate();
		return this.isEqual(otherSpheric);
	}

	private boolean isEqual(SphericCoordinate other) {
		double diffRadius = MathUtil.round(this.radius) - MathUtil.round(other.radius);
		double diffTheta = MathUtil.round(this.theta) - MathUtil.round(other.theta);
		double diffPhi = MathUtil.round(this.phi) - MathUtil.round(other.phi);
		return diffRadius == 0 && diffTheta == 0 && diffPhi == 0;
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
		return Objects.hash(MathUtil.round(this.radius), MathUtil.round(this.theta), MathUtil.round(this.phi));
	}
}