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

		// Retrieve all bicycles from the database
		try {
			PreparedStatement stmt = this.getReadingStatement("SELECT * FROM bicycles");
			readObjects(bicycles, stmt);
		} catch (SQLException ex) {
			SysLog.logThrowable(ex);
		} catch (InvalidPersistentObjectException ex) {
			SysLog.logThrowable(ex);
		}

		// Store all bicycles in the cache
		for (Bicycle bicycle : bicycles) {
			this.bicycleCache.put(bicycle.getId(), bicycle);
		}

		SysLog.logSysInfo("loaded all bicycles");
	}

	private void initializeBicycleTypes() {
		if (!this.bicycleTypeCache.isEmpty()) {
			throw new IllegalStateException("Initialization must not be called, if cache is not empty.");
		}

		ArrayList<BicycleType> bicycleTypes = new ArrayList<BicycleType>();

		// Retrieve all bicycle types from the database
		try {
			PreparedStatement stmt = this.getReadingStatement("SELECT * FROM bicycle_types");
			this.readBicycleTypes(bicycleTypes, stmt);
		} catch (SQLException ex) {
			SysLog.logThrowable(ex);
		} catch (InvalidPersistentObjectException ex) {
			SysLog.logThrowable(ex);
		}

		// Store all bicycle types in the cache
		for (BicycleType bicycleType : bicycleTypes) {
			this.bicycleTypeCache.put(bicycleType.getId(), bicycleType);
		}

		// Connect the bicycle types with the respective parents
		for (BicycleType bicycleType : bicycleTypes) {
			Integer superTypeId = bicycleType.getSuperTypeId();
			if (superTypeId != null) {
				BicycleType parent = this.bicycleTypeCache.get(superTypeId);
				parent.addSubType(bicycleType);
			}
		}
	}

	/**
	 * 
	 */
	protected void readBicycleTypes(Collection<BicycleType> result, PreparedStatement stmt) throws SQLException, InvalidPersistentObjectException {
		SysLog.logQuery(stmt);
		ResultSet rset = stmt.executeQuery();
		while (rset.next()) {
			BicycleType obj = new BicycleType(rset);
			result.add(obj);
		}
	}
}