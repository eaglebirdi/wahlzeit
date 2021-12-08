package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
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
	public void testAsCartesianCoordinate() throws InvalidCoordinateException {
		CartesianCoordinate cartesian = CartesianCoordinate.create(1.0, 2.0, 3.0);
		CartesianCoordinate result = cartesian.asCartesianCoordinate();
		assertEquals(cartesian, result);
		assertSame(cartesian, result);
	}

	/**
	 *
	 */
	@Test
	public void testGetDistance() throws InvalidCoordinateException {
		assertEquals(5.0990195, CartesianCoordinate.create(1.0, 2.0, 3.0).getDistance(CartesianCoordinate.create(2.0, 5.0, -1.0)), EPSILON);
		assertEquals(1.0, CartesianCoordinate.create(0.0, 0.0, 0.0).getDistance(CartesianCoordinate.create(-1.0, 0.0, 0.0)), EPSILON);
		assertEquals(1.4142136, CartesianCoordinate.create(-1.0, -2.0, 0.0).getDistance(CartesianCoordinate.create(0.0, -3.0, 0.0)), EPSILON);
		assertEquals(0.0, CartesianCoordinate.create(1.0, 2.0, 3.0).getDistance(CartesianCoordinate.create(1.0, 2.0, 3.0)), EPSILON);
	}

	/**
	 *
	 */
	@Test
	public void testAsSphericCoordinateNonZeroCoordinate() throws InvalidCoordinateException {
		CartesianCoordinate coordinate = CartesianCoordinate.create(1.0, 2.0, 3.0);
		SphericCoordinate sphericActual = coordinate.asSphericCoordinate();
		SphericCoordinate sphericExpected = SphericCoordinate.create(3.7416573867739, 1.107148718, 0.6405223127);
		assertEquals(sphericExpected, sphericActual);
	}

	/**
	 *
	 */
	@Test
	public void testAsSphericCoordinateZeroCoordinate() throws InvalidCoordinateException {
		CartesianCoordinate coordinate = CartesianCoordinate.create(0, 0, 0);
		SphericCoordinate sphericActual = coordinate.asSphericCoordinate();
		SphericCoordinate sphericExpected = SphericCoordinate.create(0, 0, 0);
		assertEquals(sphericExpected, sphericActual);
	}

	/**
	 *
	 */
	@Test
	public void testGetCentralAngle() throws InvalidCoordinateException {
		CartesianCoordinate coordinate1 = CartesianCoordinate.create(1, 2, 3);

		CartesianCoordinate coordinate2Cartesian = CartesianCoordinate.create(2, 0, -3);
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
	public void testIsEqual() throws InvalidCoordinateException {
		CartesianCoordinate coordinate = CartesianCoordinate.create(1.0, 2.0, 3.0);
		CartesianCoordinate other = CartesianCoordinate.create(2.0, 5.0, -1.0);
		assertTrue(coordinate.isEqual(coordinate));
		assertTrue(coordinate.isEqual(coordinate.asSphericCoordinate()));
		assertTrue(coordinate.isEqual(coordinate.asCartesianCoordinate()));
		assertFalse(coordinate.isEqual(other));
		assertFalse(coordinate.isEqual(other.asSphericCoordinate()));
		assertFalse(coordinate.isEqual(other.asCartesianCoordinate()));

		assertTrue(CartesianCoordinate.create(1.0, 2.0, 3.0).isEqual(CartesianCoordinate.create(1.0, 2.0, 3.0)));
		assertFalse(CartesianCoordinate.create(1.0, 2.0, 3.0).isEqual(CartesianCoordinate.create(1.0, 2.0, 4.0)));
		assertTrue(CartesianCoordinate.create(1.0, 2.0, 3.0).isEqual(CartesianCoordinate.create(1.000049, 1.99995, 3.0)));
		assertFalse(CartesianCoordinate.create(1.0, 2.0, 3.0).isEqual(CartesianCoordinate.create(1.00005, 2.000049, 3.0)));
	}

	/**
	 *
	 */
	@Test
	public void testEquals() throws InvalidCoordinateException {
		assertFalse(CartesianCoordinate.create(1.0, 2.0, 3.0).equals(null));
		assertFalse(CartesianCoordinate.create(1.0, 2.0, 3.0).equals(new Object()));
		assertFalse(CartesianCoordinate.create(1.0, 2.0, 3.0).equals(CartesianCoordinate.create(1.0, 2.0, 4.0)));
		assertTrue(CartesianCoordinate.create(1.0, 2.0, 3.0).equals(CartesianCoordinate.create(1.0, 2.0, 3.0)));
		assertTrue(CartesianCoordinate.create(1.0, 2.0, 3.0).equals(CartesianCoordinate.create(1.0, 2.0, 3.0).asSphericCoordinate()));
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsXIsNaN() throws InvalidCoordinateException {
		CartesianCoordinate.create(Double.NaN, 2, 3);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsYIsNaN() throws InvalidCoordinateException {
		CartesianCoordinate.create(1, Double.NaN, 3);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsZIsNaN() throws InvalidCoordinateException {
		CartesianCoordinate.create(1, 2, Double.NaN);
	}

	/**
	 * 
	 */
	@Test
	public void testConstructorAssertionsValidArguments() throws InvalidCoordinateException {
		CartesianCoordinate.create(1, 2, 3); // no exception is thrown
	}

	/**
	 * 
	*/
	@Test(expected = IllegalArgumentException.class)
	public void testGetDistanceAssertionsNullArgument() throws InvalidCoordinateException {
		CartesianCoordinate coordinate = CartesianCoordinate.create(1, 2, 3);
		coordinate.getDistance(null);
	}

	/**
	 * 
	*/
	@Test
	public void testGetDistanceAssertionsValidArgument() throws InvalidCoordinateException {
		CartesianCoordinate coordinate = CartesianCoordinate.create(1, 2, 3);
		coordinate.getDistance(coordinate); // no exception is thrown
	}

	@Test
	public void testCreateNearlyEqualAreSame() throws InvalidCoordinateException {
		CartesianCoordinate cartesian1 = CartesianCoordinate.create(1, 2, 3);
		CartesianCoordinate cartesian2 = CartesianCoordinate.create(1.000049, 2, 3);
		assertEquals(cartesian1, cartesian2);
		assertSame(cartesian1, cartesian2);
	}

	@Test
	public void testCreateNotNearlyEqualAreNotSame() throws InvalidCoordinateException {
		CartesianCoordinate cartesian1 = CartesianCoordinate.create(1, 2, 3);
		CartesianCoordinate cartesian2 = CartesianCoordinate.create(1.00005, 2, 3);
		assertNotEquals(cartesian1, cartesian2);
		assertNotSame(cartesian1, cartesian2);
	}
	
}