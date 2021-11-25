package org.wahlzeit.services;

import org.wahlzeit.model.*;

public abstract class AbstractCoordinate implements Coordinate {
	protected AbstractCoordinate() {
	}

	public abstract CartesianCoordinate asCartesianCoordinate() throws ArithmeticException;
	public abstract SphericCoordinate asSphericCoordinate() throws ArithmeticException;
	public abstract boolean isEqual(Coordinate other);

	public double getCartesianDistance(Coordinate other) throws ArithmeticException {
		CartesianCoordinate thisCartesian = this.asCartesianCoordinate();
		CartesianCoordinate otherCartesian = other.asCartesianCoordinate();
		return thisCartesian.getDistance(otherCartesian);
	}

	public double getCentralAngle(Coordinate other) throws ArithmeticException {
		SphericCoordinate thisSpheric = this.asSphericCoordinate();
		SphericCoordinate otherSpheric = other.asSphericCoordinate();
		return thisSpheric.getAngleTo(otherSpheric);
	}
}