package org.wahlzeit.services;

import org.junit.Test;
import org.wahlzeit.model.CartesianCoordinate;

import static org.junit.Assert.assertEquals;

public class AbstractCoordinateTest {
	private static final double EPSILON = 0.0001;

	@Test
	public void testGetCartesianDistance() {
		Coordinate cartesian1 = new CartesianCoordinate(1, 2, 3);
		Coordinate cartesian2 = new CartesianCoordinate(-3, 4, 0);
		Coordinate spheric1 = cartesian1.asSphericCoordinate();
		Coordinate spheric2 = cartesian2.asSphericCoordinate();

		double expected = 5.385164807;

		assertEquals(expected, cartesian1.getCartesianDistance(cartesian2), EPSILON);
		assertEquals(expected, cartesian2.getCartesianDistance(cartesian1), EPSILON);
		assertEquals(expected, cartesian1.getCartesianDistance(spheric2), EPSILON);
		assertEquals(expected, cartesian2.getCartesianDistance(spheric1), EPSILON);
		assertEquals(expected, spheric1.getCartesianDistance(cartesian2), EPSILON);
		assertEquals(expected, spheric2.getCartesianDistance(cartesian1), EPSILON);
		assertEquals(expected, spheric1.getCartesianDistance(spheric2), EPSILON);
		assertEquals(expected, spheric2.getCartesianDistance(spheric1), EPSILON);
	}

	@Test
	public void testGetCentralAngle() {
		Coordinate cartesian1 = new CartesianCoordinate(1, 2, 3);
		Coordinate cartesian2 = new CartesianCoordinate(-3, 4, 0);
		Coordinate spheric1 = cartesian1.asSphericCoordinate();
		Coordinate spheric2 = cartesian2.asSphericCoordinate();

		double expected = 0.930274180647598;

		assertEquals(expected, cartesian1.getCentralAngle(cartesian2), EPSILON);
		assertEquals(expected, cartesian2.getCentralAngle(cartesian1), EPSILON);
		assertEquals(expected, cartesian1.getCentralAngle(spheric2), EPSILON);
		assertEquals(expected, cartesian2.getCentralAngle(spheric1), EPSILON);
		assertEquals(expected, spheric1.getCentralAngle(cartesian2), EPSILON);
		assertEquals(expected, spheric2.getCentralAngle(cartesian1), EPSILON);
		assertEquals(expected, spheric1.getCentralAngle(spheric2), EPSILON);
		assertEquals(expected, spheric2.getCentralAngle(spheric1), EPSILON);
	}
}