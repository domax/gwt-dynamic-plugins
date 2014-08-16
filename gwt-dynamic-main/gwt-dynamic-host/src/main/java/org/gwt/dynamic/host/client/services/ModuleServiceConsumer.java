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
package org.gwt.dynamic.host.client.services;

import java.util.List;
import java.util.logging.Logger;

import org.fusesource.restygwt.client.MethodCallback;
import org.gwt.dynamic.common.client.services.AbstractServiceConsumer;
import org.gwt.dynamic.host.shared.beans.ModuleBean;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ModuleServiceConsumer extends AbstractServiceConsumer<ModuleRestService> {
	
	private static final Logger LOG = Logger.getLogger(ModuleServiceConsumer.class.getName());
	
	private static ModuleServiceConsumer instance;
	
	public static ModuleServiceConsumer get() {
		if (instance == null) instance = new ModuleServiceConsumer();
		return instance;
	}

	private ModuleServiceConsumer() {
		super(GWT.<ModuleRestService> create(ModuleRestService.class));
		LOG.info("ModuleServiceConsumer: instantiated");
	}

	public void getModules(AsyncCallback<List<ModuleBean>> callback) {
		LOG.info("ModuleServiceConsumer.getModules");
		new Requestor<List<ModuleBean>>() {
			@Override
			protected void callRest(ModuleRestService rest, MethodCallback<List<ModuleBean>> callback) {
				rest.getModules(callback);
			}
		}.call(callback);
	}
}
