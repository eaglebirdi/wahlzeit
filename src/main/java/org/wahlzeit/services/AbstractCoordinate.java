package org.wahlzeit.services;

import org.wahlzeit.model.*;

public abstract class AbstractCoordinate implements Coordinate {
	protected AbstractCoordinate() {
	}

	protected abstract void assertClassInvariants() throws InvalidCoordinateException;

	protected abstract CartesianCoordinate doAsCartesianCoordinate() throws ArithmeticException, InvalidCoordinateException;
	protected abstract SphericCoordinate doAsSphericCoordinate() throws ArithmeticException, InvalidCoordinateException;
	protected abstract boolean doIsEqual(Coordinate other) throws InvalidCoordinateException;

	public CartesianCoordinate asCartesianCoordinate() throws InvalidCoordinateException {
		this.assertClassInvariants();

		CartesianCoordinate cartesian;
		try {
			cartesian = this.doAsCartesianCoordinate();
		} catch (ArithmeticException ex) {
			throw new InvalidCoordinateException(ex);
		}

		this.assertResultIsNotNull(cartesian);
		this.assertClassInvariants();

		return cartesian;
	}

	public double getCartesianDistance(Coordinate other) throws InvalidCoordinateException {
		this.assertClassInvariants();
		this.assertArgumentIsNotNull(other);

		CartesianCoordinate thisCartesian = this.asCartesianCoordinate();
		CartesianCoordinate otherCartesian = other.asCartesianCoordinate();
		double distance = thisCartesian.getDistance(otherCartesian);

		this.assertResultIsNotNaN(distance);
		this.assertResultIsNotNegative(distance);
		this.assertClassInvariants();

		return distance;
	}

	public SphericCoordinate asSphericCoordinate() throws InvalidCoordinateException {
		this.assertClassInvariants();

		SphericCoordinate spheric;
		try {
			spheric = this.doAsSphericCoordinate();
		} catch (ArithmeticException ex) {
			throw new InvalidCoordinateException(ex);
		}

		this.assertResultIsNotNull(spheric);
		this.assertClassInvariants();

		return spheric;
	}

	public double getCentralAngle(Coordinate other) throws InvalidCoordinateException {
		this.assertClassInvariants();
		this.assertArgumentIsNotNull(other);

		SphericCoordinate thisSpheric = this.asSphericCoordinate();
		SphericCoordinate otherSpheric = other.asSphericCoordinate();
		double angle = thisSpheric.getAngleTo(otherSpheric);

		this.assertResultIsNotNaN(angle);
		this.assertResultIsInAngleRange(angle);
		this.assertClassInvariants();

		return angle;
	}

	public boolean isEqual(Coordinate other) throws InvalidCoordinateException {
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
	
	protected void assertResultIsNotNull(Coordinate coordinate) throws InvalidCoordinateException {
		if (coordinate == null) {
			throw new InvalidCoordinateException("coordinate must not be null.");
		}
	}
	
	protected void assertArgumentIsNotNaN(double value) throws IllegalArgumentException {
		if (Double.isNaN(value)) {
			throw new IllegalArgumentException("value must not be NaN.");
		}
	}

	protected void assertResultIsNotNaN(double value) throws InvalidCoordinateException {
		if (Double.isNaN(value)) {
			throw new InvalidCoordinateException("value must not be NaN.");
		}
	}

	protected void assertArgumentIsNotNegative(double value) throws IllegalArgumentException {
		if (value < 0) {
			throw new IllegalArgumentException("value must not be negative.");
		}
	}

	protected void assertResultIsNotNegative(double value) throws InvalidCoordinateException {
		if (value < 0) {
			throw new InvalidCoordinateException("value must not be negative.");
		}
	}

	protected void assertArgumentIsInAngleRange(double angle) throws IllegalArgumentException {
		if (!isAngleInRange(angle)) {
			throw new IllegalArgumentException("angle must be between minus PI and PI.");
		}
	}

	protected void assertResultIsInAngleRange(double angle) throws InvalidCoordinateException {
		if (!isAngleInRange(angle)) {
			throw new InvalidCoordinateException("angle must be between minus PI and PI.");
		}
	}

	protected static boolean isAngleInRange(double angle) {
		return angle <= Math.PI && angle > -Math.PI;
	}
}