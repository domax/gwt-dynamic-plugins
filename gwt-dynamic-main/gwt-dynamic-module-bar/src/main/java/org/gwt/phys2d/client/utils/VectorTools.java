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
package org.gwt.phys2d.client.utils;

import org.gwt.phys2d.client.math.Line;
import org.gwt.phys2d.client.math.PrintableVector2;

import gwt.g2d.client.math.Circle;
import gwt.g2d.client.math.Vector2;
import static java.lang.Math.abs;

/**
 * This class is just a holder for several useful static functions
 * that extend functionality of {@link Vector2} class.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class VectorTools {
	
	/**
	 * A maximal difference between double values to consider them as equal.
	 */
	public static final double EPSILON = 1.0E-7;
	public static final PrintableVector2 P_ONE = PrintableVector2.valueOf(Vector2.ONE);
	public static final PrintableVector2 P_UNIT_X = PrintableVector2.valueOf(Vector2.UNIT_X);
	public static final PrintableVector2 P_UNIT_Y = PrintableVector2.valueOf(Vector2.UNIT_Y);
	public static final PrintableVector2 P_ZERO = PrintableVector2.valueOf(Vector2.ZERO);

	/**
	 * Gets textual representation of {@link Vector2} instance.
	 * 
	 * @param vector A {@link Vector2} instance that should be converted into text representation
	 * @return Text representation of given vector. String representation of {@code null} if vector is {@code null}.
	 */
	public static String printVector(Vector2 vector) {
		if (vector == null)
			return String.valueOf(null);
		StringBuilder builder = new StringBuilder();
		builder.append(Utils.getSimpleName(vector.getClass()));
		builder.append(" {X=");
		builder.append(vector.getX());
		builder.append(", Y=");
		builder.append(vector.getY());
		builder.append("}");
		return builder.toString();
	}

	/**
	 * Rounds vector's X and Y properties by given precision.<br/>
	 * The formula that is used to round values is following:
	 * <pre><code>newValue = Math.round(oldValue * precision) / precision</code></pre>
	 * 
	 * @param vector A {@link Vector2} instance that should be rounded
	 * @param precision A value that must be greater than 0 and multiple of 10
	 * @return A new vector with rounded coordinates
	 * @throws IllegalArgumentException Precision must be greater than 0 and multiple of 10
	 * @see #mutableRoundVector(Vector2, int)
	 */
	public static Vector2 roundVector(Vector2 vector, int precision) {
		return vector == null ? null : mutableRoundVector(new Vector2(vector), precision);
	}

	/**
	 * Rounds vector's X and Y properties by given precision.<br/>
	 * The formula that is used to round values is following:
	 * <pre><code>newValue = Math.round(oldValue * precision) / precision</code></pre>
	 * 
	 * @param vector A {@link Vector2} instance that should be rounded
	 * @param precision A value that must be greater than 0 and multiple of 10
	 * @return A specified vector with rounded coordinates
	 * @throws IllegalArgumentException Precision must be greater than 0 and multiple of 10
	 * @see #roundVector(Vector2, int)
	 */
	public static Vector2 mutableRoundVector(Vector2 vector, int precision) {
		if (vector == null)
			return null;
		if (precision <= 0 || precision % 10 != 0)
			throw new IllegalArgumentException("Precision must be greater than 0 and multiple of 10");
		vector.set((double) Math.round(vector.getX() * precision) / precision, 
				(double) Math.round(vector.getY() * precision) / precision);
		return vector;
	}
	
	/**
	 * Gets textual representation of {@link Circle} instance.
	 * 
	 * @param circle A {@link Circle} instance that should be converted into text representation
	 * @return Text representation of given circle. String representation of {@code null} if circle is {@code null}.
	 */
	public static String printCircle(Circle circle) {
		if (circle == null)
			return String.valueOf(null);
		StringBuilder builder = new StringBuilder();
		builder.append(Utils.getSimpleName(circle.getClass()));
		builder.append(" {X=");
		builder.append(circle.getCenterX());
		builder.append(", Y=");
		builder.append(circle.getCenterY());
		builder.append(", R=");
		builder.append(circle.getRadius());
		builder.append("}");
		return builder.toString();
	}
	
	/**
	 * Rounds circle's center and radius by given precision.<br/>
	 * The formula that is used to round values is following:
	 * <pre><code>newValue = Math.round(oldValue * precision) / precision</code></pre>
	 * 
	 * @param circle A {@link Circle} instance that should be rounded
	 * @param precision A value that must be greater than 0 and multiple of 10
	 * @throws IllegalArgumentException Precision must be greater than 0 and multiple of 10
	 * @return A new circle with rounded center coordinates and radius
	 */
	public static Circle roundCircle(Circle circle, int precision) {
		return circle == null ? null : mutableRoundCircle(new Circle(circle), precision);
	}
	
	/**
	 * Rounds circle's center and radius by given precision.<br/>
	 * The formula that is used to round values is following:
	 * <pre><code>newValue = Math.round(oldValue * precision) / precision</code></pre>
	 * 
	 * @param circle A {@link Circle} instance that should be rounded
	 * @param precision A value that must be greater than 0 and multiple of 10
	 * @throws IllegalArgumentException Precision must be greater than 0 and multiple of 10
	 * @return A specified circle with rounded center coordinates and radius
	 */
	public static Circle mutableRoundCircle(Circle circle, int precision) {
		if (circle == null)
			return null;
		if (precision <= 0 || precision % 10 != 0)
			throw new IllegalArgumentException("Precision must be greater than 0 and multiple of 10");
		mutableRoundVector(circle.getCenter(), precision);
		circle.setRadius((double) Math.round(circle.getRadius() * precision) / precision);
		return circle;
	}

	/**
	 * Computes distance from given point to line that is defined by two vertices.<br/>
	 * An order of vertexes is important - it defines line orientation, and as consequence
	 * it affects an orientation of line's normal. Normal of line defines sign of result:
	 * in case if given point is "outside" of line (at the side of line where it's normal points to)
	 * then result will be positive. In case if given point "inside" 
	 * (it is in opposite side where normal points to) then result will be negative.
	 * <pre><code>
	 *        * vertex2
	 *        ^
	 *        | distance
	 *        |----------* point
	 * normal |
	 *      <-* vertex1
	 * </code></pre>
	 * <p>Distance at the picture above will be negative, because normal of line points to
	 * opposite side of line where point is.</p>
	 * 
	 * @param point A {@link Vector2} instance that defines point where from you want to compute distance.
	 * @param vertex1 A {@link Vector2} instance that defines first vertex of line.
	 * @param vertex2 A {@link Vector2} instance that defines second vertex of line.
	 * @return Positive value of distance if given point and line's normal at the same side. 
	 *         Negative value otherwise. {@code 0} value if point is in the line.
	 */
	public static double distanceToLine(Vector2 point, Vector2 vertex1, Vector2 vertex2) {
		if (point == null) point = P_ZERO;
		if (vertex1 == null) vertex1 = P_ZERO;
		if (vertex2 == null) vertex2 = P_ZERO;
		Vector2 v0 = vertex1.subtract(point);
		Vector2 v1 = vertex2.subtract(vertex1);
		double result = (v0.getX() * v1.getY() - v1.getX() * v0.getY()) / v1.length();
		if (abs(result) <= EPSILON) result = 0;
		return result;
	}
	
	/**
	 * Returns new vector as reflection of given one against specified normal.<br/>
	 * <em>Important:</em> {@code normal} must be a vector which length equals to 1.0,
	 * otherwise result of this function will be incorrect.
	 * You can obtain normalized vector by using {@link Vector2#normalize()} or 
	 * {@link Vector2#mutableNormalize()} methods. 
	 * 
	 * @param vector A {@link Vector2} instance that should be mirrored
	 * @param normal A {@link Vector2} instance of normalized vector that is used as axe of mirroring
	 * @return New vector that is mirror of given one against specified normal
	 * @see Vector2#normalize()
	 * @see Vector2#mutableNormalize()
	 */
	public static Vector2 mirrorVector(Vector2 vector, Vector2 normal) {
		return vector == null ? null : mutableMirrorVector(new Vector2(vector), normal);
	}
	
	/**
	 * Changes given vector as reflection against specified normal.<br/>
	 * <em>Important:</em> {@code normal} must be a vector which length equals to 1.0,
	 * otherwise result of this function will be incorrect.
	 * You can obtain normalized vector by using {@link Vector2#normalize()} or 
	 * {@link Vector2#mutableNormalize()} methods. 
	 * 
	 * @param vector A {@link Vector2} instance that should be mirrored
	 * @param normal A {@link Vector2} instance of normalized vector that is used as axe of mirroring
	 * @return Input vector changed as mirror against specified normal
	 * @see Vector2#normalize()
	 * @see Vector2#mutableNormalize()
	 */
	public static Vector2 mutableMirrorVector(Vector2 vector, Vector2 normal) {
		if (normal == null || vector == null)
			return null;
		double dot2 = vector.dot(normal) * 2;
		return vector.mutableSubtract(new Vector2(dot2).mutableMultiply(normal));
	}
	
	/**
	 * Rounds line's vector properties by given precision.<br/>
	 * The formula that is used to round values is following:
	 * <pre><code>newValue = Math.round(oldValue * precision) / precision</code></pre>
	 * 
	 * @param line A {@link Line} instance that should be rounded
	 * @param precision A value that must be greater than 0 and multiple of 10
	 * @return A new line with rounded properties
	 * @throws IllegalArgumentException Precision must be greater than 0 and multiple of 10
	 * @see #mutableRoundLine(Line, int)
	 */
	public static Line roundLine(Line line, int precision) {
		return line == null ? null : mutableRoundLine(new Line(line), precision);
	}

	/**
	 * Rounds line's vector properties by given precision.<br/>
	 * The formula that is used to round values is following:
	 * <pre><code>newValue = Math.round(oldValue * precision) / precision</code></pre>
	 * 
	 * @param line A {@link Line} instance that should be rounded
	 * @param precision A value that must be greater than 0 and multiple of 10
	 * @return A specified line with rounded coordinates
	 * @throws IllegalArgumentException Precision must be greater than 0 and multiple of 10
	 * @see #roundLine(Line, int)
	 */
	public static Line mutableRoundLine(Line line, int precision) {
		if (line == null)
			return null;
		if (precision <= 0 || precision % 10 != 0)
			throw new IllegalArgumentException("Precision must be greater than 0 and multiple of 10");
		mutableRoundVector(line.getVertex1(), precision);
		mutableRoundVector(line.getVertex2(), precision);
		line.update();
		mutableRoundVector(line.getVector(), precision);
		mutableRoundVector(line.getNormal(), precision);
		return line;
	}

	/**
	 * Compares two vectors for equality.<br/>
	 * They are considered equal if difference between their vertex coordinates less than {@link #EPSILON}.
	 * 
	 * @param v1 First vector for comparison
	 * @param v2 Second vector for comparison
	 * @return {@code true} if given vectors are almost identical, {@code false} otherwise.
	 */
	public static boolean areVectorsEqual(Vector2 v1, Vector2 v2) {
		if (v1 == null && v2 == null)
			return true;
		if (v1 == null || v2 == null)
			return false;
		return Math.abs(v1.getX() - v2.getX()) < EPSILON
				&& Math.abs(v1.getY() - v2.getY()) < EPSILON;
	}

	/**
	 * Compares two vectors for collinearity.<br/>
	 * They are considered collinear if they are parallel with deviation not bigger than {@link #EPSILON}.<br/>
	 * This method uses {@link #areVectorsEqual(Vector2, Vector2)} for equality comparison.
	 * 
	 * @param v1 First vector for comparison
	 * @param v2 Second vector for comparison
	 * @return {@code true} if given vectors are almost collinear, {@code false} otherwise.
	 * @see #areVectorsEqual(Vector2, Vector2)
	 */
	public static boolean areVectorsCollinear(Vector2 v1, Vector2 v2) {
		if (v1 == null && v2 == null)
			return true;
		if (v1 == null || v2 == null)
			return false;
		if (v1.length() < EPSILON || v2.length() < EPSILON)
			return true;
		return areVectorsEqual(v1.normalize(), v2.normalize())
				|| areVectorsEqual(v1.normalize(), v2.normalize().mutableNegate());
	}
	
	/**
	 * Checks if specified rays represented as {@link Line} objects 
	 * are approaching each other.
	 * 
	 * @param l1 First ray to be checked for intersection with second one
	 * @param l2 Second ray to be checked for intersection with first one
	 * @return {@code true} if rays are intersected, {@code false} if they're not
	 *         or some of arguments is {@code null}.
	 */
	public static boolean areRaysOncoming(Line l1, Line l2) {
		if (l1 == null || l2 == null)
			return false;
		
		//projection vector for both rays
		Vector2 projVector = l2.getVertex1().subtract(l1.getVertex1()).mutableNormalize();
		
		//project a onto normalized projection vector
		//formula: b * (a . b)
		//where . is dot product, * - scaling
		Vector2 l1Proj = projVector.scale(l1.getVector().dot(projVector));
		Vector2 l2Proj = projVector.scale(l2.getVector().dot(projVector));
		
		if (areVectorsEqual(l1Proj, l2Proj))
			return false;
		
		Vector2 l1ProjN = l1Proj.normalize();
		Vector2 l2ProjN = l2Proj.normalize();
		if (areVectorsEqual(l1ProjN, l2ProjN)) {
			double l1d = l1Proj.lengthSquared();
			double l2d = l2Proj.lengthSquared();
			return areVectorsEqual(l1ProjN, projVector) ? l2d < l1d : l1d < l2d;
		} else
			return areVectorsEqual(l1ProjN, projVector);
	}
}
