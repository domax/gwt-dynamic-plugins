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

import org.gwt.dynamic.common.client.features.ModuleContentFeatureProvider;
import org.gwt.dynamic.common.client.features.ModuleContentFeatureProvider.ViewHandler;
import org.gwt.dynamic.common.client.features.ModuleInfoFeatureProvider;
import org.gwt.dynamic.common.client.module.AbstractModule;
import org.gwt.dynamic.module.bar.client.demo.CircleCollisionPane;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.Widget;

public class DynamicModuleBar extends AbstractModule {

	private static final Logger LOG = Logger.getLogger(DynamicModuleBar.class.getName());
	
	private CircleCollisionPane contentView;
	
	@Override
	protected void onModuleConfigured() {
		LOG.info("DynamicModuleBar.onModuleConfigured");
		
		new ModuleInfoFeatureProvider(
				"Circle Collisions Demo", 
				"A demo of my gwt-phys2d project: gravitation + circle objects collisions. Simple but meditative ;)");

		new ModuleContentFeatureProvider(new ViewHandler() {
			@Override
			public Widget getView() {
				return getContentView();
			}
		});
		
		reportIsReady();
	}
	
	private CircleCollisionPane getContentView() {
		if (contentView == null) {
			contentView = new CircleCollisionPane();
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					contentView.start();
				}
			});
		} else contentView.reset();
		return contentView;
	}
}
