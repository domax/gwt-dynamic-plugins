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

/**
 * This is implementation of immutable {@link ArrayList}.</br>
 * It means that this class doesn't allow runtime list management: adding, removing, replacing elements.
 * Though, developer still has an ability to change particular elements themselves.
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 *
 * @param <E> Type of element in source list.
 */
public class ImmutableArrayList<E> extends ArrayList<E> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String ADD = "add";
	private static final String REMOVE = "remove";
	private static final String SET = "set";
	private static final String RETAIN = "retain";

	/**
	 * A constructor for immutable list. It's only way to define item list.
	 * 
	 * @param elements {@link Collection} of elements that should be added into this list.
	 */
	public ImmutableArrayList(Collection<? extends E> elements) {
		super(elements);
	}

	/**
	 * A constructor for immutable list. It's only way to define item list.
	 * 
	 * @param elements Array of elements that should be added into this list.
	 */
	public ImmutableArrayList(E... elements) {
		super(Arrays.asList(elements));
	}

	/**
	 * This method just throws {@link ImmutableArrayListException} with appropriate message.
	 */
	@Override
	public boolean add(E element) throws ImmutableArrayListException {
		throw new ImmutableArrayListException(exceptionMessage(ADD, element));
	}

	/**
	 * This method just throws {@link ImmutableArrayListException} with appropriate message.
	 */
	@Override
	public void add(int index, E element) throws ImmutableArrayListException {
		throw new ImmutableArrayListException(exceptionMessage(ADD, element));
	}

	/**
	 * This method just throws {@link ImmutableArrayListException} with appropriate message.
	 */
	@Override
	public boolean addAll(Collection<? extends E> elements) throws ImmutableArrayListException {
		throw new ImmutableArrayListException(exceptionMessage(ADD, elements));
	}

	/**
	 * This method just throws {@link ImmutableArrayListException} with appropriate message.
	 */
	@Override
	public boolean addAll(int index, Collection<? extends E> elements) throws ImmutableArrayListException {
		throw new ImmutableArrayListException(exceptionMessage(ADD, elements));
	}

	/**
	 * This method just throws {@link ImmutableArrayListException} with appropriate message.
	 */
	@Override
	public void clear() throws ImmutableArrayListException {
		throw new ImmutableArrayListException(exceptionMessage(REMOVE, this));
	}

	/**
	 * This method just throws {@link ImmutableArrayListException} with appropriate message.
	 */
	@Override
	public E remove(int index) throws ImmutableArrayListException {
		throw new ImmutableArrayListException(exceptionMessage(REMOVE, get(index)));
	}

	/**
	 * This method just throws {@link ImmutableArrayListException} with appropriate message.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) throws ImmutableArrayListException {
		throw new ImmutableArrayListException(exceptionMessage(REMOVE, (E) o));
	}

	/**
	 * This method just throws {@link ImmutableArrayListException} with appropriate message.
	 */
	@Override
	protected void removeRange(int fromIndex, int toIndex) throws ImmutableArrayListException {
		throw new ImmutableArrayListException(exceptionMessage(REMOVE, subList(fromIndex, toIndex)));
	}

	/**
	 * This method just throws {@link ImmutableArrayListException} with appropriate message.
	 */
	@Override
	public E set(int index, E element) throws ImmutableArrayListException {
		throw new ImmutableArrayListException(exceptionMessage(SET, element));
	}

	/**
	 * This method just throws {@link ImmutableArrayListException} with appropriate message.
	 */
	@Override
	public boolean removeAll(Collection<?> elements) throws ImmutableArrayListException {
		throw new ImmutableArrayListException(exceptionMessage(REMOVE, elements));
	}

	/**
	 * This method just throws {@link ImmutableArrayListException} with appropriate message.
	 */
	@Override
	public boolean retainAll(Collection<?> elements) throws ImmutableArrayListException {
		throw new ImmutableArrayListException(exceptionMessage(RETAIN, elements));
	}
	
	@SuppressWarnings("unchecked")
	private String exceptionMessage(String action, E element) {
		return exceptionMessage(action, element == null ? null : Arrays.asList(element));
	}
	
	private String exceptionMessage(String action, Collection<?> elements) {
		return "Cannot " + action + " "
				+ (elements != null && elements.size() == 1 ? "element" : "elements")
				+ " " + elements + " " 
				+ (ADD.equals(action) || SET.equals(action) ? "into" : REMOVE.equals(action) ? "from" : "in") 
				+ " immutable list.";
	}
}
