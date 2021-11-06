package org.wahlzeit.model;

import java.sql.*;

public class BicyclePhoto extends Photo {
	protected String brandName;

	/**
	 * @methodtype constructor
	 */
	public BicyclePhoto() {
		super();
	}
	
	/**
	 * 
	 * @methodtype constructor
	 */
	public BicyclePhoto(PhotoId myId) {
		super(myId);
	}
	
	/**
	 * 
	 * @methodtype constructor
	 */
	public BicyclePhoto(ResultSet rset) throws SQLException {
		super(rset);
	}

	/**
	 * 
	 * @methodtype get
	 */
	public String getBrandName() {
		return this.brandName;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * 
	 */
	@Override	
	public void writeOn(ResultSet rset) throws SQLException {
		super.writeOn(rset);
		rset.updateString("brand_name", this.brandName);
	}

	/**
	 * 
	 */
	@Override	
	public void readFrom(ResultSet rset) throws SQLException {
		super.readFrom(rset);
		this.brandName = rset.getString("brand_name");
	}
}