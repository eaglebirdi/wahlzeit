package org.wahlzeit.testEnvironmentProvider;

import static org.junit.Assert.fail;

public class AssertionHelper {
	public static <T extends Throwable>T assertThrows(Class<T> expectedType, Runnable executedCode) {
		try {
			executedCode.run();
			fail("No exception of type '" + expectedType.getName() + "' was thrown.");
			return null;
		} catch (Exception ex) {
			if (expectedType.isInstance(ex)) {
				return (T)ex;
			}
			throw ex;
		}
	}
}