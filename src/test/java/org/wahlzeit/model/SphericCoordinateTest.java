package org.wahlzeit.model;

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
	public void testAsCartesianCoordinate() {
		SphericCoordinate coordinate = new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI);
		CartesianCoordinate cartesianActual = coordinate.asCartesianCoordinate();
		CartesianCoordinate cartesianExpected = new CartesianCoordinate(0.5822798637, 0.6939341195, 3.380740392);
		assertEquals(cartesianExpected, cartesianActual);
	}

	/**
	 * 
	 */
	@Test
	public void testGetCartesianDistance() {
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
	public void testAsSphericCoordinate() {
		SphericCoordinate coordinate = new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI);
		SphericCoordinate result = coordinate.asSphericCoordinate();
		assertEquals(coordinate, result);
		assertNotSame(coordinate, result);
	}

	/**
	 *
	 */
	@Test
	public void testGetCentralAngle() {
		SphericCoordinate sphericCoordinate1 = new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI);
		SphericCoordinate sphericCoordinate2 = new SphericCoordinate(2.5, 20.0/180*Math.PI, 85.0/180*Math.PI);
		double result1 = sphericCoordinate1.getCentralAngle(sphericCoordinate2);
		assertEquals(1.23370728859045, result1, EPSILON);

		CartesianCoordinate cartesianCoordinate1 = new CartesianCoordinate(0, 1, 1);
		CartesianCoordinate cartesianCoordinate2 = new CartesianCoordinate(0, 1, 0);
		double result2 = cartesianCoordinate1.getCentralAngle(cartesianCoordinate2);
		assertEquals(Math.PI / 4, result2, EPSILON);
	}

	/**
	 *
	 */
	@Test
	public void testIsEqual() {
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
	public void testEquals() {
		assertFalse(new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).equals(null));
		assertFalse(new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).equals(new Object()));
		assertFalse(new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).equals(new SphericCoordinate(3.5, 50.0/180*Math.PI, 20.0/180*Math.PI)));
		assertTrue(new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).equals(new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI)));
		assertTrue(new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).equals(new SphericCoordinate(3.5, 50.0/180*Math.PI, 15.0/180*Math.PI).asCartesianCoordinate()));
	}
}