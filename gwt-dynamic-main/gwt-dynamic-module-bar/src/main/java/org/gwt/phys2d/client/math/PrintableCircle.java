/*
 * Copyright 2014 Maxim Dominichenko
 * 
 * Licensed under The MIT License (MIT) (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * https://github.com/domax/gwt-dynamic-plugins/blob/master/LICENSE
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gwt.phys2d.client.math;

import static java.lang.Math.abs;
import static org.gwt.phys2d.client.utils.VectorTools.EPSILON;
import static org.gwt.phys2d.client.utils.VectorTools.printVector;

import org.gwt.phys2d.client.utils.VectorTools;

import gwt.g2d.client.math.Circle;
import gwt.g2d.client.math.Vector2;

/**
 * Class that implements {@link Object#toString()} and {@link Object#equals(Object)} methods 
 * for {@link Circle} class.<br/>
 * This class will be partially deprecated as soon as issue with absent 
 * {@code toString()} and {@code equals()} methods in native {@link Circle} class will be fixed.<br/>
 * In addition, this class implements logic to build circle using 2 or 3 points: see constructors
 * {@link #PrintableCircle(Vector2, Vector2)} and {@link #PrintableCircle(Vector2, Vector2, Vector2)}.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class PrintableCircle extends Circle {

	private static final long serialVersionUID = -8406325481883407302L;

	public PrintableCircle(Circle circle) {
		super(circle);
	}

	public PrintableCircle(double centerX, double centerY, double radius) {
		super(centerX, centerY, radius);
	}

	public PrintableCircle(Vector2 center, double radius) {
		super(PrintableVector2.valueOf(center), radius);
	}

	/**
	 * Creates circle from 2 points.<br/>
	 * Just used 2 points as diameter of circle.
	 * 
	 * @param v1 First point of circle
	 * @param v2 Second point of circle
	 */
	public PrintableCircle(Vector2 v1, Vector2 v2) {
		super(new PrintableVector2(), 0);
		Vector2 vMin = v1.min(v2);
		Vector2 vMax = v1.max(v2);
		getCenter().setX(vMin.getX() + (vMax.getX() - vMin.getX()) / 2);
		getCenter().setY(vMin.getY() + (vMax.getY() - vMin.getY()) / 2);
		setRadius(vMin.distance(vMax) / 2);
	}
	
	/**
	 * Creates circle from 3 points.<br/>
	 * Used algorithm described <a href="http://paulbourke.net/geometry/circlefrom3/">here</a>.
	 * 
	 * @param v1 First point of circle
	 * @param v2 Second point of circle
	 * @param v3 Third point of circle
	 * @throws IllegalArgumentException if given vertices are collinear or they are perpendicular to axis
	 */
	public PrintableCircle(Vector2 v1, Vector2 v2, Vector2 v3) throws IllegalArgumentException {
		super(new PrintableVector2(), 0);
		if (!arePerpendicular(v1, v2, v3))
			calculateCircle(v1, v2, v3);	
		else if (!arePerpendicular(v1, v3, v2))
			calculateCircle(v1, v3, v2);	
		else if (!arePerpendicular(v2, v1, v3))
			calculateCircle(v2, v1, v3);	
		else if (!arePerpendicular(v2, v3, v1))
			calculateCircle(v2, v3, v1);	
		else if (!arePerpendicular(v3, v2, v1))
			calculateCircle(v3, v2, v1);	
		else if (!arePerpendicular(v3, v1, v2))
			calculateCircle(v3, v1, v2);	
		else
			throw new IllegalArgumentException("The three vertices are perpendicular to axis: "
					+ printVector(v1) + ", " + printVector(v2) + ", " + printVector(v3) + ".");
	}
	
	/**
	 * Adopts given circle into printable implementation.<br/>
	 * <em>Caution</em>: You have to use explicit vector creation if you want
	 * to have guaranteed new instance of {@link PrintableCircle}.
	 * 
	 * @param circle An instance of {@link Circle} that should be adopted.
	 * @return new instance of {@link PrintableCircle} if given circle isn't instance of it.
	 *         The same instance of given circle if it is {@link PrintableCircle}.
	 *         {@code null} if given circle is {@code null}.
	 */
	public static PrintableCircle valueOf(Circle circle) {
		return circle == null ? null 
				: circle instanceof PrintableCircle ? (PrintableCircle) circle
				: new PrintableCircle(circle);
	}
	
	@Override
	public String toString() {
		return VectorTools.printCircle(this);
	}
	
	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Circle ? equals((Circle) o) : false;
	}
	
//	public boolean equals(Circle circle) {
//		return circle != null
//				&& abs(getCenterX() - circle.getCenterX()) <= EPSILON 
//				&& abs(getCenterY() - circle.getCenterY()) <= EPSILON
//				&& abs(getRadius() - circle.getRadius()) <= EPSILON;
//	}
	public boolean equals(Circle circle) {
		return circle != null
				&& getCenterX() == circle.getCenterX() 
				&& getCenterY() == circle.getCenterY()
				&& getRadius() == circle.getRadius();
	}

	/**
	 * Check the given vertices are perpendicular to X or Y axis.
	 * 
	 * @param v1 First vertex of angle
	 * @param v2 Second vertex of angle
	 * @param v3 Third vertex of angle
	 * @return {@code true} if given vertices are perpendicular, {@code false} otherwise
	 */
	private boolean arePerpendicular(Vector2 v1, Vector2 v2, Vector2 v3) {
		double dYA = abs(v2.getY() - v1.getY());
		double dXA = abs(v2.getX() - v1.getX());
		double dYB = abs(v3.getY() - v2.getY());
		double dXB = abs(v3.getX() - v2.getX());
		// Checking whether the line of the two vertices are vertical.
		if (dXA <= EPSILON && dYB <= EPSILON)
			return false;
		// Checking whether the line of the two vertices are perpendicular to x or y axis.
		return dYA <= EPSILON || dYB <= EPSILON || dXA <= EPSILON || dXB <= EPSILON;
	}

	private void calculateCircle(Vector2 v1, Vector2 v2, Vector2 v3) throws IllegalArgumentException {
		double dYA = v2.getY() - v1.getY();
		double dXA = v2.getX() - v1.getX();
		double dYB = v3.getY() - v2.getY();
		double dXB = v3.getX() - v2.getX();
		
		if (abs(dXA) <= EPSILON && abs(dYB) <= EPSILON) {
			getCenter().setX(0.5 * (v2.getX() + v3.getX()));
			getCenter().setY(0.5 * (v1.getY() + v2.getY()));
			setRadius(getCenter().distance(v1));
			return;
		}
		
		// arePerpendicular() assure that deltas are not zero
		double aSlope = dYA / dXA; 
		double bSlope = dYB / dXB;
		// checking whether the given points are collinear.
		if (abs(aSlope - bSlope) <= EPSILON)
			throw new IllegalArgumentException("Given points are collinear: "
					+ printVector(v1) + ", " + printVector(v2) + ", " + printVector(v3) + ".");

		getCenter().setX((aSlope * bSlope * (v1.getY() - v3.getY()) + bSlope * (v1.getX() + v2.getX())
				- aSlope * (v2.getX() + v3.getX())) / (2 * (bSlope - aSlope)));
		getCenter().setY(-1 * (getCenter().getX() - (v1.getX() + v2.getX()) / 2) / aSlope
				+ (v1.getY() + v2.getY()) / 2);
		setRadius(getCenter().distance(v1));
	}
}
