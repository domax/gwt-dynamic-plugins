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
package org.gwt.dynamic.common.client.services;

import static org.gwt.dynamic.common.shared.Utils.isHollow;

import java.util.logging.Logger;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.gwt.dynamic.common.client.features.BusyFeatureConsumer;
import org.gwt.dynamic.common.client.services.BusyEvent.BusyHandler;
import org.gwt.dynamic.common.client.services.BusyEvent.HasBusyHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AbstractServiceConsumer<R extends RestService> implements HasBusyHandlers {
	
	private static final Logger LOG = Logger.getLogger(AbstractServiceConsumer.class.getName());
	private static final String REST_ROOT_PATH = "rest";

	protected abstract class Requestor<T> {
		
		protected abstract void callRest(R rest, MethodCallback<T> callback);
		
		public void call(final AsyncCallback<T> callback) {
			LOG.info("AbstractServiceConsumer.Requestor.call");
			setBusy(true);
			callRest(rest, new MethodCallback<T>() {

				@Override
				public void onSuccess(Method method, T response) {
					LOG.info("AbstractServiceConsumer.Requestor.call#callRest#onSuccess");
					setBusy(false);
					if (callback != null) callback.onSuccess(response);
				}

				@Override
				public void onFailure(Method method, Throwable exception) {
					LOG.severe("AbstractServiceConsumer.Requestor.call#callRest#onFailure: " + exception.getMessage());
					setBusy(false);
					if (callback != null) callback.onFailure(exception);
				}
			});
		}
	}
	
	private final R rest;
	private HandlerManager handlerManager;
	
	private static native String getServiceUrl0(String moduleName) /*-{
		if (!$wnd.__config || !$wnd.__config[moduleName]) return null;
		return $wnd.__config[moduleName].service_url;
	}-*/;
	
	public static String getServiceUrl() {
		String result = GWT.isProdMode() ? getServiceUrl0(GWT.getModuleName()) : null;
		if (isHollow(result)) result = GWT.getHostPageBaseURL() + REST_ROOT_PATH;
		return result;
	}

	protected AbstractServiceConsumer(R rest) {
		if (rest == null) throw new IllegalArgumentException("Rest service cannot be a null");
		this.rest = rest;
		RestServiceProxy restProxy = (RestServiceProxy) this.rest;
		String serviceUrl = getServiceUrl();
		String resourceUrl = restProxy.getResource().getUri();
		if (resourceUrl.startsWith(GWT.getModuleBaseURL()))
			serviceUrl += resourceUrl.substring(GWT.getModuleBaseURL().length() - 1);
		LOG.info("AbstractServiceConsumer: resourceUrl=" + resourceUrl + "; serviceUrl=" + serviceUrl);
		restProxy.setResource(new Resource(serviceUrl));
	}

	private void setBusy(boolean busy) {
		LOG.info("AbstractServiceConsumer.setBusy: busy=" + busy);
		fireEvent(new BusyEvent(busy));
		BusyFeatureConsumer.get().call(busy);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		if (handlerManager != null)
			handlerManager.fireEvent(event);
	}

	@Override
	public HandlerRegistration addBusyHandler(BusyHandler handler) {
		if (handlerManager == null) handlerManager = new HandlerManager(this);
		return handlerManager.addHandler(BusyEvent.getType(), handler);
	}
}