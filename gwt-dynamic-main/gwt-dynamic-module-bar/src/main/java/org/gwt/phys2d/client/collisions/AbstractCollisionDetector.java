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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.gwt.phys2d.client.gameplay.GameScene;
import org.gwt.phys2d.client.gameplay.sprites.Sprite;
import org.gwt.phys2d.client.math.Line;

import com.google.gwt.event.shared.HandlerRegistration;

public abstract class AbstractCollisionDetector implements CollisionDetector {
	
	/**
	 * Holds an accuracy level for collision computations.
	 */
	private int accuracy = 1;
	/**
	 * Holds a correspondence between sprite and world edges it touches.
	 */
	private HashMap<Sprite, List<Line>> spriteToBoundsMap = new HashMap<Sprite, List<Line>>();
	/**
	 * Holds all the collision handlers that was registered for this collision detector.
	 */
	protected ArrayList<CollisionHandler> collisionHandlerList = new ArrayList<CollisionHandler>();
	/**
	 * Holds all the collision notification handlers that was registered 
	 * for this collision detector and particular sprites.
	 */
	protected HashMap<Sprite, List<CollisionNotification<Sprite>>> collisionNotificationMap =
		new HashMap<Sprite, List<CollisionNotification<Sprite>>>(); 

	@Override
	public HandlerRegistration addCollisionHandler(CollisionHandler handler) {
		if (handler == null)
			return null;
		if (!collisionHandlerList.contains(handler))
			collisionHandlerList.add(handler);
		return new CollisionHandlerRegistration(this, handler);
	}

	@Override
	public HandlerRegistration addCollisionNotification(CollisionNotification<Sprite> handler, Sprite sprite) {
		if (handler == null)
			return null;
		List<CollisionNotification<Sprite>> handlers = collisionNotificationMap.get(sprite);
		if (handlers == null) {
			handlers = new ArrayList<CollisionNotification<Sprite>>();
			collisionNotificationMap.put(sprite, handlers);
		}
		if (!handlers.contains(handler))
			handlers.add(handler);
		return new CollisionNotificationRegistration(this, handler, sprite);
	}

	/**
	 * Fires all the events that was registered for sprite colliding.
	 * This method isn't invoked by default, it should be invoked in 
	 * {@link CollisionDetector#testForCollisions(List, org.gwt.phys2d.client.gameplay.GameScene)}
	 * of appropriate derivative class.
	 * 
	 * @param sprite1 First sprite that collided with second one.
	 * @param sprite2 Second sprite that collided with first one.
	 */
	protected void fireOnCollideEvents(Sprite sprite1, Sprite sprite2) {
		if (sprite1 == null || sprite2 == null)
			return;
		for (CollisionHandler handler : collisionHandlerList)
			handler.onCollide(this, sprite1, sprite2);
		fireOnCollideNotifications(sprite1, sprite2);
		fireOnCollideNotifications(sprite2, sprite1);
	}
	
	private void fireOnCollideNotifications(Sprite sprite1, Sprite sprite2) {
		List<CollisionNotification<Sprite>> handlers = collisionNotificationMap.get(sprite1);
		if (handlers != null)
			for (CollisionNotification<Sprite> handler : handlers)
				handler.onCollide(this, sprite1, sprite2);
	}
	
	/**
	 * Fires all the events that was registered for sprite reaching world boundary.
	 * This method isn't invoked by default, it should be invoked in
	 * {@link CollisionDetector#testForCollisions(List, org.gwt.phys2d.client.gameplay.GameScene)} 
	 * of appropriate derivative class.
	 * 
	 * @param sprites List that contains one or more sprites that reached world bounds.
	 */
	protected void fireOnBeyondWorldEvents(List<Sprite> sprites) {
		if (sprites == null || sprites.isEmpty())
			return;
		for (CollisionHandler handler : collisionHandlerList)
			handler.onBeyondWorld(this, sprites);
		fireOnBeyondWorldNotifications(sprites);
	}
	
	private void fireOnBeyondWorldNotifications(List<Sprite> sprites) {
		if (collisionNotificationMap.isEmpty())
			return;
		for (Sprite sprite : sprites) {
			List<CollisionNotification<Sprite>> handlers = collisionNotificationMap.get(sprite);
			if (handlers != null)
				for (CollisionNotification<Sprite> handler : handlers)
					handler.onBeyondWorld(this, sprite, getCollidedEdges(sprite));
		}
	}

	/**
	 * Fires all the events that was registered for sprite reaching world boundary.
	 * This method isn't invoked by default, it should be invoked in
	 * {@link CollisionDetector#testForCollisions(List, org.gwt.phys2d.client.gameplay.GameScene)} 
	 * of appropriate derivative class.
	 * 
	 * @param sprites Array that contains one or more sprites that reached world bounds.
	 */
	protected void fireOnBeyondWorldEvents(Sprite... sprites) {
		if (sprites == null || sprites.length == 0)
			return;
		fireOnBeyondWorldEvents(Arrays.asList(sprites));
	}
	
	@Override
	public void testForCollisions(List<Sprite> sprites, GameScene gameScene) {
		spriteToBoundsMap.clear();
		if (sprites == null || sprites.isEmpty())
			return;
		for (int i = 0; i < accuracy; i++) {
			testForCollisionsSprites(sprites);
			testForCollisionsGameScene(sprites, gameScene);
		}
	}

	@Override
	public int getAccuracy() {
		return accuracy;
	}

	@Override
	public void setAccuracy(int accuracy) {
		if (accuracy < 1)
			accuracy = 1;
		this.accuracy = accuracy;
	}

	/**
	 * Gets list of world boundaries edges that was intersected by given sprite during last collision test.
	 *  
	 * @param sprite {@link Sprite} object we want to obtain collided edges for.
	 * @return non-empty list with edges if sprite collided world bounds. {@code null} otherwise.
	 * @see #testForCollisions(List, GameScene)
	 * @see #putCollidedEdge(Sprite, Line)
	 */
	public List<Line> getCollidedEdges(Sprite sprite) {
		return spriteToBoundsMap.get(sprite);
	}
	
	/**
	 * Register specified edge of world as intersected by given sprite.
	 * 
	 * @param sprite {@link Sprite} object we register collided edge for.
	 * @param edge {@link Line} object that was intersected by sprite
	 */
	public void putCollidedEdge(Sprite sprite, Line edge) {
		List<Line> lines = spriteToBoundsMap.get(sprite);
		if (lines == null) {
			lines = new ArrayList<Line>();
			spriteToBoundsMap.put(sprite, lines);
		}
		lines.add(edge);
	}
	
	/**
	 * Checking for collisions between sprites.
	 * 
	 * @param sprites List of sprites that may collide with each other
	 */
	protected void testForCollisionsSprites(List<Sprite> sprites) {
		if (sprites.size() >= 2)
			for (int i = 0; i < sprites.size() - 1; i++) {
				Sprite sprite1 = sprites.get(i);
				if (sprite1.isCollidable())
					for (int j = i + 1; j < sprites.size(); j++) {
						Sprite sprite2 = sprites.get(j);
						if (sprite2.isCollidable() && testForCollisions(sprite1, sprite2))
							fireOnCollideEvents(sprite1, sprite2);
					}
			}
	}
	
	/**
	 * Checking for collisions of sprites with world boundaries.
	 * 
	 * @param sprites List that contains one or more sprites that reached world bounds.
	 * @param gameScene {@link GameScene} object that encapsulates world boundaries.
	 */
	protected void testForCollisionsGameScene(List<Sprite> sprites, GameScene gameScene) {
		if (gameScene.getBounds().isCollidable()) {
			ArrayList<Sprite> a = new ArrayList<Sprite>(sprites.size());
			for (Sprite sprite : sprites)
				if (sprite.isCollidable() && testForCollisions(sprite, gameScene))
					a.add(sprite);
			if (!a.isEmpty())
				this.fireOnBeyondWorldEvents(a);
		}
	}
	
	/**
	 * Helper method to test for collision between two sprites. Is used in 
	 * {@link AbstractCollisionDetector#testForCollisions(List, GameScene)} method.
	 *  
	 * @param sprite1 First sprite that should be tested for collision with second one.
	 * @param sprite2 Second sprite that should be tested for collision with first one.
	 * @return {@code true} if was detected that specified sprites are intersected. {@code false} otherwise.  
	 * @see AbstractCollisionDetector#testForCollisions(List, GameScene)
	 */
	protected abstract boolean testForCollisions(Sprite sprite1, Sprite sprite2);
	
	/**
	 * Helper method to test for collision between sprite and world boundaries (if any). Is used in 
	 * {@link AbstractCollisionDetector#testForCollisions(List, GameScene)} method.
	 * 
	 * @param sprite sprite that should be tested for collision with world bounds.
	 * @param gameScene {@link GameScene} instance that holds polygon with world boundaries.
	 * @return {@code true} if was detected that specified sprite is reached world boundary. {@code false} otherwise.
	 * @see AbstractCollisionDetector#testForCollisions(List, GameScene)
	 */
	protected abstract boolean testForCollisions(Sprite sprite, GameScene gameScene);
}
