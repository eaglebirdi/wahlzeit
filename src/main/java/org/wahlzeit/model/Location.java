package org.wahlzeit.model;

/**
 * A location represents the local position of a photo.
 */
public class Location {
	protected Coordinate coordinate;
	
	public Location(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public Coordinate getCoordinate() {
		return this.coordinate;
	}
}
