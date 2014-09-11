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

import java.util.logging.Logger;

import org.gwt.dynamic.common.client.features.FeatureEvent.Handler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.Event.Type;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class FeatureEventBus<T> extends EventBus implements HasHandlers, FeatureEvent.HasHandlers<T> {
	
	private static final Logger LOG = Logger.getLogger(FeatureEventBus.class.getName());
	
	private static final String ERR_SOURCE_H = "Processing handlers for source isn't supported";
	private static final String ERR_EVENT_TYPE = "Event should be a FeatureEvent";
	
	private class FeatureEventBusProvider<A> extends FeatureProvider<A, Void> {

		public FeatureEventBusProvider() {
			super(moduleName);
			LOG.info("FeatureEventBus$FeatureEventBusProvider: registered for " + GWT.getModuleName());
		}

		@Override
		public String getType() {
			return featureType;
		}

		@Override
		public void call(A arguments, AsyncCallback<Void> callback) {
			try {
				FeatureEvent.fire(eventBus, moduleName, featureType, arguments);
				if (callback != null) callback.onSuccess(null);
			} catch (Exception e) {
				if (callback != null) callback.onFailure(e);
			}
		}
	}

	private class FeatureEventBusConsumer<A> extends FeatureConsumer<A, Void> {

		@Override
		public String getModuleName() {
			return moduleName;
		}

		@Override
		public String getType() {
			return featureType;
		}
	}
	
	private final String moduleName;
	private final String featureType;
	private final FeatureEventBusConsumer<T> consumer;
	private final SimpleEventBus eventBus;
	private final AsyncCallback<Void> consumerCallback = new AsyncCallback<Void>() {
		
		@Override
		public void onSuccess(Void result) {
			LOG.info("FeatureEventBus.consumerCallback#onSuccess");
		}
		
		@Override
		public void onFailure(Throwable caught) {
			LOG.warning("FeatureEventBus.consumerCallback#onFailure: " + caught.getMessage());
		}
	};

	public FeatureEventBus(String moduleName, String featureType) {
		this.moduleName = moduleName;
		this.featureType = featureType;
		new FeatureEventBusProvider<T>();
		consumer = new FeatureEventBusConsumer<T>();
		eventBus = new SimpleEventBus();
		LOG.info("FeatureEventBus: moduleName=" + moduleName + "; featureType=" + featureType);
	}

	@Override
	public <H> HandlerRegistration addHandler(Type<H> type, H handler) {
		return eventBus.addHandler(type, handler);
	}

	@Override
	public <H> HandlerRegistration addHandlerToSource(Type<H> type, Object source, H handler) {
		if (source != null) throw new IllegalArgumentException(ERR_SOURCE_H);
		return addHandler(type, handler);
	}

	@Override
	public void fireEvent(Event<?> event) {
		if (event instanceof FeatureEvent) {
			@SuppressWarnings("unchecked") FeatureEvent<T> e = (FeatureEvent<T>) event;
			consumer.call(e.getData(), consumerCallback);
		} else throw new IllegalArgumentException(ERR_EVENT_TYPE);
	}

	@Override
	public void fireEventFromSource(Event<?> event, Object source) {
		if (source != null) throw new IllegalArgumentException(ERR_SOURCE_H);
		fireEvent(event);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		fireEvent((Event<?>) event);
	}

	@Override
	public HandlerRegistration addFeatureEventHandler(Handler<T> handler) {
		return addHandler(FeatureEvent.getType(), handler);
	}
}
