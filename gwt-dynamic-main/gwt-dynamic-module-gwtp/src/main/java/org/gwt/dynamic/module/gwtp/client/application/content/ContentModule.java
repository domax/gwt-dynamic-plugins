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
package org.gwt.dynamic.module.gwtp.client.application.content;

import org.gwt.dynamic.module.gwtp.client.application.roles.RolesPresenter;
import org.gwt.dynamic.module.gwtp.client.application.roles.RolesView;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ContentModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
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