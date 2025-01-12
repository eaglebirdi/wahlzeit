package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
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
	public void testAsCartesianCoordinateNonZeroCoordinate() throws InvalidCoordinateException {
		SphericCoordinate coordinate = SphericCoordinate.create(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI);
		CartesianCoordinate cartesianActual = coordinate.asCartesianCoordinate();
		CartesianCoordinate cartesianExpected = CartesianCoordinate.create(0.5822798637, 0.6939341195, 3.380740392);
		assertEquals(cartesianExpected, cartesianActual);
	}

	/**
	 *
	 */
	@Test
	public void testAsCartesianCoordinateZeroCoordinate() throws InvalidCoordinateException {
		SphericCoordinate coordinate = SphericCoordinate.create(0, 0, 0);
		CartesianCoordinate cartesianActual = coordinate.asCartesianCoordinate();
		CartesianCoordinate cartesianExpected = CartesianCoordinate.create(0, 0, 0);
		assertEquals(cartesianExpected, cartesianActual);
	}

	/**
	 * 
	 */
	@Test
	public void testGetCartesianDistance() throws InvalidCoordinateException {
		SphericCoordinate coordinate1 = SphericCoordinate.create(1, 45.0/180*Math.PI, 15.0/180*Math.PI);

		SphericCoordinate coordinate2Spheric = SphericCoordinate.create(2, 30.0/180*Math.PI, 60.0/180*Math.PI);
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
		SphericCoordinate coordinate = SphericCoordinate.create(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI);
		SphericCoordinate result = coordinate.asSphericCoordinate();
		assertEquals(coordinate, result);
		assertSame(coordinate, result);
	}

	/**
	 *
	 */
	@Test
	public void testGetAngleTo() throws InvalidCoordinateException {
		SphericCoordinate sphericCoordinate1 = SphericCoordinate.create(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI);
		SphericCoordinate sphericCoordinate2 = SphericCoordinate.create(2.5, 20.0/180*Math.PI, 85.0/180*Math.PI);
		double result1 = sphericCoordinate1.getAngleTo(sphericCoordinate2);
		assertEquals(1.23370728859045, result1, EPSILON);

		SphericCoordinate cartesianCoordinate1 = CartesianCoordinate.create(0, 1, 1).asSphericCoordinate();
		SphericCoordinate cartesianCoordinate2 = CartesianCoordinate.create(0, 1, 0).asSphericCoordinate();
		double result2 = cartesianCoordinate1.getAngleTo(cartesianCoordinate2);
		assertEquals(Math.PI / 4, result2, EPSILON);
	}

	/**
	 *
	 */
	@Test
	public void testIsEqual() throws InvalidCoordinateException {
		SphericCoordinate coordinate = SphericCoordinate.create(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI);
		SphericCoordinate other = SphericCoordinate.create(2.5, 20.0/180*Math.PI, 85.0/180*Math.PI);
		assertTrue(coordinate.isEqual(coordinate));
		assertTrue(coordinate.isEqual(coordinate.asSphericCoordinate()));
		assertTrue(coordinate.isEqual(coordinate.asCartesianCoordinate()));
		assertFalse(coordinate.isEqual(other));
		assertFalse(coordinate.isEqual(other.asSphericCoordinate()));
		assertFalse(coordinate.isEqual(other.asCartesianCoordinate()));

		assertTrue(SphericCoordinate.create(1.0, 45.0/180*Math.PI, 120.0/180*Math.PI).isEqual(SphericCoordinate.create(1.0, 45.0/180*Math.PI, 120.0/180*Math.PI)));
		assertFalse(SphericCoordinate.create(1.0, 45.0/180*Math.PI, 120.0/180*Math.PI).isEqual(SphericCoordinate.create(1.0, 45.0/180*Math.PI, 150.0/180*Math.PI)));
		assertTrue(SphericCoordinate.create(1.0, 45.0/180*Math.PI, 120.0/180*Math.PI).isEqual(SphericCoordinate.create(1.00002, 1.00002 * 45.0/180*Math.PI, 1.00002 * 120.0/180*Math.PI)));
		assertFalse(SphericCoordinate.create(1.0, 45.0/180*Math.PI, 120.0/180*Math.PI).isEqual(SphericCoordinate.create(1.0001, 1.0001 * 45.0/180*Math.PI, 1.0001 * 120.0/180*Math.PI + EPSILON)));
	}

	/**
	 *
	 */
	@Test
	public void testEquals() throws InvalidCoordinateException {
		assertFalse(SphericCoordinate.create(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).equals(null));
		assertFalse(SphericCoordinate.create(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).equals(new Object()));
		assertFalse(SphericCoordinate.create(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).equals(SphericCoordinate.create(3.5, 50.0/180*Math.PI, 20.0/180*Math.PI)));
		assertTrue(SphericCoordinate.create(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).equals(SphericCoordinate.create(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI)));
		assertTrue(SphericCoordinate.create(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).equals(SphericCoordinate.create(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).asCartesianCoordinate()));
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsRadiusIsNaN() throws InvalidCoordinateException {
		SphericCoordinate.create(Double.NaN, Math.PI, Math.PI);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsRadiusIsNegative() throws InvalidCoordinateException {
		SphericCoordinate.create(-1, Math.PI, Math.PI);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsThetaIsNaN() throws InvalidCoordinateException {
		SphericCoordinate.create(1, Double.NaN, Math.PI);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsPhiIsNaN() throws InvalidCoordinateException {
		SphericCoordinate.create(1, Math.PI, Double.NaN);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsThetaIsTooBig() throws InvalidCoordinateException {
		SphericCoordinate.create(1, Math.PI + 0.01, Math.PI);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsPhiIsTooBig() throws InvalidCoordinateException {
		SphericCoordinate.create(1, Math.PI, Math.PI + 0.01);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsThetaIsTooSmall() throws InvalidCoordinateException {
		SphericCoordinate.create(1, -Math.PI - 0.01, Math.PI);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsPhiIsTooSmall() throws InvalidCoordinateException {
		SphericCoordinate.create(1, Math.PI, -Math.PI - 0.01);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorAssertionsThetaAndPhiAreBothZero() throws InvalidCoordinateException {
		SphericCoordinate.create(1, 0, 0);
	}

	/**
	 * 
	 */
	@Test
	public void testConstructorAssertionsValidArguments() throws InvalidCoordinateException {
		SphericCoordinate.create(0, 0, 0); // no exception is thrown
		SphericCoordinate.create(1, 2, 3); // no exception is thrown
	}

	/**
	 * 
	*/
	@Test(expected = IllegalArgumentException.class)
	public void testGetAngleToAssertionsNullArgument() throws InvalidCoordinateException {
		SphericCoordinate coordinate = SphericCoordinate.create(1, Math.PI, Math.PI);
		coordinate.getAngleTo(null);
	}

	/**
	 * 
	*/
	@Test
	public void testGetAngleToAssertionsValidArgument() throws InvalidCoordinateException {
		SphericCoordinate coordinate = SphericCoordinate.create(1, Math.PI, Math.PI);
		coordinate.getAngleTo(coordinate); // no exception is thrown
	}

	@Test
	public void testCreateNearlyEqualAreSame() throws InvalidCoordinateException {
		SphericCoordinate spheric1 = SphericCoordinate.create(1, Math.PI, 0.0);
		SphericCoordinate spheric2 = SphericCoordinate.create(1.000049, Math.PI, 0.0);
		assertEquals(spheric1, spheric2);
		assertSame(spheric1, spheric2);
	}

	@Test
	public void testCreateNotNearlyEqualAreNotSame() throws InvalidCoordinateException {
		SphericCoordinate spheric1 = SphericCoordinate.create(1, Math.PI, 0.0);
		SphericCoordinate spheric2 = SphericCoordinate.create(1.00005, Math.PI, 0.0);
		assertNotEquals(spheric1, spheric2);
		assertNotSame(spheric1, spheric2);
	}
}