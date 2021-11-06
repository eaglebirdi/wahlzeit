package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * All test cases of the class {@link Coordinate}.
 */
public class CoordinateTest {
	private static final double EPSILON = 0.0001;
	private static final double SUBEPSILON = EPSILON * 0.9;

	/**
	 *
	 */
	@Test
	public void testGetDistance() {
		assertEquals(5.0990195, new Coordinate(1.0, 2.0, 3.0).getDistance(new Coordinate(2.0, 5.0, -1.0)), EPSILON);
		assertEquals(1.0, new Coordinate(0.0, 0.0, 0.0).getDistance(new Coordinate(-1.0, 0.0, 0.0)), EPSILON);
		assertEquals(1.4142136, new Coordinate(-1.0, -2.0, 0.0).getDistance(new Coordinate(0.0, -3.0, 0.0)), EPSILON);
		assertEquals(0.0, new Coordinate(1.0, 2.0, 3.0).getDistance(new Coordinate(1.0, 2.0, 3.0)), EPSILON);
	}

	/**
	 *
	 */
	@Test
	public void testIsEqual() {
		assertTrue(new Coordinate(1.0, 2.0, 3.0).isEqual(new Coordinate(1.0, 2.0, 3.0)));
		assertFalse(new Coordinate(1.0, 2.0, 3.0).isEqual(new Coordinate(1.0, 2.0, 4.0)));
		assertTrue(new Coordinate(1.0, 2.0, 3.0).isEqual(new Coordinate(1.0 + SUBEPSILON, 2.0 + SUBEPSILON, 3.0 + SUBEPSILON)));
		assertFalse(new Coordinate(1.0, 2.0, 3.0).isEqual(new Coordinate(1.0 + EPSILON, 2.0 + EPSILON, 3.0 + EPSILON)));
	}

	/**
	 *
	 */
	@Test
	public void testEquals() {
		assertFalse(new Coordinate(1.0, 2.0, 3.0).equals(null));
		assertFalse(new Coordinate(1.0, 2.0, 3.0).equals(new Object()));
		assertFalse(new Coordinate(1.0, 2.0, 3.0).isEqual(new Coordinate(1.0, 2.0, 4.0)));
		assertTrue(new Coordinate(1.0, 2.0, 3.0).isEqual(new Coordinate(1.0, 2.0, 3.0)));
	}
}