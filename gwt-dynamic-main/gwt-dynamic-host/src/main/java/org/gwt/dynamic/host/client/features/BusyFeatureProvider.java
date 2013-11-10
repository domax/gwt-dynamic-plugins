/*
 * Copyright 2013 Maxim Dominichenko
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
package org.gwt.dynamic.host.client.features;

import java.util.logging.Logger;

import org.gwt.dynamic.common.client.features.FeatureCommonConst;
import org.gwt.dynamic.common.client.features.FeatureProvider;
import org.gwt.dynamic.common.client.services.BusyEvent;
import org.gwt.dynamic.common.client.services.BusyEvent.BusyHandler;
import org.gwt.dynamic.common.client.services.BusyEvent.HasBusyHandlers;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class BusyFeatureProvider extends FeatureProvider<Boolean, Void>
		implements FeatureCommonConst, HasBusyHandlers {

	private static final Logger LOG = Logger.getLogger(BusyFeatureProvider.class.getName());
	
	private final HandlerManager handlerManager;
	private int count;
	
	public BusyFeatureProvider() {
		super(MODULE_HOST);
		handlerManager = new HandlerManager(this);
		LOG.info("BusyFeatureProvider: instantiated");
	}

	@Override
	public String getType() {
		return FEATURE_BUSY;
	}

	@Override
	public void call(Boolean busy, AsyncCallback<Void> callback) {
		LOG.info("BusyFeatureProvider.call: busy=" + busy + "; count=" + count);
		if (busy) {
			if (count++ > 0) return;
		} else {
			if (--count > 0) return;
		}
		fireEvent(new BusyEvent(busy));
	}

	public boolean isBusy() {
		return count > 0;
	}
	
	@Override
	public void fireEvent(GwtEvent<?> event) {
		handlerManager.fireEvent(event);
	}

	@Override
	public HandlerRegistration addBusyHandler(BusyHandler handler) {
		return handlerManager.addHandler(BusyEvent.getType(), handler);
	}
}
