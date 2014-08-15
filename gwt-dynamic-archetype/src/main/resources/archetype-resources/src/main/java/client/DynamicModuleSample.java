#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
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
package ${package}.client;

import java.util.logging.Logger;

import org.gwt.dynamic.common.client.features.ModuleContentFeatureProvider;
import org.gwt.dynamic.common.client.features.ModuleContentFeatureProvider.ViewHandler;
import org.gwt.dynamic.common.client.features.ModuleInfoFeatureProvider;
import org.gwt.dynamic.common.client.module.AbstractModule;
import ${package}.client.services.SampleServiceConsumer;
import ${package}.shared.beans.SampleData;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class DynamicModuleSample extends AbstractModule {

	private static final Logger LOG = Logger.getLogger(DynamicModuleSample.class.getName());
	
	private SampleContentView contentView;
	
	@Override
	protected void onModuleConfigured() {
		LOG.info("DynamicModuleSample.onModuleConfigured");
		
		new ModuleInfoFeatureProvider(
				"Sample Module", 
				"Just a working template for new dynamic plugin for this sample project.");
		
		new ModuleContentFeatureProvider(new ViewHandler() {
			@Override
			public Widget getView() {
				return getContentView();
			}
		});
		
		reportIsReady();
	}
	
	private SampleContentView getContentView() {
		if (contentView == null) {
			contentView = new SampleContentView();
			SampleServiceConsumer.get().getSampleData(new AsyncCallback<SampleData>() {
				
				@Override
				public void onSuccess(SampleData result) {
					LOG.info("DynamicModuleSample.getContentView${symbol_pound}getSampleData${symbol_pound}onSuccess: result=" + result);
					contentView.setSampleData(result);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					LOG.severe("DynamicModuleSample.getContentView${symbol_pound}getSampleData${symbol_pound}onFailure: " + caught.getMessage());
					contentView.setError("Error occurred during web-service call: " +  caught.getMessage());
				}
			});
		}
		return contentView;
	}
}
