package org.wahlzeit.model;

import org.wahlzeit.services.*;

/**
 * A spheric coordinate represents a position which is defined by a radial distance, a polar angle and an azimuthal angle.
 */
public class SphericCoordinate implements Coordinate {
	/**
	 * Radial distance
	 */
	private double radius;

	/**
	 * Polar angle
	 */
	private double theta;

	/**
	 * Azimuthal angle
	 */
	private double phi;

	public SphericCoordinate(double radius, double theta, double phi) {
		this.radius = radius;
		this.theta = theta;
		this.phi = phi;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public double getRadius() {
		return this.radius;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public double getTheta() {
		return this.theta;
	}

	/**
	 * 
	 * @methodtype get
	 */
	public double getPhi() {
		return this.phi;
	}

	public CartesianCoordinate asCartesianCoordinate() {
		if (this.radius == 0) {
			return new CartesianCoordinate(0, 0, 0);
		}

		double x = this.radius * Math.sin(this.phi) * Math.cos(this.theta);
		double y = this.radius * Math.sin(this.phi) * Math.sin(this.theta);
		double z = this.radius * Math.cos(this.phi);
		return new CartesianCoordinate(x, y, z);
	}

	public double getCartesianDistance(Coordinate other) {
		CartesianCoordinate thisCartesian = this.asCartesianCoordinate();
		return thisCartesian.getCartesianDistance(other);
	}

	public SphericCoordinate asSphericCoordinate() {
		return new SphericCoordinate(this.radius, this.theta, this.phi);
	}

	public double getCentralAngle(Coordinate other) {
		SphericCoordinate otherSpheric = other.asSphericCoordinate();
		return Math.acos(Math.sin(this.phi) * Math.sin(otherSpheric.phi) + Math.cos(this.phi) * Math.cos(otherSpheric.phi) * Math.cos(this.theta - otherSpheric.theta));
	}

	public boolean isEqual(Coordinate other) {
		SphericCoordinate otherSpheric = other.asSphericCoordinate();
		return this.isEqual(otherSpheric);
	}

	private boolean isEqual(SphericCoordinate other) {
		final double EPSILON = 0.0001;
		double diffRadius = Math.abs(this.radius - other.radius);
		double diffTheta = Math.abs(this.theta - other.theta);
		double diffPhi = Math.abs(this.phi - other.phi);

		return diffRadius < EPSILON && diffTheta < EPSILON && diffPhi < EPSILON;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof Coordinate)) { 
			return false;
		}

		Coordinate other = (Coordinate) obj;
		return this.isEqual(other);
	}
}