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

import java.util.List;

import org.gwt.phys2d.client.gameplay.GameScene;
import org.gwt.phys2d.client.gameplay.sprites.Sprite;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Interface that describes properties and actions for any collision detector.
 * Each collision detector may follow its own rules, assumptions and simplifications of world modeling.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public interface CollisionDetector {

	/**
	 * Checks for all the collisions between sprites and tests that sprites intersected world bounds.
	 * It's only test, no collision consequences computing here.<br/>
	 * This method is just for determine which sprites properties should be re-computed because collision.<br/>
	 * All the detected collisions should be followed by firing corresponding event, 
	 * registered via {@link #addCollisionHandler(CollisionHandler)}.
	 * 
	 * @param sprites List of sprites that may collide between each other. 
	 * @param gameScene {@link GameScene} instance that holds polygon with world boundaries.
	 */
	void testForCollisions(List<Sprite> sprites, GameScene gameScene);

	/**
	 * Method should change {@link Sprite}'s fields according to their properties
	 * (position, velocity, weight, shape, etc) when two sprites collide with each other.<br/>
	 * Since it's completely gameplay's business to decide whether change sprite
	 * properties or not, this method should be invoked (if any) from collision handler's
	 * event {@link CollisionHandler#onCollide(CollisionDetector, Sprite, Sprite)}
	 * registered by {@link #addCollisionHandler(CollisionHandler)}.
	 * 
	 * @param sprite1 First sprite that collided with second one.
	 * @param sprite2 Second sprite that collided with first one.
	 */
	void computeCollision(Sprite sprite1, Sprite sprite2);
	
	/**
	 * Method should change {@link Sprite}'s fields according to their properties
	 * (position, velocity, weight, shape, etc) when sprite reaches world bounds.<br/>
	 * Since it's completely gameplay's business to decide whether change sprite
	 * properties or not, this method should be invoked (if any) from collision handler's
	 * event {@link CollisionHandler#onBeyondWorld(CollisionDetector, List)}
	 * registered by {@link #addCollisionHandler(CollisionHandler)}.
	 * 
	 * @param sprite sprite that collided with world bounds.
	 * @param gameScene {@link GameScene} instance that holds polygon with world boundaries.
	 */
	void computeCollision(Sprite sprite, GameScene gameScene);

	/**
	 * Should add specified collision handler into list of active handlers.<br/>
	 * Collision handlers registered here are fired by {@link #testForCollisions(List, GameScene)} method.
	 * 
	 * @param collisionHandler A {@link CollisionHandler} instance that encapsulates logic for processing collision.
	 * @return {@link HandlerRegistration} ticket that may be used for removing this handler.
	 */
	HandlerRegistration addCollisionHandler(CollisionHandler collisionHandler);
	
	/**
	 * Should add specified collision notification handler into list of active handlers for given sprite.<br/>
	 * Collision handlers registered here are fired by {@link #testForCollisions(List, GameScene)} method.
	 * 
	 * @param collisionNotification A {@link CollisionNotification} instance 
	 *        that encapsulates logic for processing collision.
	 * @param sprite {@link Sprite} object instance this registration is performed for.
	 * @return {@link HandlerRegistration} ticket that may be used for removing this handler.
	 */
	HandlerRegistration addCollisionNotification(CollisionNotification<Sprite> collisionNotification, Sprite sprite);
	
	/**
	 * Gets a value of accuracy level.<br/>
	 * Accuracy should be positive non-zero. Default value is 1. 
	 * Higher value increases accuracy but slows calculations.<br/>
	 * Heuristic formula:<br/>
	 * <code>
	 * accuracy = {@link GameScene#getColliders()}.size() / 4;
	 * </code>
	 * 
	 * @param accuracy value of accuracy level
	 * @see #getAccuracy()
	 */
	public void setAccuracy(int accuracy);
	
	/**
	 * Gets a value of accuracy level.<br/>
	 * Accuracy should be positive non-zero. Default value is 1. 
	 * Higher value increases accuracy but slows calculations.
	 *  
	 * @return value of accuracy level
	 * @see #setAccuracy(int)
	 */
	public int getAccuracy();
}
