package org.wahlzeit.model;

import java.sql.*;
import org.wahlzeit.services.PatternInstance;

@PatternInstance(
	patternName = "Abstract Factory",
	participants = { "ConcreteProduct" }
)
public class BicyclePhoto extends Photo {
	protected Bicycle bicycle;

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
	public BicyclePhoto(ResultSet rset) throws SQLException, InvalidPersistentObjectException {
		super(rset);
	}

	/**
	 * 
	 * @methodtype get
	 */
	public Bicycle getBicycle() {
		return this.bicycle;
	}

	/**
	 * 
	 * @methodtype set
	 */
	public void setBicycle(Bicycle bicycle) {
		this.bicycle = bicycle;
	}

	/**
	 * 
	 */
	@Override	
	public void writeOn(ResultSet rset) throws SQLException, InvalidPersistentObjectException {
		super.writeOn(rset);
		if (this.bicycle == null) {
			rset.updateNull("bicycle_id");
		} else {
			rset.updateInt("bicycle_id", this.bicycle.getId());
		}
	}

	/**
	 * 
	 */
	@Override	
	public void readFrom(ResultSet rset) throws SQLException, InvalidPersistentObjectException {
		super.readFrom(rset);
		int bicycle_id = rset.getInt("bicycle_id");
		if (rset.wasNull()) {
			this.bicycle = null;
		} else {
			this.bicycle = BicycleManager.getInstance().getBicycle(bicycle_id);
		}
	}
}