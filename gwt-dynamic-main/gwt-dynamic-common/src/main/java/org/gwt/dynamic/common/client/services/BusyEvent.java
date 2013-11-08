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
package org.gwt.dynamic.common.client.services;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class BusyEvent extends GwtEvent<BusyEvent.BusyHandler> {

	private static Type<BusyHandler> TYPE = new Type<BusyHandler>();

	public interface BusyHandler extends EventHandler {

		void onChange(BusyEvent event);
	}

	public interface HasBusyHandlers extends HasHandlers {

		HandlerRegistration addBusyHandler(BusyHandler handler);
	}

	private final boolean busy;

	public static Type<BusyHandler> getType() {
		return TYPE;
	}

	public BusyEvent(boolean busy) {
		this.busy = busy;
	}

	public boolean isBusy() {
		return busy;
	}

	@Override
	public Type<BusyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BusyHandler handler) {
		handler.onChange(this);
	}
}
