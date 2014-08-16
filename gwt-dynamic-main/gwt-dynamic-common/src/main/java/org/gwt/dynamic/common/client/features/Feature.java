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

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Describes API for inter-module data exchange.
 * <p>
 * <b>CAUTION!</b> Both generics must be only a primitive type (String, int/Integer, double/Double) or JavaScriptObject
 * derivative.
 * </p>
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 * 
 * @param <A>
 *          Type of arguments.
 * @param <R>
 *          Type of results.
 */
interface Feature<A, R> {

	/**
	 * Defines module name this feature related on. Should be unique per whole application.
	 * 
	 * @return A module name.
	 */
	String getModuleName();

	/**
	 * Defines feature name for specified module. Should be unique per module.
	 * 
	 * @return A feature name.
	 */
	String getType();

	/**
	 * Gets the status of readiness.
	 * 
	 * @return {@code true} if this feature was registered (see {@link FeatureProvider#register()}), or {@code false} if
	 *         it wasn't or was unregistered (see {@link FeatureProvider#unregister()}).
	 */
	boolean isRegistered();

	/**
	 * Defines the logic of this feature.<br>
	 * For provider side this method must have user-defined code that performs needed business logic.<br>
	 * For consumer side - it is pre-defined to invoke it's pair from provider.
	 * 
	 * @param arguments
	 *          An object that holds optional arguments for this feature.
	 * @param callback
	 *          A structure that passes returned results or related exception.
	 */
	void call(A arguments, AsyncCallback<R> callback);
}
