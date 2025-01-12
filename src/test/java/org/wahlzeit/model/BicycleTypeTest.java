package org.wahlzeit.model;

import org.junit.Test;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * All test cases of the class {@link org.wahlzeit.model.BicycleType}.
 */
public class BicycleTypeTest {
	@Test
	public void addSubTypeDefault() {
		BicycleType supertype = new BicycleType(1, "super");
		BicycleType subtype = new BicycleType(2, "sub");

		supertype.addSubType(subtype);

		Iterator<BicycleType> subtypesIterator = supertype.getSubTypeIterator();
		assertTrue(subtypesIterator.hasNext());
		BicycleType firstSubtype = subtypesIterator.next();
		assertTrue(firstSubtype == subtype);
		assertFalse(subtypesIterator.hasNext());

		assertTrue(firstSubtype.getSuperType() == supertype);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addSubTypeDoubleAssociation() {
		BicycleType supertype1 = new BicycleType(1, "super1");
		BicycleType supertype2 = new BicycleType(1, "super2");
		BicycleType subtype = new BicycleType(2, "sub");

		supertype1.addSubType(subtype);
		supertype2.addSubType(subtype);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addSubTypePassNull() {
		BicycleType supertype = new BicycleType(1, "super");
		BicycleType subtype = null;

		supertype.addSubType(subtype);
	}

	@Test(expected = IllegalArgumentException.class)
	public void hasInstancePassNull() {
		BicycleType type = new BicycleType(1, "type");
		Bicycle bicycle = null;

		type.hasInstance(bicycle);
	}

	@Test
	public void hasInstanceNoType() {
		BicycleType type = new BicycleType(1, "type");
		Bicycle bicycle = new Bicycle();

		boolean result = type.hasInstance(bicycle);
		assertFalse(result);
	}

	@Test
	public void hasInstanceNoRelationship() {
		BicycleType type1 = new BicycleType(1, "type1");
		BicycleType type2 = new BicycleType(1, "type2");

		Bicycle bicycle = new Bicycle();
		bicycle.setBicycleType(type1);

		boolean result = type2.hasInstance(bicycle);
		assertFalse(result);
	}

	@Test
	public void hasInstanceDirectAssociation() {
		BicycleType type = new BicycleType(1, "type");

		Bicycle bicycle = new Bicycle();
		bicycle.setBicycleType(type);

		boolean result = type.hasInstance(bicycle);
		assertTrue(result);
	}

	@Test
	public void hasInstanceChildAssociation() {
		BicycleType supertype = new BicycleType(1, "super");
		BicycleType subtype = new BicycleType(2, "sub");
		supertype.addSubType(subtype);

		Bicycle bicycle = new Bicycle();
		bicycle.setBicycleType(subtype);

		boolean result = supertype.hasInstance(bicycle);
		assertTrue(result);
	}

	@Test
	public void hasInstanceGrandchildAssociation() {
		BicycleType supertype = new BicycleType(1, "super");
		BicycleType subtype = new BicycleType(2, "sub");
		BicycleType subsubtype = new BicycleType(3, "subsub");
		supertype.addSubType(subtype);
		subtype.addSubType(subsubtype);

		Bicycle bicycle = new Bicycle();
		bicycle.setBicycleType(subsubtype);

		boolean result = supertype.hasInstance(bicycle);
		assertTrue(result);
	}

	@Test
	public void hasInstanceParentAssociation() {
		BicycleType supertype = new BicycleType(1, "super");
		BicycleType subtype = new BicycleType(2, "sub");
		supertype.addSubType(subtype);

		Bicycle bicycle = new Bicycle();
		bicycle.setBicycleType(supertype);

		boolean result = subtype.hasInstance(bicycle);
		assertFalse(result);
	}

	@Test
	public void hasInstanceSiblingAssociation() {
		BicycleType supertype = new BicycleType(1, "super");
		BicycleType subtype1 = new BicycleType(2, "sub1");
		BicycleType subtype2 = new BicycleType(3, "sub2");
		supertype.addSubType(subtype1);
		supertype.addSubType(subtype2);

		Bicycle bicycle = new Bicycle();
		bicycle.setBicycleType(subtype1);

		boolean result = subtype2.hasInstance(bicycle);
		assertFalse(result);
	}

	@Test
	public void isSubType() {
		BicycleType supertype = new BicycleType(1, "super");
		BicycleType subtype = new BicycleType(2, "sub");
		supertype.addSubType(subtype);

		assertFalse(supertype.isSubtype());
		assertTrue(subtype.isSubtype());
	}

	@Test(expected = IllegalArgumentException.class)
	public void isSubTypeOfpassNull() {
		BicycleType type = new BicycleType(1, "");
		type.isSubtypeOf(null);
	}

	@Test
	public void isSubTypeOf() {
		BicycleType supertype = new BicycleType(1, "super");
		BicycleType other = new BicycleType(9, "other");
		BicycleType subtypeLevel1 = new BicycleType(2, "subtypeLevel1");
		BicycleType subtypeLevel2 = new BicycleType(3, "subtypeLevel2");
		supertype.addSubType(subtypeLevel1);
		subtypeLevel1.addSubType(subtypeLevel2);

		assertTrue(supertype.isSubtypeOf(supertype));
		assertFalse(supertype.isSubtypeOf(subtypeLevel1));
		assertFalse(supertype.isSubtypeOf(subtypeLevel2));
		assertFalse(supertype.isSubtypeOf(other));

		assertTrue(subtypeLevel1.isSubtypeOf(supertype));
		assertTrue(subtypeLevel1.isSubtypeOf(subtypeLevel1));
		assertFalse(subtypeLevel1.isSubtypeOf(subtypeLevel2));
		assertFalse(subtypeLevel1.isSubtypeOf(other));

		assertTrue(subtypeLevel2.isSubtypeOf(supertype));
		assertTrue(subtypeLevel2.isSubtypeOf(subtypeLevel1));
		assertTrue(subtypeLevel2.isSubtypeOf(subtypeLevel2));
		assertFalse(subtypeLevel2.isSubtypeOf(other));

		assertFalse(other.isSubtypeOf(supertype));
		assertFalse(other.isSubtypeOf(subtypeLevel1));
		assertFalse(other.isSubtypeOf(subtypeLevel2));
		assertTrue(other.isSubtypeOf(other));
	}
}