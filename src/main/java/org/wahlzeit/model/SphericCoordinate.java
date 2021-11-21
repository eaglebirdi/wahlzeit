package org.wahlzeit.model;

import java.util.*;

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

	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		if (this.radius == 0) {
			return new CartesianCoordinate(0, 0, 0);
		}

		double x = this.radius * Math.sin(this.phi) * Math.cos(this.theta);
		double y = this.radius * Math.sin(this.phi) * Math.sin(this.theta);
		double z = this.radius * Math.cos(this.phi);
		return new CartesianCoordinate(x, y, z);
	}

	@Override
	public SphericCoordinate asSphericCoordinate() {
		return new SphericCoordinate(this.radius, this.theta, this.phi);
	}

	@Override
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

	public double getAngleTo(SphericCoordinate other) {
		return Math.acos(Math.sin(this.phi) * Math.sin(other.phi) + Math.cos(this.phi) * Math.cos(other.phi) * Math.cos(this.theta - other.theta));
	} 

	@Override
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

	@Override
	public int hashCode() {
		return Objects.hash(this.radius, this.theta, this.phi);
	}
}