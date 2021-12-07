package org.wahlzeit.model;

import java.sql.*;

/**
 * A location represents the local position of a photo.
 */
public class Location {
	protected CartesianCoordinate coordinate;
	
	public Location(CartesianCoordinate coordinate) {
		if (coordinate == null){
			throw new IllegalArgumentException("coordinate must not be null.");
		} 

		this.coordinate = coordinate;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public CartesianCoordinate getCoordinate() {
		return this.coordinate;
	}

	/**
	 * 
	 */
	public void writeOn(ResultSet rset) throws SQLException, InvalidCoordinateException {
		this.coordinate.writeOn(rset);
	}
}
