package org.wahlzeit.model;

import java.sql.*;

import org.wahlzeit.services.*;

@PatternInstance(
	patternName = "Abstract Factory",
	participants = { "ConcreteFactory" }
)
public class BicyclePhotoFactory extends PhotoFactory {
	
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	private static BicyclePhotoFactory instance = null;
	
	/**
	 * Public singleton access method.
	 */
	public static synchronized BicyclePhotoFactory getInstance() {
		if (instance == null) {
			SysLog.logSysInfo("setting BicyclePhotoFactory");
			setInstance(new BicyclePhotoFactory());
		}
		
		return instance;
	}
	
	/**
	 * Method to set the singleton instance of BicyclePhotoFactory.
	 */
	protected static synchronized void setInstance(BicyclePhotoFactory photoFactory) {
		if (instance != null) {
			throw new IllegalStateException("attempt to initialize BicyclePhotoFactory twice");
		}
		
		instance = photoFactory;
	}
	
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	public static void initialize() {
		getInstance(); // drops result due to getInstance() side-effects
	}
	
	/**
	 * 
	 */
	protected BicyclePhotoFactory() {
		super();
	}

	/**
	 * @methodtype factory
	 */
	@Override	
	public BicyclePhoto createPhoto() {
		return new BicyclePhoto();
	}
	
	/**
	 * 
	 */
	@Override	
	public BicyclePhoto createPhoto(PhotoId id) {
		return new BicyclePhoto(id);
	}
	
	/**
	 * 
	 */
	@Override
	public BicyclePhoto createPhoto(ResultSet rs) throws SQLException, InvalidPersistentObjectException {
		return new BicyclePhoto(rs);
	}
}