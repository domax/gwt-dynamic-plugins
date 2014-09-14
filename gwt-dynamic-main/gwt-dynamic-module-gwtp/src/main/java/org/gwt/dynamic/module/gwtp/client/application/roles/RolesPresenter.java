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
package org.gwt.dynamic.module.gwtp.client.application.roles;

import java.util.Set;
import java.util.logging.Logger;

import org.gwt.dynamic.module.gwtp.client.event.RolesLoadedEvent;
import org.gwt.dynamic.module.gwtp.shared.RoleBean;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class RolesPresenter extends PresenterWidget<RolesPresenter.MyView> 
implements RolesUiHandlers, RolesLoadedEvent.Handler {

	private static final Logger LOG = Logger.getLogger(RolesPresenter.class.getName());
	
	public interface MyView extends View, HasUiHandlers<RolesUiHandlers> {
		
		void setData(Set<RoleBean> roles);
		
		void update();
	}
	
	@Inject
	public RolesPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		LOG.info("RolesPresenter: instantiated");
	}
	
	@Override
	protected void onBind() {
		super.onBind();
		LOG.info("RolesPresenter.onBind");
		addRegisteredHandler(RolesLoadedEvent.getType(), this);
	}
	
	@Override
	protected void onReveal() {
		super.onReveal();
		LOG.info("RolesPresenter.onReveal");
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		LOG.info("RolesPresenter.onReset");
		getView().update();
	}

	@Override
	public void onRolesLoaded(RolesLoadedEvent event) {
		LOG.fine("RolesPresenter.onRolesLoaded: event.roles=" + event.getRoles());
		getView().setData(event.getRoles());
	}
}
