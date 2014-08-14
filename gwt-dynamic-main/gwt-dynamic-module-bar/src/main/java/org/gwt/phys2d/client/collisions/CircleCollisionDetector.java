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
package org.gwt.phys2d.client.collisions;

import static gwt.g2d.client.math.MathHelper.square;
import static org.gwt.phys2d.client.gameplay.sprites.Polygon.NO_CIRCLE;
import static org.gwt.phys2d.client.utils.VectorTools.*;
import gwt.g2d.client.math.Circle;
import gwt.g2d.client.math.Vector2;

import java.util.List;

import org.gwt.phys2d.client.gameplay.GameScene;
import org.gwt.phys2d.client.gameplay.sprites.Polygon;
import org.gwt.phys2d.client.gameplay.sprites.Sprite;
import org.gwt.phys2d.client.math.Line;

public class CircleCollisionDetector extends AbstractCollisionDetector {
	
	@Override
	protected boolean testForCollisions(Sprite sprite1, Sprite sprite2) {
		Circle c1 = sprite1.getCollidePolygon().getBestFitCircle();
		Circle c2 = sprite2.getCollidePolygon().getBestFitCircle();
		if (NO_CIRCLE.equals(c1) || NO_CIRCLE.equals(c2))
			return false;
		if (c1.getCenter().distanceSquared(c2.getCenter()) > square(c1.getRadius() + c2.getRadius()))
			return false;
		//if circles are intersected, check if they are approach each other
		return areRaysOncoming(
				new Line(c1.getCenter(), c1.getCenter().add(sprite1.getVelocity())),
				new Line(c2.getCenter(), c2.getCenter().add(sprite2.getVelocity())));
	}

	@Override
	protected boolean testForCollisions(Sprite sprite, GameScene gameScene) {
		boolean result = false;
		Circle c = sprite.getCollidePolygon().getBestFitCircle();
		if (NO_CIRCLE.equals(c))
			return result;
		Polygon bounds = gameScene.getBounds();
		for (Line edge : bounds.getEdges()) {
			double d = edge.getDistance(c.getCenter());
			if (d <= c.getRadius()) {
				//if circle intersects edge, check if it continues moving outside
				Vector2 eProj = edge.getNormal();
				Vector2 vProj = eProj.scale(sprite.getVelocity().dot(eProj)).mutableNormalize();
				if (!areVectorsEqual(eProj, vProj)) {
					putCollidedEdge(sprite, edge);
					result = true;
				}
			}
		}
		return result;
	}
	
	@Override
	public void computeCollision(Sprite sprite1, Sprite sprite2) {
		//normalized difference vector between circles at time of collision
		Vector2 collisionNormal = sprite2.getPosition().subtract(sprite1.getPosition()).mutableNormalize();
		
		//relative velocity of circles
		Vector2 relativeVelocity = sprite1.getVelocity().subtract(sprite2.getVelocity());
		
		//coefficient of restitution range of 0.0 - 1.0
		double restitution = Math.sqrt(sprite1.getElasticity() * sprite2.getElasticity());
		
		//the impulse created at collision:
		//this is given by the following equation (j is used to represent impulse)
		//j = -(1 + e)vAB . n / n . n(1 / mA + 1 / mB)
		//where e is the coefficient of restitution, vAB is the relative velocity
		//n is the collision normal and mA/mB are the respective masses of the different circles
		//"."s represent the vector dot product
		double impulse = collisionNormal.dot(relativeVelocity.scale(-(1 + restitution)))
				/ collisionNormal.dot(collisionNormal.scale(1 / sprite1.getWeight() + 1 / sprite2.getWeight()));
		
		//scale each impulse along the collision normal by the circles' respective masses
		//and correct sprite's velocities
		sprite1.getVelocity().mutableAdd(collisionNormal.scale(impulse / sprite1.getWeight()));
		sprite2.getVelocity().mutableAdd(collisionNormal.scale(-impulse / sprite2.getWeight()));
		
		//adding repulsion vectors for intersected circles if any
		double repulsion = 
				(sprite1.getCollidePolygon().getBestFitCircle().getRadius()
				+ sprite2.getCollidePolygon().getBestFitCircle().getRadius()
				- sprite1.getPosition().distance(sprite2.getPosition())) * restitution;
		if (repulsion > EPSILON) {
			sprite1.getVelocity().mutableAdd(collisionNormal.scale(-repulsion / sprite1.getWeight()));
			sprite2.getVelocity().mutableAdd(collisionNormal.scale(repulsion / sprite2.getWeight()));
		}
	}

	@Override
	public void computeCollision(Sprite sprite, GameScene gameScene) {
		List<Line> lines = getCollidedEdges(sprite);
		if (lines != null)
			for (Line line : lines) {
				//the same algorithm as above for 2 sprites 
				Vector2 n = line.getNormal();
				double impulse = n.dot(sprite.getVelocity().scale(-(1 + sprite.getElasticity())))
						/ n.dot(n.scale(1 / sprite.getWeight()));
				sprite.getVelocity().mutableAdd(n.scale(impulse / sprite.getWeight()));
			}
	}
}
