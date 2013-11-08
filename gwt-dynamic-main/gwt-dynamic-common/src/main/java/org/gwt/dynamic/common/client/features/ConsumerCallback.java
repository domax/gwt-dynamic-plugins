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

import com.google.gwt.user.client.rpc.AsyncCallback;

class ConsumerCallback<T> implements AsyncCallback<T> {
	
	final private AsyncCallback<T> sourceCallback;
	
	ConsumerCallback(AsyncCallback<T> sourceCallback) {
		this.sourceCallback = sourceCallback;
	}

	@Override
	public final void onFailure(Throwable caught) {
		if (sourceCallback != null) sourceCallback.onFailure(caught);
	}

	@Override
	public final void onSuccess(T result) {
		if (sourceCallback != null) sourceCallback.onSuccess(result);
	}
}
