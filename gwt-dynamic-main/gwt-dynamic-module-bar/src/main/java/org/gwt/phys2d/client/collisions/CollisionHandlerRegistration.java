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

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * The {@link HandlerRegistration} implementation for {@link CollisionDetector} event handling.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class CollisionHandlerRegistration implements HandlerRegistration {
	
	private AbstractCollisionDetector source;
	private CollisionHandler handler;
	
	/**
	 * Constructs ticket about registration of specified collision detector and handler.
	 * 
	 * @param source An {@link AbstractCollisionDetector} instance where handler is registered.
	 * @param handler A {@link CollisionHandler} instance that is registered.
	 */
	public CollisionHandlerRegistration(AbstractCollisionDetector source, CollisionHandler handler) {
		this.source = source;
		this.handler = handler;
	}

	@Override
	public void removeHandler() {
		if (source != null && handler != null) {
			source.collisionHandlerList.remove(handler);
			handler = null;
		}
	}
}
