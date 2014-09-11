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
package org.gwt.dynamic.common.client.features;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class FeatureEvent<T> extends GwtEvent<FeatureEvent.Handler<T>> {

	private static final Type<Handler<?>> TYPE = new Type<Handler<?>>();

	public static interface Handler<T> extends EventHandler {

		void onEvent(FeatureEvent<T> event);
	}

	public static interface HasHandlers<T> {
		
		public HandlerRegistration addFeatureEventHandler(Handler<T> handler);
	}

	public static <T> void fire(EventBus eventBus, String moduleName, String featureType, T data) {
		eventBus.fireEvent(new FeatureEvent<T>(moduleName, featureType, data));
	}

	static <T> void fire(EventBus eventBus, Object source, String moduleName, String featureType, T data) {
		eventBus.fireEventFromSource(new FeatureEvent<T>(moduleName, featureType, data), source);
	}
	
	public static <T> void fire(
			com.google.gwt.event.shared.HasHandlers source, String moduleName, String featureType, T data) {
		source.fireEvent(new FeatureEvent<T>(moduleName, featureType, data));
	}

	public static Type<Handler<?>> getType() {
		return TYPE;
	}

	private final String moduleName;
	private final String featureType;
	private final T data;

	private FeatureEvent(String moduleName, String featureType, T data) {
		if (data != null && !(data instanceof String || data instanceof JavaScriptObject))
			throw new IllegalArgumentException("Data argument can be String or JavaScriptObject or derived");
		this.moduleName = moduleName;
		this.featureType = featureType;
		this.data = data;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Type<Handler<T>> getAssociatedType() {
		return (Type) TYPE;
	}

	@Override
	protected void dispatch(Handler<T> handler) {
		handler.onEvent(this);
	}

	public T getData() {
		return data;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getFeatureType() {
		return featureType;
	}
}
