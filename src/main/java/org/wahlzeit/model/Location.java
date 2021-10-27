package org.wahlzeit.model;

import java.sql.*;

/**
 * A location represents the local position of a photo.
 */
public class Location {
	protected Coordinate coordinate;
	
	public Location(Coordinate coordinate) {
		if (coordinate == null){
			throw new IllegalArgumentException("coordinate must not be null.");
		} 

		this.coordinate = coordinate;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public Coordinate getCoordinate() {
		return this.coordinate;
	}

	/**
	 * 
	 */
	public void writeOn(ResultSet rset) throws SQLException {
		this.coordinate.writeOn(rset);
	}
}
