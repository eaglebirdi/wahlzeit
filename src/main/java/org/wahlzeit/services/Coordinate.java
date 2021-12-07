package org.wahlzeit.services;

import org.wahlzeit.model.*;

public interface Coordinate {
	CartesianCoordinate asCartesianCoordinate() throws InvalidCoordinateException;
	double getCartesianDistance(Coordinate other) throws InvalidCoordinateException;
	SphericCoordinate asSphericCoordinate() throws InvalidCoordinateException;
	double getCentralAngle(Coordinate other) throws InvalidCoordinateException;
	boolean isEqual(Coordinate other) throws InvalidCoordinateException;
}