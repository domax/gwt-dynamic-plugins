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

import org.gwt.phys2d.client.gameplay.GameScene;
import org.gwt.phys2d.client.gameplay.sprites.Sprite;

/**
 * Simple polygon-related collision detector.<a>
 * It detects collisions between sprites, taking into account following assumptions:
 * <ol>
 * <li>Collidable sprites should have the collision polygons for detection.</li>
 * <li>All polygons that are liable for detection must be convex.</li>
 * <li>World bounds are also a polygon, but with inverted normals.</li>
 * </ol>
 * <p>See <a href="http://content.gpwiki.org/index.php/Polygon_Collision">Polygon Collision</a> for theory.</p>
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class PolygonCollisionDetector extends AbstractCollisionDetector {

	@Override
	protected boolean testForCollisions(Sprite sprite1, Sprite sprite2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean testForCollisions(Sprite sprite, GameScene gameScene) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void computeCollision(Sprite sprite1, Sprite sprite2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void computeCollision(Sprite sprite, GameScene gameScene) {
		// TODO Auto-generated method stub
		
	}
}
