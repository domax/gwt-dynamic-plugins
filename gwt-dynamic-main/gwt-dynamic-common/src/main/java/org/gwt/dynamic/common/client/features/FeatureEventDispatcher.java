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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class FeatureEventDispatcher implements HasHandlers {

	private static final Logger LOG = Logger.getLogger(FeatureEventDispatcher.class.getName());
	
	private static class Key {

		private final String moduleName;
		private final String featureType;

		public Key(String moduleName, String featureType) {
			this.moduleName = moduleName;
			this.featureType = featureType;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((featureType == null) ? 0 : featureType.hashCode());
			result = prime * result + ((moduleName == null) ? 0 : moduleName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			Key other = (Key) obj;
			if (featureType == null) {
				if (other.featureType != null) return false;
			} else if (!featureType.equals(other.featureType)) return false;
			if (moduleName == null) {
				if (other.moduleName != null) return false;
			} else if (!moduleName.equals(other.moduleName)) return false;
			return true;
		}

		@Override
		public String toString() {
			return new StringBuilder("Key")
					.append(" {moduleName=").append(moduleName)
					.append(", featureType=").append(featureType)
					.append("}").toString();
		}
	}

	private final Map<Key, FeatureEventBus<?>> featureEventBusMap = new HashMap<>();
	private final Map<Key, HandlerRegistration> handlerRegistrationMap = new HashMap<>();
	private final FeatureEvent.Handler<Object> featureEventHandler;

	public FeatureEventDispatcher(final EventBus eventBus) {
		featureEventHandler = new FeatureEvent.Handler<Object>() {
			@Override
			public void onEvent(FeatureEvent<Object> event) {
				FeatureEvent.fire(eventBus, FeatureEventDispatcher.this, 
						event.getModuleName(), event.getFeatureType(), event.getData());
			}
		};
		eventBus.addHandler(FeatureEvent.getType(), new FeatureEvent.Handler<Object>() {
			@Override
			public void onEvent(FeatureEvent<Object> e) {
				if (e.getSource() != FeatureEventDispatcher.this)
					FeatureEvent.fire(FeatureEventDispatcher.this, e.getModuleName(), e.getFeatureType(), e.getData());
			}
		});
		LOG.info("FeatureEventDispatcher: instantiated");
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		if (event instanceof FeatureEvent) {
			@SuppressWarnings("unchecked") FeatureEvent<Object> e = (FeatureEvent<Object>) event;
			Key pair = new Key(e.getModuleName(), e.getFeatureType());
			FeatureEventBus<?> featureEventBus = featureEventBusMap.get(pair);
			if (featureEventBus != null)
				FeatureEvent.fire((EventBus) featureEventBus, e.getModuleName(), e.getFeatureType(), e.getData());
		} else LOG.warning("FeatureEventDispatcher.fireEvent: wrong event type specified");
	}

	public FeatureEventDispatcher activate(String moduleName, String featureType) {
		LOG.info("FeatureEventDispatcher.activateFeature: moduleName=" + moduleName + "; featureType=" + featureType);
		Key pair = new Key(moduleName, featureType);
		FeatureEventBus<?> featureEventBus = featureEventBusMap.get(pair);
		if (featureEventBus == null) {
			featureEventBus = new FeatureEventBus<Object>(moduleName, featureType);
			featureEventBusMap.put(pair, featureEventBus);
		}
		HandlerRegistration handlerRegistration = handlerRegistrationMap.get(pair);
		if (handlerRegistration == null)
			handlerRegistrationMap.put(pair, featureEventBus.addHandler(FeatureEvent.getType(), featureEventHandler));			
		return this;
	}

	public FeatureEventDispatcher deactivate(String moduleName, String featureType) {
		LOG.info("FeatureEventDispatcher.deactivateFeature: moduleName=" + moduleName + "; featureType=" + featureType);
		Key pair = new Key(moduleName, featureType);
		HandlerRegistration handlerRegistration = handlerRegistrationMap.remove(pair);
		if (handlerRegistration != null) handlerRegistration.removeHandler();
		return this;
	}
}
