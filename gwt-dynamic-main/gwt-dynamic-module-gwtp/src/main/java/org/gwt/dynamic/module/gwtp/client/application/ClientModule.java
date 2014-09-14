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

import org.gwt.dynamic.module.gwtp.client.application.content.ContentPresenter;
import org.gwt.dynamic.module.gwtp.client.application.content.ContentView;
import org.gwt.dynamic.module.gwtp.client.application.roles.RolesPresenter;
import org.gwt.dynamic.module.gwtp.client.application.roles.RolesView;

import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.gwtplatform.common.client.CommonGinModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
    install(new CommonGinModule());
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
    bind(PlaceManager.class).to(SilentPlaceManager.class).in(Singleton.class);
		bind(FeatureController.class).asEagerSingleton();
		
		bindSingletonPresenterWidget(
				ContentPresenter.class,
				ContentPresenter.MyView.class,
				ContentView.class);
		bindSingletonPresenterWidget(
				RolesPresenter.class,
				RolesPresenter.MyView.class,
				RolesView.class);
	}
}
