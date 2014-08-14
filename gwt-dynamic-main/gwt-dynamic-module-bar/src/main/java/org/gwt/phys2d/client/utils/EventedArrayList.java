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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.google.gwt.event.shared.HandlerRegistration;

import static org.gwt.phys2d.client.utils.ListHandler.Type;

/**
 * This is implementation of eventful {@link ArrayList}.</br>
 * It means that all operations that change list (element adding, removing, replacing)
 * are preceded by firing {@link ListHandler#onModifying(java.util.List, Type, Collection)} event
 * handler with appropriate arguments.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 *
 * @param <E> Type of element in source list.
 */
public class EventedArrayList<E> extends ArrayList<E> implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean innerAction;
	
	/**
	 * List of {@link ListHandler}s that was registered for this list.
	 */
	protected ArrayList<ListHandler<E>> listHandlers = new ArrayList<ListHandler<E>>();

	/**
	 * An empty default constructor for list instance.
	 */
	public EventedArrayList() {
		super();
	}

	/**
	 * A constructor for this list instance with predefined elements.
	 * 
	 * @param elements {@link Collection} of elements that should be added into this list. 
	 */
	public EventedArrayList(Collection<? extends E> elements) {
		super(elements);
	}

	/**
	 * An empty default constructor for list instance with custom capacity.
	 * 
	 * @param initialCapacity An initial capacity of the list.
	 */
	public EventedArrayList(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public boolean add(E element) {
		if (fireEvents(Type.ADDING, element))
			return super.add(element);
		else
			return false;
	}

	@Override
	public void add(int index, E element) {
		if (fireEvents(Type.ADDING, element))
			super.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> elements) {
		if (fireEvents(Type.ADDING, elements))
			return super.addAll(elements);
		else
			return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> elements) {
		if (fireEvents(Type.ADDING, elements))
			return super.addAll(index, elements);
		else
			return false;
	}

	@Override
	public void clear() {
		if (fireEvents(Type.REMOVING, this))
			super.clear();
	}

	@Override
	public E remove(int index) {
		if (innerAction || fireEvents(Type.REMOVING, get(index)))
			return super.remove(index);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		if (fireEvents(Type.REMOVING, (E) o))
			return super.remove(o);
		else
			return false;
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		if (innerAction || fireEvents(Type.REMOVING, subList(fromIndex, toIndex)))
				super.removeRange(fromIndex, toIndex);
	}

	@Override
	public E set(int index, E element) {
		if (fireEvents(Type.UPDATING, element))
			return super.set(index, element);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean removeAll(Collection<?> elements) {
		if (fireEvents(Type.REMOVING, (Collection<? extends E>) elements)) {
			innerAction = true;
			try {
				return super.removeAll(elements);
			} finally {
				innerAction = false;
			}
		} else
			return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean retainAll(Collection<?> elements) {
		if (fireEvents(Type.RETAINING, (Collection<? extends E>) elements)) {
			innerAction = true;
			try {
				return super.retainAll(elements);
			} finally {
				innerAction = false;
			}
		} else
			return false;
	}

	public HandlerRegistration addEventHandler(ListHandler<E> handler) {
		if (handler == null)
			return null;
		if (!listHandlers.contains(handler))
			listHandlers.add(handler);
		return new ListHandlerRegistration<E>(this, handler);
	}
	
	@SuppressWarnings("unchecked")
	private boolean fireEvents(Type type, E item) {
		return fireEvents(type, item == null ? null : Arrays.asList(item));
	}
	
	private boolean fireEvents(Type type, Collection<? extends E> item) {
		for (ListHandler<E> listHandler : listHandlers)
			if (!listHandler.onModifying(this, type, item))
				return false;
		return true;
	}
}
