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
package org.gwt.phys2d.client.math;

import gwt.g2d.client.math.Vector2;

/**
 * Interface that represents event handlers for {@link EventedVector2}.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
//FIXME: Find a way to join both event methods into one common
public interface Vector2Handler {

	/**
	 * Should handle an event when property X of specified instance of {@link Vector2} source was changed.
	 * 
	 * @param source An instance of {@link Vector2} where changes was performed.
	 * @param oldX Previous value of property X
	 * @param newX New (and current) value of property X
	 */
	void onXChanged(Vector2 source, double oldX, double newX);

	/**
	 * Should handle an event when property Y of specified instance of {@link Vector2} source was changed.
	 * 
	 * @param source An instance of {@link Vector2} where changes was performed.
	 * @param oldY Previous value of property Y
	 * @param newY New (and current) value of property Y
	 */
	void onYChanged(Vector2 source, double oldY, double newY);
}
