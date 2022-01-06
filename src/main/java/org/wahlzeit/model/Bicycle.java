package org.wahlzeit.model;

import java.sql.*;
import org.wahlzeit.services.*;

public class Bicycle extends DataObject {
	protected int id;
	protected BicycleType bicycleType;
	protected String brandName;
	protected String modelName;

	public Bicycle() {
	}

	public Bicycle(ResultSet rset) throws SQLException, InvalidPersistentObjectException {
		this.readFrom(rset);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BicycleType getBicycleType() {
		return this.bicycleType;
	}

	public void setBicycleType(BicycleType bicycleType) {
		this.bicycleType = bicycleType;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Override
	public String getIdAsString() {
		return Integer.toString(this.id);
	}

	@Override
	public void readFrom(ResultSet rset) throws SQLException, InvalidPersistentObjectException {
		this.id = rset.getInt("id");
		this.bicycleType = BicycleManager.getInstance().getBicycleType(rset.getInt("bicycle_type_id"));
		this.brandName = rset.getString("brand_name");
		this.modelName = rset.getString("model_name");
	}

	@Override
	public void writeOn(ResultSet rset) throws SQLException, InvalidPersistentObjectException {
		rset.updateInt("id", this.id);
		rset.updateInt("bicycle_type_id", this.bicycleType.getId());
		rset.updateString("brand_name", this.brandName);
		rset.updateString("model_name", this.modelName);
	}

	@Override
	public void writeId(PreparedStatement stmt, int pos) throws SQLException {
		stmt.setInt(pos, this.id);
	}
}