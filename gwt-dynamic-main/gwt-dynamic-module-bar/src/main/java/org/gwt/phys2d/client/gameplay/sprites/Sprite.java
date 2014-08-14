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

import gwt.g2d.client.math.Vector2;

/**
 * Interface for all objects in game that can be moved, painted, collided, etc. 
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public interface Sprite {

	/**
	 * Initializes all sprite resources. This method may be asynchronous.<br/>
	 * Gameplay will not start until all {@link Sprite#init(SpriteInitCallback)}s will be finished.
	 * 
	 * @param callback Contains handler of an event when sprite is ready to use.
	 */
	void init(SpriteInitCallback callback);

	/**
	 * Is invoked in every frame to re-compute sprite's properties.<br/>
	 * <em>Caution</em>: this method must not contain any drawing logic, use {@link #draw()} for that.
	 */
	void update();

	/**
	 * Is invoked (ideally in every frame) to render sprite view in screen.<br/>
	 * Developer has to avoid placing here any computations except visualization.
	 * All animation logic must be placed in {@link #update()} method.
	 */
	void draw();

	/**
	 * Computed property that gets information about sprite visibility.
	 * 
	 * @return {@code true} if sprite should be drawn. {@code false} otherwise.
	 * @see #update()
	 */
	boolean isVisible();

	/**
	 * Property that gets information about sprite's position.
	 * 
	 * @return {@link Vector2} instance that contains current 2D coordinates of sprite's pivot point.
	 * @see #update()
	 */
	Vector2 getPosition();

	/**
	 * Property that gets information about sprite's velocity (if any).
	 * 
	 * @return {@link Vector2} instance that contains current 2D vector of sprite movement.
	 *         Zero-point of this vector is sprite's pivot ({@link #getPosition()})
	 * @see #update()
	 */
	Vector2 getVelocity();
	
	/**
	 * Property that gets information about sprite's weight (if any).<br/>
	 * It is used to compute sprite's animation with physics.
	 * 
	 * @return Conventional weight of sprite - units depend on game.
	 */
	double getWeight();

	/**
	 * Property that gets information about sprite's elasticity.<br/>
	 * 0 is completely inelastic (lump of clay) and 1 is purely elastic (superball).
	 * 
	 * @return Sprite's elasticity.
	 */
	double getElasticity();
	
	/**
	 * Computed property whether this sprite may collide with others or not.
	 * 
	 * @return {@code true} if sprite may collide with others and with world boundary. {@code false} otherwise.
	 * @see #update()
	 */
	boolean isCollidable();
	
	/**
	 * Defines conventional polygon that is used to compute sprite collisions.
	 * 
	 * @return A {@link Polygon} object instance that describes sprite bounds for collision detection
	 * @see #update()
	 * @see Polygon
	 * @see org.gwt.phys2d.client.collisions.CollisionDetector
	 */
	Polygon getCollidePolygon();
}
