package org.wahlzeit.services;

import org.wahlzeit.model.*;

public abstract class AbstractCoordinate implements Coordinate {
	protected AbstractCoordinate() {
	}

	public abstract CartesianCoordinate asCartesianCoordinate();
	public abstract SphericCoordinate asSphericCoordinate();
	public abstract boolean isEqual(Coordinate other);

	public double getCartesianDistance(Coordinate other) {
		CartesianCoordinate thisCartesian = this.asCartesianCoordinate();
		CartesianCoordinate otherCartesian = other.asCartesianCoordinate();
		return thisCartesian.getDistance(otherCartesian);
	}

	public double getCentralAngle(Coordinate other) {
		SphericCoordinate thisSpheric = this.asSphericCoordinate();
		SphericCoordinate otherSpheric = other.asSphericCoordinate();
		return thisSpheric.getAngleTo(otherSpheric);
	}
}