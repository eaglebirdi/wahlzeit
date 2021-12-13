package org.wahlzeit.utils;

import java.math.*;

public class MathUtil {
	public static double round(double value, int decimals) {
		double result = BigDecimal.valueOf(value).setScale(decimals, RoundingMode.HALF_UP).doubleValue();
		return result;
	}

	public static double round(double value) {
		return round(value, 4);
	}
}