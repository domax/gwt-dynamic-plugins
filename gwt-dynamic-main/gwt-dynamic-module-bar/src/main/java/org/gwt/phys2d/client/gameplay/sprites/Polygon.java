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
package org.gwt.phys2d.client.gameplay.sprites;

import static org.gwt.phys2d.client.utils.VectorTools.*;
import gwt.g2d.client.math.Circle;
import gwt.g2d.client.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.gwt.phys2d.client.math.Line;
import org.gwt.phys2d.client.math.PrintableCircle;
import org.gwt.phys2d.client.utils.EventedArrayList;
import org.gwt.phys2d.client.utils.ImmutableArrayList;
import org.gwt.phys2d.client.utils.ListHandler;
import org.gwt.phys2d.client.utils.Utils;

/**
 * Container for vertices and computable edges, that represents a polygon.<br/>
 * Normals are computed from edges by rotating them to 90&#xB0; counterclockwise,
 * so if you want to have polygon with normals that point outside, you
 * have to build polygon by specifying vertices clockwise, e.g. square:
 * <pre><code>
 * import {@link gwt.g2d.client.math.Vector2};
 * ...
 * new Polygon({@link Vector2#ZERO}, {@link Vector2#UNIT_Y}, {@link Vector2#ONE}, {@link Vector2#UNIT_X});
 * </code></pre>
 * will build a polygon with 4 edges:
 * <ol>
 * <li><code>UNIT_Y</code></li>
 * <li><code>UNIT_X</code></li>
 * <li><code>UNIT_Y.negate()</code></li>
 * <li><code>UNIT_X.negate()</code></li>
 * </ol>
 * and with normals:
 * <ol>
 * <li><code>UNIT_X.negate()</code></li>
 * <li><code>UNIT_Y</code></li>
 * <li><code>UNIT_X</code></li>
 * <li><code>UNIT_Y.negate()</code></li>
 * </ol>
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class Polygon {
	
	/**
	 * Just an empty circle: x=0, y=0 and r=0.
	 */
	public static final Circle NO_CIRCLE = new PrintableCircle(0, 0, 0);
	
	private static final ImmutableArrayList<Line> NO_EDGES =
		new ImmutableArrayList<Line>(new ArrayList<Line>(0));
	
	private EventedArrayList<Vector2> vertices;
	private ImmutableArrayList<Line> edges;
	private Circle bestFitCircle;
	
	/**
	 * An empty default constructor. Creates a polygon with no vertices and edges.
	 */
	public Polygon() {
		vertices = new EventedArrayList<Vector2>(8);
		edges = NO_EDGES;
		bestFitCircle = NO_CIRCLE;
		vertices.addEventHandler(new VertexChaghingHandler<Vector2>());
	}

	/**
	 * Constructor that creates polygon with specified vertices at once. 
	 * 
	 * @param vertices Array of vertices to be added into polygon.
	 * @see Polygon#addVertices(Vector2...)
	 */
	public Polygon(Vector2... vertices) {
		this();
		addVertices(vertices);
	}

	/**
	 * Gets a list with vertices of polygon.
	 * Vertices changing is followed by automatic recomputing of edges. 
	 * 
	 * @return list of vertices. An Empty list if there are no vertices.
	 */
	public List<Vector2> getVertices() {
		return vertices;
	}
	
	/**
	 * Helper method that simplifies adding vertices into empty (well, not only into empty) polygon.
	 * 
	 * @param vertices Array of vertices to be added into polygon.
	 */
	public void addVertices(Vector2... vertices) {
		this.vertices.addAll(Arrays.asList(vertices));
	}
	
	/**
	 * Gets an <em>immutable</em> list of edges.<br/>
	 * Edges are re-computed automatically when vertices are changed.
	 * 
	 * @return list of edges. An Empty list if there are no computed edges.
	 */
	public List<Line> getEdges() {
		if (edges == null)
			buildEdges();
		return edges;
	}
	
	/**
	 * Gets a flag if this polygon could be used as a collider.<br/>
	 * At least 2 vertices must be defined to make polygon be collidable.
	 * 
	 * @return {@code true} if this polygon is collidable, {@code false} otherwise.
	 */
	public boolean isCollidable() {
		return vertices.size() > 1;
	}

	/**
	 * Gets a circle that is best fits to cover all the vertices in polygon.
	 * 
	 * @return An auto-computed {@link Circle} instance. A {@link #NO_CIRCLE} if proper circle cannot be computed.  
	 */
	public Circle getBestFitCircle() {
		if (bestFitCircle == null)
			buildBestFitCircle();
		return bestFitCircle;
	}

	/**
	 * Forces refreshing inner structures: edges, normals, etc.
	 */
	public void refresh() {
		edges = null;
		bestFitCircle = null;
	}
	
	private void buildEdges() {
		if (isCollidable()) {
			ArrayList<Line> result = new ArrayList<Line>(vertices.size());
			edges = null;
			int i = 0;
			for (Vector2 vertex1 : vertices) {
				Vector2 vertex2 = i + 1 >= vertices.size() ? vertex2 = vertices.get(0) : vertices.get(i + 1);
				result.add(new Line(vertex1, vertex2));
				i++;
			}
			edges = new ImmutableArrayList<Line>(result);
		}
		if (edges == null)
			edges = NO_EDGES;
	}
	
	private void buildBestFitCircle() {
		// 0. The cases with 0, 1, 2 or 3 vertices are very simple, let's process them.
		if (vertices.size() < 2)
			bestFitCircle = NO_CIRCLE;
		else if (vertices.size() == 2)
			bestFitCircle = new PrintableCircle(vertices.get(0), vertices.get(1));
		else if (vertices.size() == 3)
			bestFitCircle = new PrintableCircle(vertices.get(0), vertices.get(1), vertices.get(2));
		else {
			// 1. Here we have more than 3 vertices in polygon. 
			//    First of all we have to compute all the distances between vertices.
			ArrayList<VerticesDistance> verticesDistances = new ArrayList<VerticesDistance>(vertices.size());
			for (int i = 0; i < vertices.size() - 1; i++) {
				Vector2 vertex1 = vertices.get(i);
				for (int j = i + 1; j < vertices.size(); j++) {
					Vector2 vertex2 = vertices.get(j);
					verticesDistances.add(new VerticesDistance(vertex1, vertex2));
				}
			}
			// 2. Sort distances in descendant order 
			//    (see {@link VerticesDistance#compareTo(VerticesDistance)}),
			//    and take 2 longest distances - their vertices should be a basis
			//    for circle we're computing.
			Collections.sort(verticesDistances);
			VerticesDistance vd1 = verticesDistances.get(0);
			VerticesDistance vd2 = verticesDistances.get(1);
			// 3. We have to find 3 vertices most remote from each other.
			//    Now we have 2 longest distances in the beginning of "verticesDistances": vd1, vd2.
			//    These 2 distances may have shared vertices.
			//    I proceed from assumption that "vertices" list has no duplicated vertices.
			//    So, then we may have 3 or 4 different vertices.
			//    Let's process cases when found distances have 1 common vertex.
			if (vd1.vertex1.equals(vd2.vertex1) || vd1.vertex2.equals(vd2.vertex1))
				bestFitCircle = new PrintableCircle(vd1.vertex1, vd1.vertex2, vd2.vertex2);
			else if (vd1.vertex1.equals(vd2.vertex2) || vd1.vertex2.equals(vd2.vertex2))
				bestFitCircle = new PrintableCircle(vd1.vertex1, vd1.vertex2, vd2.vertex1);
			else {
				// 4. Here we have a case that 2 distances have no shared vertices.
				//    Thus, we have to select one vertex that should be rejected.
				//    First of all, we have to leave both vertices of vd1 - because it's longest.
				//    The candidate to be rejected should be a vertex of vd2 that have shortest sum
				//    of distances to vertices of vd1.
				double d1 = vd2.vertex1.distance(vd1.vertex1) + vd2.vertex1.distance(vd1.vertex2);
				double d2 = vd2.vertex2.distance(vd1.vertex1) + vd2.vertex2.distance(vd1.vertex2);
				if (d1 > d2)
					bestFitCircle = new PrintableCircle(vd1.vertex1, vd1.vertex2, vd2.vertex1);
				else
					bestFitCircle = new PrintableCircle(vd1.vertex1, vd1.vertex2, vd2.vertex2);
			}
			// 5. Now we have to make sure that found circle covers all the vertices.
			//    If it doesn't - we have to correct radius.
			Vector2 c = bestFitCircle.getCenter();
			for (Vector2 v : vertices) {
				double d = c.distance(v);
				if (d > bestFitCircle.getRadius())
					bestFitCircle.setRadius(d);
			}
		}
	}

	/**
	 * Moves polygon by specified X and Y offsets.
	 * This method takes into account specifics of lazy calculations polygon inner structures.
	 * 
	 * @param dx X offset polygon to be moved
	 * @param dy Y offset polygon to be moved
	 */
	public void moveBy(double dx, double dy) {
		if (Math.abs(dx) <= EPSILON) dx = 0.0;
		if (Math.abs(dy) <= EPSILON) dy = 0.0;
		if (Math.abs(dx) > 0.0 || Math.abs(dy) > 0.0) {
			Vector2 dv = new Vector2(dx, dy);
			for (Vector2 vertex : vertices)
				vertex.mutableAdd(dv);
			if (bestFitCircle != null && !NO_CIRCLE.equals(bestFitCircle))
				bestFitCircle.getCenter().mutableAdd(dv);
		}
	}

	/**
	 * Moves polygon by specified X and Y offsets from given {@link Vector2} object.
	 * 
	 * @param offset instance of {@link Vector2} that represents moving offset
	 * @see #moveBy(double, double)
	 */
	public void moveBy(Vector2 offset) {
		moveBy(offset.getX(), offset.getY());
	}
	
	private class VertexChaghingHandler<Vertex2> implements ListHandler<Vertex2> {
		@Override
		public boolean onModifying(List<Vertex2> source, ListHandler.Type type, Collection<? extends Vertex2> items) {
			refresh();
			return true;
		}
	}
	
	private class VerticesDistance implements Comparable<VerticesDistance> {
		
		private Vector2 vertex1, vertex2;
		private double distance;
		
		VerticesDistance(Vector2 vertex1, Vector2 vertex2) {
			this.vertex1 = vertex1;
			this.vertex2 = vertex2;
			distance = vertex1.distance(vertex2);
		}
		
		@Override
		public int compareTo(VerticesDistance verticesDistance) {
			return (int) Math.signum(verticesDistance.distance - distance);
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder("\n\t");
			builder.append(Utils.getSimpleName(getClass()));
			builder.append(" {vertex1=");
			builder.append(printVector(vertex1));
			builder.append(", vertex2=");
			builder.append(printVector(vertex2));
			builder.append(", distance=");
			builder.append(distance);
			builder.append("}");
			return builder.toString();
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(Utils.getSimpleName(getClass()));
		builder.append(" {vertices=");
		builder.append(vertices);
		builder.append(", edges=");
		builder.append(edges);
		builder.append(", bestFitCircle=");
		builder.append(bestFitCircle);
		builder.append("}");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vertices == null) ? 0 : vertices.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Polygon other = (Polygon) obj;
		if (vertices == null) {
			if (other.vertices != null)
				return false;
		} else if (!vertices.equals(other.vertices))
			return false;
		return true;
	}
}
