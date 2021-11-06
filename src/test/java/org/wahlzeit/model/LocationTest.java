package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertSame;

/**
 * All test cases of the class {@link Location}.
 */
public class LocationTest {
	/**
	 *
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorPassNullCoordinate() {
		Coordinate coordinate = null;
		new Location(coordinate);
	}

	/**
	 *
	 */
	@Test
	public void testConstructorPassNotNullCoordinate() {
		Coordinate coordinate = new Coordinate(0.0, 0.0, 0.0);
		Location location = new Location(coordinate);
		assertSame(coordinate, location.getCoordinate());
	}
}