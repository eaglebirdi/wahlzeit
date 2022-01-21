package org.wahlzeit.model;

import java.sql.*;
import java.util.*;
import org.wahlzeit.services.DataObject;

public class BicycleType extends DataObject {
	protected int id;
	protected String name;
	protected Integer superTypeId;
	protected BicycleType superType;
	protected Set<BicycleType> subTypes = new HashSet<BicycleType>();

	public BicycleType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public BicycleType(ResultSet rset) throws SQLException, InvalidPersistentObjectException {
		this.readFrom(rset);
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

	public boolean isSubtypeOf(BicycleType type) {
		if (type == null) {
			throw new IllegalArgumentException("type must not be null.");
		}

		if (this == type) {
			return true;
		}

		for (BicycleType subType : type.subTypes) {
			if (this.isSubtypeOf(subType)) {
				return true;
			}
		}

		return false;
	}

	public Integer getSuperTypeId() {
		return this.superTypeId;
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

	@Override
	public String getIdAsString() {
		return Integer.toString(this.id);
	}

	@Override
	public void readFrom(ResultSet rset) throws SQLException, InvalidPersistentObjectException {
		this.id = rset.getInt("id");
		this.name = rset.getString("bicycle_type_name");

		Integer superTypeId = rset.getInt("super_type_id");

		if (rset.wasNull()) {
			this.superTypeId = null;
		} else {
			this.superTypeId = superTypeId;
		}
	}

	@Override
	public void writeOn(ResultSet rset) throws SQLException, InvalidPersistentObjectException {
		rset.updateInt("id", this.id);
		rset.updateString("bicycle_type_name", this.name);

		if (this.superTypeId == null) {
			rset.updateNull("super_type_id");
		} else {
			rset.updateInt("super_type_id", this.superTypeId);
		}
	}

	@Override
	public void writeId(PreparedStatement stmt, int pos) throws SQLException {
		stmt.setInt(pos, this.id);
	}
}