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
package org.gwt.dynamic.common.client.module;

import static org.gwt.dynamic.common.client.features.FeatureCommonConst.FEATURE_MODULE_READY;
import static org.gwt.dynamic.common.client.features.FeatureCommonConst.MODULE_HOST;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.gwt.dynamic.common.client.features.SimpleFeatureConsumer;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;

public abstract class AbstractModule implements EntryPoint {

	private static final Logger LOG = Logger.getLogger(AbstractModule.class.getName());

	@Override
	public final void onModuleLoad() {
		LOG.info("AbstractModule.onModuleLoad: moduleName=" + GWT.getModuleName());
		ScriptInjector.fromUrl(GWT.getModuleBaseURL() + "/config.js")
				.setRemoveTag(true)
				.setWindow(ScriptInjector.TOP_WINDOW)
				.setCallback(new Callback<Void, Exception>() {

					@Override
					public void onSuccess(Void result) {
						LOG.info("AbstractModule.onModuleLoad#fromUrl#onSuccess");
						onModuleConfigured();
					}

					@Override
					public void onFailure(Exception reason) {
						LOG.log(Level.SEVERE, "AbstractModule.onModuleLoad#fromUrl#onFailure:", reason);
					}
				})
				.inject();
	}
	
	protected void reportIsReady() {
		LOG.info("AbstractModule.reportIsReady: moduleName=" + GWT.getModuleName());
		new SimpleFeatureConsumer<String, Void>(MODULE_HOST, FEATURE_MODULE_READY).call(GWT.getModuleName(), null);
	}

	protected abstract void onModuleConfigured();
}
