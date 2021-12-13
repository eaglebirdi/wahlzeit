package org.wahlzeit.services;

import java.util.HashMap;

public class ValueObjectRepository<T>
{
	protected HashMap<T, T> values = new HashMap<T, T>();

	public T getOrPut(T obj) {
		if (this.values.containsKey(obj)) {
			return this.values.get(obj);
		} else {
			this.values.put(obj, obj);
			return obj;
		}
	}
}