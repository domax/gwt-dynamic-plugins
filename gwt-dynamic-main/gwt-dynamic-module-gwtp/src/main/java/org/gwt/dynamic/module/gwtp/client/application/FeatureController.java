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
package org.gwt.dynamic.module.gwtp.client.application;

import java.util.logging.Logger;

import org.gwt.dynamic.common.client.features.FeatureCommonConst;
import org.gwt.dynamic.common.client.features.ModuleContentFeatureProvider;
import org.gwt.dynamic.common.client.features.ModuleContentFeatureProvider.ViewHandler;
import org.gwt.dynamic.common.client.features.ModuleInfoFeatureProvider;
import org.gwt.dynamic.module.gwtp.client.application.content.ContentPresenter;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ModulePresenter;
import com.gwtplatform.mvp.client.View;

@Singleton
public class FeatureController implements FeatureCommonConst {

	private static final Logger LOG = Logger.getLogger(FeatureController.class.getName());
	
	@Inject Provider<ContentPresenter> contentPresenterProvider;
	
	@Inject
	public FeatureController(EventBus eventBus) {
		new ModuleInfoFeatureProvider("GWTP Module", 
				"This module is built using GWTP framework. The graph in nested widget is just for fun ;)");
		new ModuleContentFeatureProvider(new ViewHandler() {
			@Override
			public Widget getView() {
				return updateModulePresenter(contentPresenterProvider.get()).asWidget();
			}
		});
		LOG.info("FeatureController: instantiated");
	}
	
	private <V extends View> ModulePresenter<V> updateModulePresenter(final ModulePresenter<V> presenter) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				if (!presenter.isVisible()) presenter.reveal();
				presenter.reset();
			}
		});
		return presenter;
	}
}
