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

public abstract class AbstractFeature<A, R> implements Feature<A, R> {

	public static native boolean isRegistered(String moduleName, String featureName) /*-{
		return !!$wnd.__features 
				&& !!$wnd.__features[moduleName] 
				&& !!$wnd.__features[moduleName][featureName] 
				&& typeof $wnd.__features[moduleName][featureName].callFunction == "function";
	}-*/;

	@Override
	public boolean isRegistered() {
		return isRegistered(getModuleName(), getType());
	}
}
