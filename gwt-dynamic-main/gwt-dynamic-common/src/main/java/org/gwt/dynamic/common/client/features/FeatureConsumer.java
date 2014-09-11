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
package org.gwt.dynamic.common.client.features;

import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class FeatureConsumer<A, R> extends AbstractFeature<A, R> {

	private static final Logger LOG = Logger.getLogger(FeatureConsumer.class.getName());
	
	@Override
	public void call(A arguments, AsyncCallback<R> callback) {
		String moduleName = getModuleName();
		String type = getType();
		LOG.fine("FeatureConsumer.call: moduleName=" + moduleName + "; type=" + type + "; arguments=" + arguments);
		if (isRegistered())
			doCall(moduleName, type, arguments, new ConsumerCallback<R>(callback));
		else if (callback != null) callback.onFailure(
				new RuntimeException("feature not supported: moduleName=" + moduleName + "; type=" + type));
	}

	private native void doCall(String moduleName, String featureName, A arguments, ConsumerCallback<R> callback) /*-{
		var a = $wnd.__features[moduleName][featureName];
		for (var i in a) {
			var f = a[i];
			f.callFunction.call(f.context, arguments,
				function (error) {
					callback.@org.gwt.dynamic.common.client.features.ConsumerCallback::onFailure(Ljava/lang/String;).call(callback, error);
				},
				function (result) {
					callback.@org.gwt.dynamic.common.client.features.ConsumerCallback::onSuccess(Ljava/lang/Object;).call(callback, result);
				});
		}
	}-*/;	
}
