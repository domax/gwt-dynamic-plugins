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

import static org.gwt.phys2d.client.utils.Utils.getSimpleName;
import static org.gwt.phys2d.client.utils.VectorTools.*;

import org.gwt.phys2d.client.gameplay.GameScene;
import org.gwt.phys2d.client.math.EventedVector2;
import org.gwt.phys2d.client.math.Vector2Handler;
import org.gwt.phys2d.client.utils.VectorTools;

import gwt.g2d.client.math.Vector2;

/**
 * Common abstract class for any sprite in game.<br/>
 * This class implements almost all needed functionality defined in interface {@link Sprite},
 * and is prepared to be completely drawable for game scene.
 * All you need to do - to customize it. 
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public abstract class AbstractSprite implements Sprite {

	private GameScene gameScene;
	private EventedVector2 position;
	private Vector2 velocity;
	private Polygon collidePolygon;
	private double weight = 0d;
	private double elasticity = 1d;
	private boolean visible = true;
	private boolean collidable = false;
	
	private class SpritePositionVector2Handler implements Vector2Handler {

		@Override
		public void onXChanged(Vector2 source, double oldX, double newX) {
			collidePolygon.moveBy(newX - oldX, 0);
		}

		@Override
		public void onYChanged(Vector2 source, double oldY, double newY) {
			collidePolygon.moveBy(0, newY - oldY);
		}
	}
	
	/**
	 * Default empty constructor.
	 * Don't use it directly in your code, but if you have to, then
	 * use {@link #setGameScene(GameScene)} method to place sprite into gameplay.
	 * @see #AbstractSprite(GameScene)
	 */
	public AbstractSprite() {
		position = new EventedVector2();
		position.addEventHandler(new SpritePositionVector2Handler());
		velocity = new Vector2();
		collidePolygon = new Polygon();
	}

	/**
	 * Constructs sprite instance and pushes it into game scene
	 * @param gameScene {@link GameScene} object instance this sprite should belong to
	 */
	public AbstractSprite(GameScene gameScene) {
		this();
		this.gameScene = gameScene;
		if (this.gameScene != null)
			this.gameScene.getSprites().add(this);
	}

	/**
	 * Gets game scene associated with this sprite instance.
	 * @return {@link GameScene} object instance this sprite belongs to
	 */
	public GameScene getGameScene() {
		return gameScene;
	}

	/**
	 * Sets game scene associated with this sprite instance.
	 * @param gameScene {@link GameScene} object instance this sprite should belong to
	 */
	public void setGameScene(GameScene gameScene) {
		//FIXME: define right behavior (adding/removing into game scene).
		this.gameScene = gameScene;
	}
	
	@Override
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets sprite visibility property.
	 * @param visible {@code true} if sprite should be drawn. {@code false} otherwise.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public Vector2 getVelocity() {
		return velocity;
	}
	
	@Override
	public double getWeight() {
		return weight;
	}

	/**
	 * Sets property that holds information about sprite's weight (if any).
	 * It is used to compute sprite's animation with physics.
	 * @param weight Conventional weight of sprite - units depend on game.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public double getElasticity() {
		return elasticity;
	}

	/**
	 * Sets property that holds information about sprite's elasticity.<br/>
	 * {@code 0} is completely inelastic (lump of clay) and {@code 1} is purely elastic (superball).
	 * @param elasticity Value of sprite elasticity.
	 */
	public void setElasticity(double elasticity) {
		if (elasticity < 0.0) elasticity = 0.0;
		else if (elasticity > 1.0) elasticity = 1.0;
		this.elasticity = elasticity;
	}

	@Override
	public Polygon getCollidePolygon() {
		return collidePolygon;
	}

	@Override
	public boolean isCollidable() {
		return collidable && collidePolygon.isCollidable() && Math.abs(weight) > VectorTools.EPSILON;
	}

	/**
	 * Sets property whether this sprite may collide with others or not.
	 * @param collidable {@code true} if sprite may collide with others and with world boundary. 
	 *          {@code false} otherwise.
	 */
	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getSimpleName(getClass()));	
		builder.append(" {gameScene=");
		builder.append(gameScene);
		builder.append(", position=");
		builder.append(printVector(position));
		builder.append(", velocity=");
		builder.append(printVector(velocity));
		builder.append(", weight=");
		builder.append(weight);
		builder.append(", elasticity=");
		builder.append(elasticity);
		builder.append(", visible=");
		builder.append(visible);
		builder.append(", collidable=");
		builder.append(collidable);
		builder.append(", collidePolygon=");
		builder.append(collidePolygon);
		builder.append("}");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (collidable ? 1231 : 1237);
		result = prime * result + ((collidePolygon == null) ? 0 : collidePolygon.hashCode());
		long temp;
		temp = new Double(elasticity * 1000).longValue();
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((velocity == null) ? 0 : velocity.hashCode());
		result = prime * result + (visible ? 1231 : 1237);
		temp = new Double(weight * 1000).longValue();
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		AbstractSprite other = (AbstractSprite) obj;
		if (collidable != other.collidable)
			return false;
		if (collidePolygon == null) {
			if (other.collidePolygon != null)
				return false;
		} else if (!collidePolygon.equals(other.collidePolygon))
			return false;
		if (Math.abs(elasticity - other.elasticity) > EPSILON)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (velocity == null) {
			if (other.velocity != null)
				return false;
		} else if (!velocity.equals(other.velocity))
			return false;
		if (visible != other.visible)
			return false;
		if (Math.abs(weight - other.weight) > EPSILON)
			return false;
		return true;
	}
}
