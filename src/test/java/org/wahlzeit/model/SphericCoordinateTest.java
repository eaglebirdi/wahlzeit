package org.wahlzeit.model;

import org.wahlzeit.testEnvironmentProvider.AssertionHelper;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * All test cases of the class {@link SphericCoordinate}.
 */
public class SphericCoordinateTest {
	private static final double EPSILON = 0.0001;
	private static final double SUBEPSILON = EPSILON * 0.9;

	/**
	 *
	 */
	@Test
	public void testAsCartesianCoordinate() throws InvalidCoordinateException {
		SphericCoordinate coordinate = new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI);
		CartesianCoordinate cartesianActual = coordinate.asCartesianCoordinate();
		CartesianCoordinate cartesianExpected = new CartesianCoordinate(0.5822798637, 0.6939341195, 3.380740392);
		assertEquals(cartesianExpected, cartesianActual);
	}

	/**
	 * 
	 */
	@Test
	public void testGetCartesianDistance() throws InvalidCoordinateException {
		SphericCoordinate coordinate1 = new SphericCoordinate(1, 45.0/180*Math.PI, 15.0/180*Math.PI);

		SphericCoordinate coordinate2Spheric = new SphericCoordinate(2, 30.0/180*Math.PI, 60.0/180*Math.PI);
		double result1 = coordinate1.getCartesianDistance(coordinate2Spheric);
		assertEquals(1.483955169, result1, EPSILON);

		CartesianCoordinate coordinate2Cartesian = coordinate2Spheric.asCartesianCoordinate();
		double result2 = coordinate1.getCartesianDistance(coordinate2Cartesian);
		assertEquals(1.483955169, result2, EPSILON);
	}

	/**
	 *
	 */
	@Test
	public void testAsSphericCoordinate() throws InvalidCoordinateException {
		SphericCoordinate coordinate = new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI);
		SphericCoordinate result = coordinate.asSphericCoordinate();
		assertEquals(coordinate, result);
		assertNotSame(coordinate, result);
	}

	/**
	 *
	 */
	@Test
	public void testGetAngleTo() throws InvalidCoordinateException {
		SphericCoordinate sphericCoordinate1 = new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI);
		SphericCoordinate sphericCoordinate2 = new SphericCoordinate(2.5, 20.0/180*Math.PI, 85.0/180*Math.PI);
		double result1 = sphericCoordinate1.getAngleTo(sphericCoordinate2);
		assertEquals(1.23370728859045, result1, EPSILON);

		SphericCoordinate cartesianCoordinate1 = new CartesianCoordinate(0, 1, 1).asSphericCoordinate();
		SphericCoordinate cartesianCoordinate2 = new CartesianCoordinate(0, 1, 0).asSphericCoordinate();
		double result2 = cartesianCoordinate1.getAngleTo(cartesianCoordinate2);
		assertEquals(Math.PI / 4, result2, EPSILON);
	}

	/**
	 *
	 */
	@Test
	public void testIsEqual() throws InvalidCoordinateException {
		SphericCoordinate coordinate = new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI);
		SphericCoordinate other = new SphericCoordinate(2.5, 20.0/180*Math.PI, 85.0/180*Math.PI);
		assertTrue(coordinate.isEqual(coordinate));
		assertTrue(coordinate.isEqual(coordinate.asSphericCoordinate()));
		assertTrue(coordinate.isEqual(coordinate.asCartesianCoordinate()));
		assertFalse(coordinate.isEqual(other));
		assertFalse(coordinate.isEqual(other.asSphericCoordinate()));
		assertFalse(coordinate.isEqual(other.asCartesianCoordinate()));

		assertTrue(new SphericCoordinate(1.0, 45.0/180*Math.PI, 120.0/180*Math.PI).isEqual(new SphericCoordinate(1.0, 45.0/180*Math.PI, 120.0/180*Math.PI)));
		assertFalse(new SphericCoordinate(1.0, 45.0/180*Math.PI, 120.0/180*Math.PI).isEqual(new SphericCoordinate(1.0, 45.0/180*Math.PI, 150.0/180*Math.PI)));
		assertTrue(new SphericCoordinate(1.0, 45.0/180*Math.PI, 120.0/180*Math.PI).isEqual(new SphericCoordinate(1.0 + SUBEPSILON, 45.0/180*Math.PI + SUBEPSILON, 120.0/180*Math.PI + SUBEPSILON)));
		assertFalse(new SphericCoordinate(1.0, 45.0/180*Math.PI, 120.0/180*Math.PI).isEqual(new SphericCoordinate(1.0 + EPSILON, 45.0/180*Math.PI + EPSILON, 120.0/180*Math.PI + EPSILON)));
	}

	/**
	 *
	 */
	@Test
	public void testEquals() throws InvalidCoordinateException {
		assertFalse(new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).equals(null));
		assertFalse(new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).equals(new Object()));
		assertFalse(new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).equals(new SphericCoordinate(3.5, 50.0/180*Math.PI, 20.0/180*Math.PI)));
		assertTrue(new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).equals(new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI)));
		assertTrue(new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).equals(new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).asCartesianCoordinate()));
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsRadiusIsNaN() throws InvalidCoordinateException {
		new SphericCoordinate(Double.NaN, Math.PI, Math.PI);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsRadiusIsNegative() throws InvalidCoordinateException {
		new SphericCoordinate(-1, Math.PI, Math.PI);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsThetaIsNaN() throws InvalidCoordinateException {
		new SphericCoordinate(1, Double.NaN, Math.PI);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsPhiIsNaN() throws InvalidCoordinateException {
		new SphericCoordinate(1, Math.PI, Double.NaN);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsThetaIsTooBig() throws InvalidCoordinateException {
		new SphericCoordinate(1, Math.PI + 0.01, Math.PI);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsPhiIsTooBig() throws InvalidCoordinateException {
		new SphericCoordinate(1, Math.PI, Math.PI + 0.01);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsThetaIsTooSmall() throws InvalidCoordinateException {
		new SphericCoordinate(1, -Math.PI - 0.01, Math.PI);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsPhiIsTooSmall() throws InvalidCoordinateException {
		new SphericCoordinate(1, Math.PI, -Math.PI - 0.01);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsThetaAndPhiAreBothZero() throws InvalidCoordinateException {
		new SphericCoordinate(1, 0, 0);
	}

	/**
	 * 
	 */
	@Test
	public void testConstructorAssertionsValidArguments() throws InvalidCoordinateException {
		new SphericCoordinate(0, 0, 0); // no exception is thrown
		new SphericCoordinate(1, 2, 3); // no exception is thrown
	}

	/**
	 * 
	*/
	@Test(expected = IllegalArgumentException.class)
	public void testGetAngleToAssertionsNullArgument() throws InvalidCoordinateException {
		SphericCoordinate coordinate = new SphericCoordinate(1, Math.PI, Math.PI);
		coordinate.getAngleTo(null);
	}

	/**
	 * 
	*/
	@Test
	public void testGetAngleToAssertionsValidArgument() throws InvalidCoordinateException {
		SphericCoordinate coordinate = new SphericCoordinate(1, Math.PI, Math.PI);
		coordinate.getAngleTo(coordinate); // no exception is thrown
	}
}