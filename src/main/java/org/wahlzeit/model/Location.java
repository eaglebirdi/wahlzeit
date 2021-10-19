package org.wahlzeit.model;

import java.sql.*;
import java.net.*;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.*;

/**
 * A location represents the local position of a photo.
 */
public class Location {
	protected Coordinate coordinate;
	
	public Location(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
}
