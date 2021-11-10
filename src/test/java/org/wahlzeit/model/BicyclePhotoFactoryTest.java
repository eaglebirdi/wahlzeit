package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * All test cases of the class {@link BicyclePhotoFactoryTest}.
 */
public class BicyclePhotoFactoryTest {
	/**
	 *
	 */
	@Test
	public void testCreatePhotoReturnType() {
		PhotoFactory factory = BicyclePhotoFactory.getInstance();
		Photo photo = factory.createPhoto();
		assertTrue(photo instanceof BicyclePhoto);
	}
}