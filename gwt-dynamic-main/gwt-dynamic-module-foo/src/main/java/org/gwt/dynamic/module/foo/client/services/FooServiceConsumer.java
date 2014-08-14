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
package org.gwt.dynamic.module.foo.client.services;

import java.util.logging.Logger;

import org.fusesource.restygwt.client.MethodCallback;
import org.gwt.dynamic.common.client.services.AbstractServiceConsumer;
import org.gwt.dynamic.module.foo.shared.beans.FooData;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FooServiceConsumer extends AbstractServiceConsumer<FooRestService> {

	private static final Logger LOG = Logger.getLogger(FooServiceConsumer.class.getName());
	
	private static FooServiceConsumer instance;
	
	public static FooServiceConsumer get() {
		if (instance == null) instance = new FooServiceConsumer();
		return instance;
	}

	private FooServiceConsumer() {
		super(GWT.<FooRestService> create(FooRestService.class));
		LOG.info("FooServiceConsumer: instantiated");
	}

	public void getFooData(AsyncCallback<FooData> callback) {
		LOG.info("FooServiceConsumer.getFooData");
		new Requestor<FooData>() {
			@Override
			protected void callRest(FooRestService rest, MethodCallback<FooData> callback) {
				rest.getFooData(callback);
			}
		}.call(callback);
	}
}
