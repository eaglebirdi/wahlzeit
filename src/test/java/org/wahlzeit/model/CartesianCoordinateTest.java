package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * All test cases of the class {@link CartesianCoordinate}.
 */
public class CartesianCoordinateTest {
	private static final double EPSILON = 0.0001;
	private static final double SUBEPSILON = EPSILON * 0.9;

	/**
	 *
	 */
	@Test
	public void testAsCartesianCoordinate() {
		CartesianCoordinate cartesian = new CartesianCoordinate(1.0, 2.0, 3.0);
		CartesianCoordinate result = cartesian.asCartesianCoordinate();
		assertEquals(cartesian, result);
		assertNotSame(cartesian, result);
	}

	/**
	 *
	 */
	@Test
	public void testGetCartesianDistance() {
		assertEquals(5.0990195, new CartesianCoordinate(1.0, 2.0, 3.0).getCartesianDistance(new CartesianCoordinate(2.0, 5.0, -1.0)), EPSILON);
		assertEquals(1.0, new CartesianCoordinate(0.0, 0.0, 0.0).getCartesianDistance(new CartesianCoordinate(-1.0, 0.0, 0.0)), EPSILON);
		assertEquals(1.4142136, new CartesianCoordinate(-1.0, -2.0, 0.0).getCartesianDistance(new CartesianCoordinate(0.0, -3.0, 0.0)), EPSILON);
		assertEquals(0.0, new CartesianCoordinate(1.0, 2.0, 3.0).getCartesianDistance(new CartesianCoordinate(1.0, 2.0, 3.0)), EPSILON);
	}

	/**
	 *
	 */
	@Test
	public void testAsSphericCoordinate() {
		CartesianCoordinate coordinate = new CartesianCoordinate(1.0, 2.0, 3.0);
		SphericCoordinate sphericActual = coordinate.asSphericCoordinate();
		SphericCoordinate sphericExpected = new SphericCoordinate(3.7416573867739, 1.107148718, 0.6405223127);
		assertEquals(sphericExpected, sphericActual);
	}

	/**
	 *
	 */
	@Test
	public void testGetCentralAngle() {
		CartesianCoordinate coordinate1 = new CartesianCoordinate(1, 2, 3);

		CartesianCoordinate coordinate2Cartesian = new CartesianCoordinate(2, 0, -3);
		double result1 = coordinate1.getCentralAngle(coordinate2Cartesian);
		assertEquals(1.53764057521456, result1, EPSILON);

		SphericCoordinate coordinate2Spheric = coordinate2Cartesian.asSphericCoordinate();
		double result2 = coordinate1.getCentralAngle(coordinate2Spheric);
		assertEquals(1.53764057521456, result2, EPSILON);
	}

	/**
	 *
	 */
	@Test
	public void testIsEqual() {
		CartesianCoordinate coordinate = new CartesianCoordinate(1.0, 2.0, 3.0);
		CartesianCoordinate other = new CartesianCoordinate(2.0, 5.0, -1.0);
		assertTrue(coordinate.isEqual(coordinate));
		assertTrue(coordinate.isEqual(coordinate.asSphericCoordinate()));
		assertTrue(coordinate.isEqual(coordinate.asCartesianCoordinate()));
		assertFalse(coordinate.isEqual(other));
		assertFalse(coordinate.isEqual(other.asSphericCoordinate()));
		assertFalse(coordinate.isEqual(other.asCartesianCoordinate()));

		assertTrue(new CartesianCoordinate(1.0, 2.0, 3.0).isEqual(new CartesianCoordinate(1.0, 2.0, 3.0)));
		assertFalse(new CartesianCoordinate(1.0, 2.0, 3.0).isEqual(new CartesianCoordinate(1.0, 2.0, 4.0)));
		assertTrue(new CartesianCoordinate(1.0, 2.0, 3.0).isEqual(new CartesianCoordinate(1.0 + SUBEPSILON, 2.0 + SUBEPSILON, 3.0 + SUBEPSILON)));
		assertFalse(new CartesianCoordinate(1.0, 2.0, 3.0).isEqual(new CartesianCoordinate(1.0 + EPSILON, 2.0 + EPSILON, 3.0 + EPSILON)));
	}

	/**
	 *
	 */
	@Test
	public void testEquals() {
		assertFalse(new CartesianCoordinate(1.0, 2.0, 3.0).equals(null));
		assertFalse(new CartesianCoordinate(1.0, 2.0, 3.0).equals(new Object()));
		assertFalse(new CartesianCoordinate(1.0, 2.0, 3.0).equals(new CartesianCoordinate(1.0, 2.0, 4.0)));
		assertTrue(new CartesianCoordinate(1.0, 2.0, 3.0).equals(new CartesianCoordinate(1.0, 2.0, 3.0)));
		assertTrue(new CartesianCoordinate(1.0, 2.0, 3.0).equals(new CartesianCoordinate(1.0, 2.0, 3.0).asSphericCoordinate()));
	}
}