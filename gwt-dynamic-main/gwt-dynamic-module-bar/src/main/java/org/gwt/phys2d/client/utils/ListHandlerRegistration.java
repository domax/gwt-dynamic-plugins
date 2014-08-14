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

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * The {@link HandlerRegistration} implementation for {@link ListHandler} event handling.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 *
 * @param <E> Type of element in source list.
 */
public class ListHandlerRegistration<E> implements HandlerRegistration {

	private EventedArrayList<E> source;
	private ListHandler<E> handler;
	
	/**
	 * Constructs ticket about registration of specified handler for eventful list.
	 * 
	 * @param source An {@link EventedArrayList} instance where handler is registered.
	 * @param handler A {@link ListHandler} instance that is registered.
	 */
	public ListHandlerRegistration(EventedArrayList<E> source, ListHandler<E> handler) {
		this.source = source;
		this.handler = handler;
	}
	
	@Override
	public void removeHandler() {
		if (source != null && handler != null) {
			source.listHandlers.remove(handler);
			handler = null;
		}
	}
}
