package org.wahlzeit.model;

import java.sql.*;

/**
 * A bicycle photo manager provides access to and manages bicycle photos.
 */
public class BicyclePhotoManager extends PhotoManager {
	/**
	 * 
	 */
	public BicyclePhotoManager() {
		photoTagCollector = BicyclePhotoFactory.getInstance().createPhotoTagCollector();
	}

	/**
	 * 
	 */
	protected BicyclePhoto createObject(ResultSet rset) throws SQLException, InvalidPersistentObjectException {
		return BicyclePhotoFactory.getInstance().createPhoto(rset);
	}
}
