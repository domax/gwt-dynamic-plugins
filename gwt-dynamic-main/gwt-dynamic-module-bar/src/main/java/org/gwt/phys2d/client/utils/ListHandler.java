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
package org.gwt.phys2d.client.utils;

import java.util.Collection;
import java.util.List;

/**
 * Interface that represents event handlers for {@link List}-oriented interfaces.<br/>
 * The custom implementation is {@link EventedArrayList}
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 *
 * @param <E> Type of element in source list.
 */
public interface ListHandler<E> {
	
	/**
	 * Enumeration of possible modification types.
	 */
	static enum Type {ADDING, UPDATING, REMOVING, RETAINING};

	/**
	 * Should handle an event when some list modification is going to perform.<br/>
	 * Take into account that this handler will be fired <em>before</em> real action, 
	 * and developer has possibility to cancel this action if any.  
	 * 
	 * @param source {@link List} that contains elements.
	 * @param type One of {@link Type} actions that should be performed.
	 * @param items Performed items - elements that should be added or removed.
	 * @return {@code true} if developer allows action. {@code false} otherwise.
	 */
	boolean onModifying(List<E> source, Type type, Collection<? extends E> items);
}
