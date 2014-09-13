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
package org.gwt.dynamic.module.gwtp.client;

import java.util.logging.Logger;

import org.gwt.dynamic.common.client.module.AbstractModule;

import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.ApplicationController;

public class DynamicModuleGWTP extends AbstractModule {

	private static final Logger LOG = Logger.getLogger(DynamicModuleGWTP.class.getName());
	private static final ApplicationController APP_CTRLR = GWT.create(ApplicationController.class);
	
	@Override
	protected void onModuleConfigured() {
		LOG.info("DynamicModuleGWTP.onModuleConfigured");
		APP_CTRLR.init();
		reportIsReady();
	}
}
