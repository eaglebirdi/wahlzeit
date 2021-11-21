package org.wahlzeit.services;

import org.wahlzeit.model.*;

public abstract class AbstractCoordinate implements Coordinate {
	protected AbstractCoordinate() {
	}

	protected abstract void assertClassInvariants();

	protected abstract CartesianCoordinate doAsCartesianCoordinate();
	protected abstract SphericCoordinate doAsSphericCoordinate();
	protected abstract boolean doIsEqual(Coordinate other);

	public CartesianCoordinate asCartesianCoordinate() {
		this.assertClassInvariants();

		CartesianCoordinate cartesian = this.doAsCartesianCoordinate();

		this.assertArgumentIsNotNull(cartesian);
		this.assertClassInvariants();

		return cartesian;
	}

	public double getCartesianDistance(Coordinate other) {
		this.assertClassInvariants();
		this.assertArgumentIsNotNull(other);

		CartesianCoordinate thisCartesian = this.asCartesianCoordinate();
		CartesianCoordinate otherCartesian = other.asCartesianCoordinate();
		double distance = thisCartesian.getDistance(otherCartesian);

		this.assertArgumentIsNotNaN(distance);
		this.assertClassInvariants();

		return distance;
	}

	public SphericCoordinate asSphericCoordinate() {
		this.assertClassInvariants();

		SphericCoordinate spheric = this.doAsSphericCoordinate();

		this.assertArgumentIsNotNull(spheric);
		this.assertClassInvariants();

		return spheric;
	}

	public double getCentralAngle(Coordinate other) {
		this.assertClassInvariants();
		this.assertArgumentIsNotNull(other);

		SphericCoordinate thisSpheric = this.asSphericCoordinate();
		SphericCoordinate otherSpheric = other.asSphericCoordinate();
		double angle = thisSpheric.getAngleTo(otherSpheric);

		this.assertArgumentIsNotNaN(angle);
		this.assertAngleIsInRange(angle);
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

	protected void assertArgumentIsNotNull(Coordinate coordinate) {
		if (coordinate == null) {
			throw new IllegalArgumentException("coordinate must not be null.");
		}
	}
	
	protected void assertArgumentIsNotNaN(double value) {
		if (Double.isNaN(value)) {
			throw new IllegalArgumentException("value must not be NaN.");
		}
	}

	protected void assertAngleIsInRange(double angle) {
		if (angle > Math.PI) {
			throw new IllegalArgumentException("angle must not be greater than PI.");
		}

		if (angle < -Math.PI) {
			throw new IllegalArgumentException("angle must be greater than minus PI.");
		}
	}
}