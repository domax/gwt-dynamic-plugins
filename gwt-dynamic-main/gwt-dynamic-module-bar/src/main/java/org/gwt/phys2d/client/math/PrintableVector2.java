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

import org.gwt.phys2d.client.utils.VectorTools;

import gwt.g2d.client.math.Vector2;

/**
 * Simple class that just implements {@link Object#toString()} method for {@link Vector2} class.<br/>
 * This class will be deprecated as soon as issue with absent {@code toString()} method
 * in native {@link Vector2} class will be fixed.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class PrintableVector2 extends Vector2 {

	private static final long serialVersionUID = -8796108587875755409L;

	public PrintableVector2() {
		super();
	}

	public PrintableVector2(double x, double y) {
		super(x, y);
	}

	public PrintableVector2(double value) {
		super(value);
	}

	public PrintableVector2(Vector2 vector) {
		super(vector);
	}

	/**
	 * Adopts given vector into printable implementation.<br/>
	 * <em>Caution</em>: You have to use explicit vector creation if you want
	 * to have guaranteed new instance of {@link PrintableVector2}.
	 * 
	 * @param vector An instance of {@link Vector2} that should be adopted.
	 * @return new instance of {@link PrintableVector2} if given vector isn't instance of it.
	 *         The same instance of given vector if it is {@link PrintableVector2}.
	 *         {@code null} if given vector is {@code null}.
	 */
	public static PrintableVector2 valueOf(Vector2 vector) {
		return vector == null ? null 
				: vector instanceof PrintableVector2 ? (PrintableVector2) vector 
				: new PrintableVector2(vector);
	}
	
	@Override
	public String toString() {
		return VectorTools.printVector(this);
	}
}
