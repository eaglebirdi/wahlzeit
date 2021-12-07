package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * All test cases of the class {@link Location}.
 */
public class PhotoTest {
	/**
	 *
	 */
	@Test
	public void testLocationIsDefaultNull() {
		Photo photo = new Photo();
		assertEquals(null, photo.getLocation());
	}

	/**
	 *
	 */
	@Test
	public void testLocationGetSet() throws InvalidCoordinateException {

		Photo photo = new Photo();
		Location location = new Location(new CartesianCoordinate(0.0, 0.0, 0.0));
		photo.setLocation(location);
		assertSame(location, photo.getLocation());
	}
}
