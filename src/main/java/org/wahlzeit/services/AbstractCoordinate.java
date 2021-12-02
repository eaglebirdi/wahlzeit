package org.wahlzeit.services;

import org.wahlzeit.model.*;

public abstract class AbstractCoordinate implements Coordinate {
	protected AbstractCoordinate() {
	}

	protected abstract void assertClassInvariants() throws IllegalStateException;

	protected abstract CartesianCoordinate doAsCartesianCoordinate() throws ArithmeticException;
	protected abstract SphericCoordinate doAsSphericCoordinate() throws ArithmeticException;
	protected abstract boolean doIsEqual(Coordinate other);

	public CartesianCoordinate asCartesianCoordinate() throws ArithmeticException {
		this.assertClassInvariants();

		CartesianCoordinate cartesian = this.doAsCartesianCoordinate();

		this.assertArgumentIsNotNull(cartesian);
		this.assertClassInvariants();

		return cartesian;
	}

	public double getCartesianDistance(Coordinate other) throws ArithmeticException {
		this.assertClassInvariants();
		this.assertArgumentIsNotNull(other);

		CartesianCoordinate thisCartesian = this.asCartesianCoordinate();
		CartesianCoordinate otherCartesian = other.asCartesianCoordinate();
		double distance = thisCartesian.getDistance(otherCartesian);

		this.assertArgumentIsNotNaN(distance);
		this.assertArgumentIsNotNegative(distance);
		this.assertClassInvariants();

		return distance;
	}

	public SphericCoordinate asSphericCoordinate() throws ArithmeticException {
		this.assertClassInvariants();

		SphericCoordinate spheric = this.doAsSphericCoordinate();

		this.assertArgumentIsNotNull(spheric);
		this.assertClassInvariants();

		return spheric;
	}

	public double getCentralAngle(Coordinate other) throws ArithmeticException {
		this.assertClassInvariants();
		this.assertArgumentIsNotNull(other);

		SphericCoordinate thisSpheric = this.asSphericCoordinate();
		SphericCoordinate otherSpheric = other.asSphericCoordinate();
		double angle = thisSpheric.getAngleTo(otherSpheric);

		this.assertArgumentIsNotNaN(angle);
		this.assertArgumentIsInAngleRange(angle);
		this.assertClassInvariants();

		return angle;
	}

	public boolean isEqual(Coordinate other) {
		this.assertClassInvariants();
		this.assertArgumentIsNotNull(other);

		boolean equal = this.doIsEqual(other);

		this.assertClassInvariants();

		return equal;
	}

	protected void assertArgumentIsNotNull(Coordinate coordinate) throws IllegalArgumentException {
		if (coordinate == null) {
			throw new IllegalArgumentException("coordinate must not be null.");
		}
	}
	
	protected void assertArgumentIsNotNaN(double value) throws IllegalArgumentException {
		if (Double.isNaN(value)) {
			throw new IllegalArgumentException("value must not be NaN.");
		}
	}

	protected void assertArgumentIsNotNegative(double value) throws IllegalArgumentException {
		if (value < 0) {
			throw new IllegalArgumentException("value must not be negative.");
		}
	}

	protected void assertArgumentIsInAngleRange(double angle) throws IllegalArgumentException {
		if (!isAngleInRange(angle)) {
			throw new IllegalArgumentException("angle must be between minus PI and PI.");
		}
	}

	protected static boolean isAngleInRange(double angle) {
		return angle <= Math.PI && angle > -Math.PI;
	}
}