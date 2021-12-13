package org.wahlzeit.model;

import java.sql.*;
import org.wahlzeit.services.DesignPattern;

/**
 * A bicycle photo manager provides access to and manages bicycle photos.
 */
@DesignPattern(
	name = "Singleton",
	participants = { "Singleton" }
)
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
