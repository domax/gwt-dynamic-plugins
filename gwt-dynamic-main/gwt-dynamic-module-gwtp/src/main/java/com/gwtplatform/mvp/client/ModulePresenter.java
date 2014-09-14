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
package com.gwtplatform.mvp.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.web.bindery.event.shared.EventBus;

public class ModulePresenter<V extends View> extends PresenterWidget<V> {

	private static final Logger LOG = Logger.getLogger(ModulePresenter.class.getName());
	
	public ModulePresenter(EventBus eventBus, V view) {
		super(eventBus, view);
		init();
		LOG.info("ModulePresenter.instantiated");
	}

	public ModulePresenter(boolean autoBind, EventBus eventBus, V view) {
		super(autoBind, eventBus, view);
		init();
		LOG.info("ModulePresenter.instantiated");
	}
	
	private void init() {
		LOG.info("ModulePresenter.init");
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				internalReveal();
			}
		});
	}
	
	public void reveal() {
		LOG.info("ModulePresenter.reveal");
		internalReveal();
	}
	
	public void hide() {
		LOG.info("ModulePresenter.hide");
		internalHide();
	}
	
	public void reset() {
		LOG.info("ModulePresenter.reset");
		internalReset();
	}
}
