package org.wahlzeit.model;

import java.sql.*;
import java.net.*;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.*;

/**
 * A coordinate represents the coordinates of a position.
 */
public class Coordinate {
	protected double x;
	protected double y;
	protected double z;
	
	public Coordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
