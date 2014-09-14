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
package org.gwt.dynamic.module.foo.client;

import java.util.logging.Logger;

import org.gwt.dynamic.common.client.features.ModuleContentFeatureProvider;
import org.gwt.dynamic.common.client.features.ModuleContentFeatureProvider.ViewHandler;
import org.gwt.dynamic.common.client.features.ModuleInfoFeatureProvider;
import org.gwt.dynamic.common.client.module.AbstractModule;
import org.gwt.dynamic.module.foo.client.services.FooServiceConsumer;
import org.gwt.dynamic.module.foo.shared.FooData;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class DynamicModuleFoo extends AbstractModule {

	private static final Logger LOG = Logger.getLogger(DynamicModuleFoo.class.getName());
	
	private FooContentView contentView;
	
	@Override
	protected void onModuleConfigured() {
		LOG.info("DynamicModuleFoo.onModuleConfigured");
		
		new ModuleInfoFeatureProvider(
				"Lorem Ipsum Service", 
				"Generates \"Lorem ipsum\" text and loads it using REST web-services.");
		
		new ModuleContentFeatureProvider(new ViewHandler() {
			@Override
			public Widget getView() {
				return getContentView();
			}
		});
		
		reportIsReady();
	}
	
	private FooContentView getContentView() {
		if (contentView == null) {
			contentView = new FooContentView();
			FooServiceConsumer.get().getFooData(new AsyncCallback<FooData>() {
				
				@Override
				public void onSuccess(FooData result) {
					LOG.info("DynamicModuleFoo.getContentView#getFooData#onSuccess: result=" + result);
					contentView.setFooData(result);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					LOG.severe("DynamicModuleFoo.getContentView#getFooData#onFailure: " + caught.getMessage());
					contentView.setError("Error occurred during web-service call: " +  caught.getMessage());
				}
			});
		}
		return contentView;
	}
}
