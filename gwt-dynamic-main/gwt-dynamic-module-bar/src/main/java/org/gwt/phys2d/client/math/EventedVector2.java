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

import static org.gwt.phys2d.client.utils.VectorTools.EPSILON;
import gwt.g2d.client.math.Vector2;

import java.util.ArrayList;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * The eventful implementation of {@link Vector2} class.<br/>
 * It means that all operations that change instance -
 * {@link Vector2#setX(double)} and {@link Vector2#setY(double)} - are followed
 * by firing {@link Vector2Handler#onXChanged(Vector2, double, double)} or
 * {@link Vector2Handler#onYChanged(Vector2, double, double)} event handlers
 * respectively.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class EventedVector2 extends Vector2 {

	private static final long serialVersionUID = -1975261295143780653L;

	/**
	 * List of {@link Vector2Handler}s that was registered for this vector.
	 */
	protected ArrayList<Vector2Handler> vector2Handlers = new ArrayList<Vector2Handler>();

	private static enum AnEvent {X, Y};

	public EventedVector2() {
		super();
	}

	public EventedVector2(double x, double y) {
		super(x, y);
	}

	public EventedVector2(double value) {
		super(value);
	}

	public EventedVector2(Vector2 rhs) {
		super(rhs);
	}

	@Override
	public void setX(double x) {
		double v = getX();
		if (Math.abs(v - x) > EPSILON) {
			super.setX(x);
			fireEvents(AnEvent.X, v, x);
		}
	}

	@Override
	public void setY(double y) {
		double v = getY();
		if (Math.abs(v - y) > EPSILON) {
			super.setY(y);
			fireEvents(AnEvent.Y, v, y);
		}
	}

	public HandlerRegistration addEventHandler(Vector2Handler handler) {
		if (handler == null)
			return null;
		if (!vector2Handlers.contains(handler))
			vector2Handlers.add(handler);
		return new Vector2HandlerRegistration(this, handler);
	}

	private void fireEvents(AnEvent event, double oldValue, double newValue) {
		for (Vector2Handler handler : vector2Handlers)
			switch (event) {
				case X:
					handler.onXChanged(this, oldValue, newValue);
					break;
				case Y:
					handler.onYChanged(this, oldValue, newValue);
					break;
				default:
					return;
			}
	}
}
