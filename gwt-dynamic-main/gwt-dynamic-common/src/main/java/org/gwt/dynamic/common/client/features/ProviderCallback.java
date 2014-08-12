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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

class ProviderCallback<T> implements AsyncCallback<T> {
	
	final private JavaScriptObject failureFunction;
	final private JavaScriptObject successFunction;
	
	ProviderCallback(JavaScriptObject failureFunction, JavaScriptObject successFunction) {
		this.failureFunction = failureFunction;
		this.successFunction = successFunction;
	}

	@Override
	public final void onFailure(Throwable caught) {
		onFailure(caught != null ? caught.getMessage() : null);
	}
	
	private final native void onFailure(String caught) /*-{
		var f = this.@org.gwt.dynamic.common.client.features.ProviderCallback::failureFunction;
		if (typeof f == "function") f(caught);
	}-*/;

	@Override
	public final native void onSuccess(T result) /*-{
		var f = this.@org.gwt.dynamic.common.client.features.ProviderCallback::successFunction;
		if (typeof f == "function") f(result);
	}-*/;
}
