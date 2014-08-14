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
package org.gwt.dynamic.module.bar.client.demo;

import static org.gwt.phys2d.client.utils.VectorTools.EPSILON;
import gwt.g2d.client.graphics.Surface;

import org.gwt.phys2d.client.gameplay.AbstractGameScene;
import org.gwt.phys2d.client.gameplay.sprites.AbstractSprite;
import org.gwt.phys2d.client.gameplay.sprites.SpriteInitCallback;

public abstract class AbstractDemoSprite extends AbstractSprite {

	public AbstractDemoSprite(CircleCollisionGameScene gameScene) {
		super(gameScene);
	}

	protected Surface getSurface() {
		return ((AbstractGameScene) getGameScene()).getSurface();
	}

	protected CircleCollisionGameScene getScene() {
		return (CircleCollisionGameScene) getGameScene();
	}
	
	@Override
	public void init(SpriteInitCallback callback) {
		callback.onComplete(this);
	}

	@Override
	public void update() {
		float fps = getScene().getFps();
		if (fps < EPSILON)
			fps = getScene().getDesiredFps();
		getPosition().mutableAdd(getVelocity().scale(1d / fps));
	}
}
