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
package org.gwt.dynamic.common.client.features;

import java.util.logging.Logger;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class FeatureProvider<A, R> extends AbstractFeature<A, R> {

	private static final Logger LOG = Logger.getLogger(FeatureProvider.class.getName());
	
	private final String moduleName;

	public FeatureProvider(String moduleName) {
		this.moduleName = moduleName;
		register();
	}

	@Override
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * Prepares needed structures in memory to provide a bridge between provider and consumer sides.
	 */
	public void register() {
		String moduleName = getModuleName();
		String featureName = getType();
		LOG.info("register: moduleName='" + moduleName + "' type=" + featureName);
		register(moduleName, featureName);
	}

	/**
	 * Removes all structures prepared by {@link #register()} method.
	 */
	public void unregister() {
		String moduleName = getModuleName();
		String featureName = getType();
		LOG.info("unregister: moduleName='" + moduleName + "' type=" + featureName);
		if (isRegistered()) unregister(moduleName, featureName);
	}

	private native void register(String moduleName, String featureName) /*-{
		if (!$wnd.__features) $wnd.__features = {};
		if (!$wnd.__features[moduleName]) $wnd.__features[moduleName] = {};
		if (!$wnd.__features[moduleName][featureName]) $wnd.__features[moduleName][featureName] = {};
		$wnd.__features[moduleName][featureName].callFunction = 
				$entry(this.@org.gwt.dynamic.common.client.features.FeatureProvider::doCall(Ljava/lang/Object;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;));
		$wnd.__features[moduleName][featureName].context = this;
	}-*/;

	private native void unregister(String moduleName, String featureName) /*-{
		$wnd.__features[moduleName][featureName].callFunction = null;
		$wnd.__features[moduleName][featureName].context = null;
	}-*/;
	
	final private void doCall(A arguments, JavaScriptObject failureFunction, JavaScriptObject successFunction) {
		LOG.fine("doCall: moduleName='" + getModuleName() + "' type=" + getType() + "; arguments=" + arguments);
		call(arguments, new ProviderCallback<R>(failureFunction, successFunction));
	}
}
