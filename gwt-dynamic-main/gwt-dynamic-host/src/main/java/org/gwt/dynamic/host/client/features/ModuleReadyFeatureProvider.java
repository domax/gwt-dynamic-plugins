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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.gwt.dynamic.common.client.features.FeatureCommonConst;
import org.gwt.dynamic.common.client.features.FeatureProvider;
import org.gwt.dynamic.common.shared.Utils;
import org.gwt.dynamic.host.shared.beans.ModuleBean;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.HasLoadHandlers;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ModuleReadyFeatureProvider extends FeatureProvider<String, Void>
		implements FeatureCommonConst, HasLoadHandlers {

	private static final Logger LOG = Logger.getLogger(ModuleReadyFeatureProvider.class.getName());
	
	private final List<ModuleBean> moduleReadyList = new ArrayList<ModuleBean>();
	private final List<ModuleBean> moduleUnreadyList = new ArrayList<ModuleBean>();
	private final HandlerManager handlerManager;
	private final Timer timeoutTimer = new Timer() {
		@Override
		public void run() {
			DomEvent.fireNativeEvent(Document.get().createLoadEvent(), ModuleReadyFeatureProvider.this);
		}
	};

	public ModuleReadyFeatureProvider(List<ModuleBean> modules, int timeoutSeconds) {
		super(MODULE_HOST);
		if (Utils.isEmpty(modules))
			throw new IllegalArgumentException("Module list cannot be empty");
		moduleUnreadyList.addAll(modules);
		handlerManager = new HandlerManager(this);
		if (timeoutSeconds <= 0) timeoutSeconds = 1;
		timeoutTimer.schedule(timeoutSeconds * 1000);
		LOG.info("ModuleReadyFeatureProvider: instantiated");
	}

	@Override
	public String getType() {
		return FEATURE_MODULE_READY;
	}

	@Override
	public void call(String moduleName, AsyncCallback<Void> callback) {
		LOG.info("ModuleReadyFeatureProvider.call: moduleName=" + moduleName);
		for (ModuleBean moduleBean : moduleUnreadyList)
			if (Utils.equals(moduleName, moduleBean.getName())) {
				moduleReadyList.add(moduleBean);
				moduleUnreadyList.remove(moduleBean);
				if (moduleUnreadyList.isEmpty()) {
					timeoutTimer.cancel();
					DomEvent.fireNativeEvent(Document.get().createLoadEvent(), this);
				}
				return;
			}
		throw new IllegalArgumentException("Module with name '" + moduleName + "' isn't expected");
	}

	public List<ModuleBean> getReadyModules() {
		return moduleReadyList;
	}

	public List<ModuleBean> getUnreadyModules() {
		return moduleUnreadyList;
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		handlerManager.fireEvent(event);
	}

	@Override
	public HandlerRegistration addLoadHandler(LoadHandler handler) {
		return handlerManager.addHandler(LoadEvent.getType(), handler);
	}
}
