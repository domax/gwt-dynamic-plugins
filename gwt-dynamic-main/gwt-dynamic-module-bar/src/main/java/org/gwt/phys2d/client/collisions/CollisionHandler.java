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

import org.gwt.phys2d.client.gameplay.sprites.Sprite;

/**
 * Interface that represents handler of {@link Sprite} collisions.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public interface CollisionHandler {
	
	/**
	 * Should handle event when two sprites collide.
	 * 
	 * @param collisionDetector A collision detector that was used.
	 * @param sprite1 First sprite that collided with second one.
	 * @param sprite2 Second sprite that collided with first one.
	 */
	void onCollide(CollisionDetector collisionDetector, Sprite sprite1, Sprite sprite2);

	/**
	 * Should handle event when one or more sprites reach world bounds.
	 * 
	 * @param collisionDetector A collision detector that was used.
	 * @param sprites List that contains one or more sprites that reached world bounds.
	 */
	void onBeyondWorld(CollisionDetector collisionDetector, List<Sprite> sprites);
}
