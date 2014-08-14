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

import gwt.g2d.client.graphics.DirectShapeRenderer;
import gwt.g2d.client.graphics.KnownColor;
import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.util.CountDown;
import gwt.g2d.client.util.FpsTimer;

import java.util.ArrayList;
import java.util.List;

import org.gwt.phys2d.client.collisions.CollisionDetector;
import org.gwt.phys2d.client.gameplay.sprites.Polygon;
import org.gwt.phys2d.client.gameplay.sprites.Sprite;
import org.gwt.phys2d.client.gameplay.sprites.SpriteInitCallback;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;

/**
 * Holds all resources to compute and draw game scene.<br/>
 * This class is abstract, though it doesn't have abstract methods
 * - it's because common game scene is empty by default, 
 * you have to setup it by creating your custom game scene with all needed stuff.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public abstract class AbstractGameScene extends FpsTimer implements GameScene {
	
	private Panel panel;
	private Surface surface;
	private DirectShapeRenderer shapeRenderer;
	private ArrayList<Sprite> sprites;
	private CollisionDetector collisionDetector;
	private Polygon bounds;

	/**
	 * Game scene constructor - prepares all needed resources and links game scene
	 * with canvas pane on HTML page.
	 * 
	 * @param panel GWT {@link Panel} instance where to will be rendered game screen
	 */
	public AbstractGameScene(Panel panel) {
		this.panel = panel;
		surface = new Surface(panel.getOffsetWidth(), panel.getOffsetHeight());
		shapeRenderer = new DirectShapeRenderer(surface);
		sprites = new ArrayList<Sprite>();
		bounds = new Polygon();
//		setDesiredFps(20);
	}

	@Override
	public int getWidth() {
		return surface.getWidth();
	}

	@Override
	public int getHeight() {
		return surface.getHeight();
	}
	
	/**
	 * Gets G2D {@link Surface} instance that is used as canvas for drawing game screen
	 * @return A {@link Surface} instance object
	 * @see #getRenderer()
	 */
	public Surface getSurface() {
		return surface;
	}

	/**
	 * Gets G2D {@link DirectShapeRenderer} instance that is used as renderer for drawing game screen
	 * @return A {@link DirectShapeRenderer} instance object
	 * @see #getSurface()
	 */
	public DirectShapeRenderer getRenderer() {
		return shapeRenderer;
	}

	/**
	 * Initializes screen drawing surface and starts loop for rendering frames.
	 */
	@Override
	public void start() {
		panel.clear();
		panel.add(surface);
		if (!sprites.isEmpty()) {
			final CountDown countdown = new CountDown(sprites.size());
			for (Sprite sprite : sprites) {
				sprite.init(new SpriteInitCallback() {

					@Override
					public void onComplete(Sprite sprite) {
						if (countdown.tick())
							AbstractGameScene.super.start();
					}

					@Override
					public void onError(Sprite sprite, Throwable error) {
						Window.alert("Error: " + error.getMessage());
					}
				});
			}
		}
	}

	@Override
	public List<Sprite> getSprites() {
		return sprites;
	}

	@Override
	public List<Sprite> getColliders() {
		final ArrayList<Sprite> result = new ArrayList<Sprite>(); 
		for (Sprite sprite : sprites)
			if (sprite.isCollidable())
				result.add(sprite);
		return result;
	}
		
	@Override
	public Polygon getBounds() {
		return bounds;
	}

	@Override
	public CollisionDetector getCollisionDetector() {
		return collisionDetector;
	}

	/**
	 * Defines instance of effective collision detector.
	 * @param collisionDetector An object instance of {@link CollisionDetector} interface
	 */
	public void setCollisionDetector(CollisionDetector collisionDetector) {
		this.collisionDetector = collisionDetector;
	}

	/**
	 * Draws background for each frame.
	 * Default action is filling frame by black color.<br/>
	 * Override this method to define your own beautiful background.
	 */
	public void drawBackground() {
		surface.clear().fillBackground(KnownColor.BLACK);
	}
	
	/**
	 * Updates game model for current frame and forces redraw game screen.<br/>
	 * All gameplay computations should be included here.<br/>
	 * This method does by default following:
	 * <ol>
	 * <li>performs collision detection (if collision detector is defined);</li>
	 * <li>then calls method {@link Sprite#update()} for each sprite in scene;</li>
	 * <li>then clears game screen background with method {@link #drawBackground()};</li>
	 * <li>finally calls method {@link Sprite#draw()} for each visible sprite;</li>
	 * </ol>
	 * As a rule you don't need to fully override this method - just to extend it somehow. 
	 */
	@Override
	public void update() {
		if (collisionDetector != null)
			collisionDetector.testForCollisions(getColliders(), this);
		
		for (Sprite sprite : sprites)
			sprite.update();
		
		drawBackground();
		//FIXME: add computation of sprites visibility 
		for (Sprite sprite : sprites)
			if (sprite.isVisible())
				sprite.draw();
	}
}
