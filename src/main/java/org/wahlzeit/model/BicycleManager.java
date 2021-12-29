package org.wahlzeit.model;

import java.sql.*;
import java.util.*;

import org.wahlzeit.services.*;

public class BicycleManager extends ObjectManager {
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	private static BicycleManager instance = null;
	
	/**
	 * Public singleton access method.
	 */
	public static synchronized BicycleManager getInstance() {
		if (instance == null) {
			SysLog.logSysInfo("setting BicycleManager");
			setInstance(new BicycleManager());
		}
		
		return instance;
	}
	
	/**
	 * Method to set the singleton instance of BicycleManager.
	 */
	protected static synchronized void setInstance(BicycleManager bicycleManager) {
		if (instance != null) {
			throw new IllegalStateException("attempt to initialize BicycleManager twice");
		}
		
		instance = bicycleManager;
	}
	
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	public static void initialize() {
		BicycleManager manager = getInstance();
		manager.initializeBicycleTypes();
		manager.initializeBicycles();
	}


	/**
	 * In-memory cache for bicycles
	 */
	protected Map<Integer, Bicycle> bicycleCache = new HashMap<Integer, Bicycle>();

	/**
	 * In-memory cache for bicycle types
	 */
	protected Map<Integer, BicycleType> bicycleTypeCache = new HashMap<Integer, BicycleType>();

	public BicycleManager() {
		super();
	}

	@Override
	protected Bicycle createObject(ResultSet rset) throws SQLException, InvalidPersistentObjectException {
		Bicycle bicycle = new Bicycle(rset);
		return bicycle;
	}

	public Bicycle getBicycle(int id) {
		Bicycle bicycle = this.bicycleCache.get(id);
		return bicycle;
	}

	public BicycleType getBicycleType(int id) {
		BicycleType bicycleType = this.bicycleTypeCache.get(id);
		return bicycleType;
	}

	private void initializeBicycles() {
		if (!this.bicycleCache.isEmpty()) {
			throw new IllegalStateException("Initialization must not be called, if cache is not empty.");
		}

		ArrayList<Bicycle> bicycles = new ArrayList<Bicycle>();

		try {
			PreparedStatement stmt = this.getReadingStatement("SELECT * FROM bicycles");
			readObjects(bicycles, stmt);
		} catch (SQLException ex) {
			SysLog.logThrowable(ex);
		} catch (InvalidPersistentObjectException ex) {
			SysLog.logThrowable(ex);
		}

		for (Bicycle bicycle : bicycles) {
			this.bicycleCache.put(bicycle.getId(), bicycle);
		}

		SysLog.logSysInfo("loaded all bicycles");
	}

	private void initializeBicycleTypes() {
		if (!this.bicycleTypeCache.isEmpty()) {
			throw new IllegalStateException("Initialization must not be called, if cache is not empty.");
		}

		BicycleType onePersonBike = this.createAndCacheBicycleType(1, "One-person-bike");
		onePersonBike.addSubType(this.createAndCacheBicycleType(11, "City bike"));
		onePersonBike.addSubType(this.createAndCacheBicycleType(12, "Leisure bike"));
		onePersonBike.addSubType(this.createAndCacheBicycleType(13, "Cargo bike"));

		BicycleType multiPersonBike = new BicycleType(2, "Multi-person bike");
		multiPersonBike.addSubType(this.createAndCacheBicycleType(21, "Tandem"));
		multiPersonBike.addSubType(this.createAndCacheBicycleType(22, "Sociable"));

		this.createAndCacheBicycleType(3, "Other bike");
	}

	private BicycleType createAndCacheBicycleType(int id, String name) {
		BicycleType bicycleType = new BicycleType(id, name);
		this.bicycleTypeCache.put(bicycleType.getId(), bicycleType);
		return bicycleType;
	}
}