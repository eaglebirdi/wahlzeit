package org.wahlzeit.services;

import org.wahlzeit.model.*;

public interface Coordinate {
	CartesianCoordinate asCartesianCoordinate() throws ArithmeticException, IllegalStateException, IllegalArgumentException;
	double getCartesianDistance(Coordinate other) throws ArithmeticException, IllegalStateException, IllegalArgumentException;
	SphericCoordinate asSphericCoordinate() throws ArithmeticException, IllegalStateException, IllegalArgumentException;
	double getCentralAngle(Coordinate other) throws ArithmeticException, IllegalStateException, IllegalArgumentException;
	boolean isEqual(Coordinate other) throws IllegalStateException, IllegalArgumentException;
}