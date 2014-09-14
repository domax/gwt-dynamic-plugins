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
package org.gwt.dynamic.module.gwtp.client.services;

import java.util.Set;
import java.util.logging.Logger;

import org.fusesource.restygwt.client.MethodCallback;
import org.gwt.dynamic.common.client.services.AbstractServiceConsumer;
import org.gwt.dynamic.module.gwtp.shared.RoleBean;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RoleServiceConsumer extends AbstractServiceConsumer<RoleRestService> {

	private static final Logger LOG = Logger.getLogger(RoleServiceConsumer.class.getName());
	
	private static RoleServiceConsumer instance;
	
	public static RoleServiceConsumer get() {
		if (instance == null) instance = new RoleServiceConsumer();
		return instance;
	}

	private RoleServiceConsumer() {
		super(GWT.<RoleRestService> create(RoleRestService.class));
		LOG.info("RoleServiceConsumer: instantiated");
	}

	public void getRoles(AsyncCallback<Set<RoleBean>> callback) {
		LOG.info("RoleServiceConsumer.getFooData");
		new Requestor<Set<RoleBean>>() {
			@Override
			protected void callRest(RoleRestService rest, MethodCallback<Set<RoleBean>> callback) {
				rest.getRoles(callback);
			}
		}.call(callback);
	}
}
