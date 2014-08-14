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

import gwt.g2d.client.math.Vector2;

import java.io.Serializable;

import org.gwt.phys2d.client.utils.Utils;
import org.gwt.phys2d.client.utils.VectorTools;

import static java.lang.Math.*;
import static org.gwt.phys2d.client.utils.VectorTools.*;

/**
 * Represents 2D line that is defined by 2 vertices.<br/>
 * Order of vertices is important - it defines line orientation.
 * See {@link VectorTools#distanceToLine(Vector2, Vector2, Vector2)} for details.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 * @see VectorTools#distanceToLine(Vector2, Vector2, Vector2)
 */
public class Line implements Serializable, Comparable<Line> {

	private static final long serialVersionUID = -8000291461462951575L;
	
	private Vector2 vertex1, vertex2, vector, normal;
	private double length;
	
	/**
	 * Default empty constructor that creates undefined line - both vertices have zero coordinates.
	 */
	public Line() {
		vertex1 = new PrintableVector2(0);
		vertex2 = new PrintableVector2(0);
		vector = new PrintableVector2(0);
		normal = new PrintableVector2(0);
		length = 0;
	}
	
	/**
	 * Constructor that creates new line instance with vertices that are copied from given line.
	 * 
	 * @param line A {@link Line} instance which vertex copies are used to create new line.
	 */
	public Line(Line line) {
		vertex1 = new PrintableVector2(line.getVertex1());
		vertex2 = new PrintableVector2(line.getVertex2());
		update();
	}
	
	/**
	 * Constructor that creates line using specified vertices.<br/>
	 * If some of input vertices will be {@code null} then zero vertex will be created instead.
	 * 
	 * @param vertex1 First vertex of line (start of vector)
	 * @param vertex2 Second vertex of line (end of vector)
	 */
	public Line(Vector2 vertex1, Vector2 vertex2) {
		this.vertex1 = vertex1 == null ? new PrintableVector2(0) : vertex1;
		this.vertex2 = vertex2 == null ? new PrintableVector2(0) : vertex2;
		update();
	}

	/**
	 * Gets instance of first vertex of line
	 * 
	 * @return A {@link Vector2} instance of first vertex of line
	 */
	public Vector2 getVertex1() {
		return vertex1;
	}

	/**
	 * Gets instance of second vertex of line
	 * 
	 * @return A {@link Vector2} instance of second vertex of line
	 */
	public Vector2 getVertex2() {
		return vertex2;
	}

	/**
	 * Gets computed instance of vector that is defined by vertices.<br>
	 * Computed vector is directed from vertex1 to vertex2.
	 * 
	 * @return A {@link Vector2} instance of line direction
	 * @see #update()
	 */
	public Vector2 getVector() {
		return vector;
	}

	/**
	 * Gets computed instance of normal that is produced from line's vector.<br/>
	 * Normal is computed as {@link #getVector()}, rotated to 90&#xB0; counterclockwise and normalized.
	 * 
	 * @return normal to oriented line
	 * @see #update()
	 */
	public Vector2 getNormal() {
		return normal;
	}
	
	/**
	 * Gets computed length of line.
	 * 
	 * @return length of line.
	 * @see #update()
	 */
	public double getLength() {
		return length;
	}

	/**
	 * Refreshes computed properties (like vector, normal or length) 
	 * in case if coordinates of vertices was changed.
	 */
	public void update() {
		vector = new PrintableVector2(vertex2).mutableSubtract(vertex1);
		normal = new PrintableVector2(-vector.getY(), vector.getX()).mutableNormalize();
		if (Double.isNaN(normal.getX()) || Double.isNaN(normal.getY()))
			normal = new PrintableVector2(0);
		length = vertex1.distance(vertex2);
	}

	/**
	 * Returns shortest distance from given point to this line.<br/>
	 * Uses {@link VectorTools#distanceToLine(Vector2, Vector2, Vector2)} method.
	 * 
	 * @param point A {@link Vector2} instance that defines point where from you want to compute distance.
	 * @return Positive value of distance if given point and line's normal at the same side. 
	 *         Negative value otherwise. {@code 0} value if point is in the line.
	 * @see VectorTools#distanceToLine(Vector2, Vector2, Vector2)
	 */
	public double getDistance(Vector2 point) {
		return distanceToLine(point, vertex1, vertex2);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(Utils.getSimpleName(getClass()));
		result.append(" {vertex1=");
		result.append(printVector(vertex1));
		result.append(", vertex2=");
		result.append(printVector(vertex2));
		result.append(", vector=");
		result.append(vector);
		result.append(", normal=");
		result.append(normal);
		result.append(", length=");
		result.append(length);
		result.append("}");
		return result.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vertex1 == null) ? 0 : vertex1.hashCode());
		result = prime * result + ((vertex2 == null) ? 0 : vertex2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Line other = (Line) obj;
		if (vertex1 == null) {
			if (other.vertex1 != null)
				return false;
		} else if (!vertex1.equals(other.vertex1))
			return false;
		if (vertex2 == null) {
			if (other.vertex2 != null)
				return false;
		} else if (!vertex2.equals(other.vertex2))
			return false;
		return true;
	}

	@Override
	public int compareTo(Line line) {
		if (line == null) return 1;
		int dX1 = (int) round(signum(vertex1.getX() - line.vertex1.getX()));
		int dY1 = (int) round(signum(vertex1.getY() - line.vertex1.getY()));
		int dX2 = (int) round(signum(vertex2.getX() - line.vertex2.getX()));
		int dY2 = (int) round(signum(vertex2.getY() - line.vertex2.getY()));
		return dX1 * 8 + dY1 * 4 + dX2 * 2 + dY2;
	}
}
