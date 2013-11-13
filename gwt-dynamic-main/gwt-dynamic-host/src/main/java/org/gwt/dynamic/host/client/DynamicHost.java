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
package org.gwt.dynamic.host.client;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.gwt.dynamic.host.client.features.BusyFeatureProvider;
import org.gwt.dynamic.host.client.features.ModuleReadyFeatureProvider;
import org.gwt.dynamic.host.client.features.NavigatorItemFeatureConsumer;
import org.gwt.dynamic.host.client.main.MainLayout;
import org.gwt.dynamic.host.client.module.ModuleLoader;
import org.gwt.dynamic.host.client.services.ModuleServiceConsumer;
import org.gwt.dynamic.host.shared.beans.ModuleBean;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class DynamicHost implements EntryPoint {

	private static final Logger LOG = Logger.getLogger(DynamicHost.class.getName());
	private static final int LOAD_TIMEOUT = 10;
	
	private final BusyFeatureProvider busyFeatureProvider = new BusyFeatureProvider();
	private MainLayout mainLayout;

	@Override
	public void onModuleLoad() {
		LOG.info("DynamicHost.onModuleLoad");
		mainLayout = new MainLayout();
		busyFeatureProvider.addBusyHandler(mainLayout);
		RootLayoutPanel.get().add(mainLayout);
		loadModules();
	}
	
	private void loadModules() {
		busyFeatureProvider.setBusy(true);
		ModuleServiceConsumer.get().getModules(new AsyncCallback<List<ModuleBean>>() {

			@Override
			public void onSuccess(List<ModuleBean> modules) {
				LOG.info("DynamicHost.onModuleLoad#getModules#onSuccess: modules=" + modules);
				final ModuleReadyFeatureProvider moduleReadyFeatureProvider = 
						new ModuleReadyFeatureProvider(modules, LOAD_TIMEOUT); 
				moduleReadyFeatureProvider.addLoadHandler(new LoadHandler() {
					@Override
					public void onLoad(LoadEvent event) {
						LOG.info("DynamicHost.onModuleLoad#ModuleReadyFeatureProvider.onLoad: unreadyModules=" 
								+ moduleReadyFeatureProvider.getUnreadyModules());
						busyFeatureProvider.setBusy(false);
						onModulesLoaded(moduleReadyFeatureProvider.getReadyModules());
					}
				});
				ModuleLoader.INSTANCE.load(modules);
			}

			@Override
			public void onFailure(Throwable caught) {
				LOG.log(Level.SEVERE, "DynamicHost.onModuleLoad#getModules#onFailure:", caught);
				busyFeatureProvider.setBusy(false);
				onModulesLoaded(null);
			}
		});
	}
	
	private void onModulesLoaded(List<ModuleBean> modules) {
		LOG.info("DynamicHost.onModulesLoaded: modules=" + modules);
		new NavigatorItemFeatureConsumer(modules).call(new AsyncCallback<List<SafeHtml>>() {

			@Override
			public void onFailure(Throwable caught) {
				LOG.log(Level.SEVERE, "DynamicHost.onModulesLoaded#NavigatorItemFeatureConsumer.call#onFailure:", caught);
			}

			@Override
			public void onSuccess(List<SafeHtml> result) {
				LOG.info("DynamicHost.onModulesLoaded#NavigatorItemFeatureConsumer.call#onSuccess");
				mainLayout.setNavigationItems(result);
			}
		});
	}
}
