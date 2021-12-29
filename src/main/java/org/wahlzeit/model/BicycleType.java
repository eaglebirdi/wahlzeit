package org.wahlzeit.model;

import java.util.*;

public class BicycleType {
	protected int id;
	protected String name;
	protected BicycleType superType;
	protected Set<BicycleType> subTypes = new HashSet<BicycleType>();

	protected BicycleType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Iterator<BicycleType> getSubTypeIterator() {
		return this.subTypes.iterator();
	}

	public void addSubType(BicycleType bt) {
		if (bt == null) {
			throw new IllegalArgumentException("bt must not be null.");
		}

		if (bt.superType != null) {
			throw new IllegalArgumentException("supertype of bt must be null.");
		}

		bt.superType = this;
		this.subTypes.add(bt);
	}

	public int getId() {
		return this.id;
	}

	public boolean isSubtype() {
		return this.superType != null;
	}

	public BicycleType getSuperType() {
		return this.superType;
	}

	public boolean hasInstance(Bicycle bicycle) {
		if (bicycle == null) {
			throw new IllegalArgumentException("bicycle must not be null.");
		}

		if (bicycle.getBicycleType() == this) {
			return true;
		}

		for (BicycleType type : this.subTypes) {
			if (type.hasInstance(bicycle)) {
				return true;
			}
		}

		return false;
	}
}