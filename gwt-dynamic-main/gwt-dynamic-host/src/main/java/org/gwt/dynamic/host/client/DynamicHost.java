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
import org.gwt.dynamic.host.client.main.MainLayout;
import org.gwt.dynamic.host.client.services.ModuleServiceConsumer;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class DynamicHost implements EntryPoint {

	private static final Logger LOG = Logger.getLogger(DynamicHost.class.getName());
	
	@Override
	public void onModuleLoad() {
		LOG.info("DynamicHost.onModuleLoad");
		MainLayout mainLayout = new MainLayout();
		BusyFeatureProvider busyFeatureProvider = new BusyFeatureProvider();
		busyFeatureProvider.addBusyHandler(mainLayout);
		RootLayoutPanel.get().add(mainLayout);
		
		ModuleServiceConsumer.get().getModules(new AsyncCallback<List<String>>() {
			
			@Override
			public void onSuccess(List<String> result) {
				LOG.info("DynamicHost.onModuleLoad#getModules#onSuccess: result=" + result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				LOG.log(Level.SEVERE, "DynamicHost.onModuleLoad#getModules#onFailure:", caught);
			}
		});
	}
}
