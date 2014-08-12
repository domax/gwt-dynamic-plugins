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
package org.gwt.dynamic.module.bar.client;

import java.util.logging.Logger;

import org.gwt.dynamic.common.client.features.NavigatorItemFeatureProvider;
import org.gwt.dynamic.common.client.module.AbstractModule;

public class DynamicModuleBar extends AbstractModule {

	private static final Logger LOG = Logger.getLogger(DynamicModuleBar.class.getName());
	
	@Override
	protected void onModuleConfigured() {
		LOG.info("DynamicModuleBar.onModuleConfigured");
		
		new NavigatorItemFeatureProvider(
				"Module Bar", 
				"Invokes Module Bar stuff from dynamcally loaded plugin, that is deployed as separate webapp");
		
		reportIsReady();
	}
}
