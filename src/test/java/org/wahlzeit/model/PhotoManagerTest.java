package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * All test cases of the class {@link PhotoManagerTest}.
 */
public class PhotoManagerTest {
	/**
	 *
	 */
	@Test
	public void testPhotoManagerInstanceType() {
		PhotoManager pm = PhotoManager.getInstance();
		assertTrue(pm instanceof BicyclePhotoManager);
	}
}