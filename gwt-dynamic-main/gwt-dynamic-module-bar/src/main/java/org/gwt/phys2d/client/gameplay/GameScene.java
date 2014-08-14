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
package org.gwt.phys2d.client.gameplay;

import java.util.List;

import org.gwt.phys2d.client.collisions.CollisionDetector;
import org.gwt.phys2d.client.gameplay.sprites.Polygon;
import org.gwt.phys2d.client.gameplay.sprites.Sprite;

/**
 * Interface that represents API for operating with game scene.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public interface GameScene {

	/**
	 * Gets width in screen pixels of game scene.<br/>
	 * Is used to represent rectangular window where game view should be rendered. 
	 * 
	 * @return game scene width
	 * @see #getBounds()
	 */
	public int getWidth();
	
	/**
	 * Gets height in screen pixels of game scene.<br/>
	 * Is used to represent rectangular window where game view should be rendered.
	 * 
	 * @return game scene height
	 * @see #getBounds()
	 */
	public int getHeight();

	/**
	 * Returns {@link Polygon} object that represents world bounds of game.<br/>
	 * It could be equal to rendering rectangular area or bigger/smaller than it -
	 * it depends on different game needs: scene scaling, hidden/background computations, etc.<br/>
	 * Normally, this polygon has normals, oriented inside - it allows to use regular collision detection
	 * algorithms that operate with polygons.
	 * 
	 * @return {@link Polygon} object of world boundaries with edges that have normals directed inside of polygon
	 * @see #getWidth() 
	 * @see #getHeight() 
	 */
	public Polygon getBounds();
	
	/**
	 * Returns list of all sprites that are registered in game.
	 * Keep in mind that all sprites that are in this list, are processed
	 * by game engine, so keep amount of these sprites within reasonable limits. 
	 * 
	 * @return list of all sprites in game
	 * @see #getColliders() 
	 */
	public List<Sprite> getSprites();

	/**
	 * Returns list of sprites that potentially may collate with each other.
	 * This list is subset of one that is returned by {@link #getSprites()}. 
	 * 
	 * @return list of sprites that may collide with each other or with world boundaries.
	 * @see #getSprites()
	 */
	public List<Sprite> getColliders();
	
	/**
	 * Gets effective collision detector that is used for computing
	 * collisions between sprites from {@link #getColliders()} list.
	 * 
	 * @return {@link CollisionDetector} instance
	 * @see #getColliders() 
	 */
	public CollisionDetector getCollisionDetector();
}
