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

import java.util.Set;
import java.util.logging.Logger;

import org.gwt.dynamic.module.gwtp.client.application.PresenterWidgetFactory;
import org.gwt.dynamic.module.gwtp.client.application.roles.RolesPresenter;
import org.gwt.dynamic.module.gwtp.client.event.RolesLoadedEvent;
import org.gwt.dynamic.module.gwtp.client.services.RoleServiceConsumer;
import org.gwt.dynamic.module.gwtp.shared.RoleBean;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.ModulePresenter;
import com.gwtplatform.mvp.client.View;

public class ContentPresenter extends ModulePresenter<ContentPresenter.MyView> implements ContentUiHandlers {

	private static final Logger LOG = Logger.getLogger(ContentPresenter.class.getName());
	public static final Object SLOT_NESTED = new Object();
	
	public interface MyView extends View, HasUiHandlers<ContentUiHandlers> {
		
		void setError(String error);
		
		void setBanner(String banner);
	}
	
	private final PresenterWidgetFactory<RolesPresenter> rolesPresenterFactory;
	
	@Inject
	public ContentPresenter(final EventBus eventBus, final MyView view, 
			PresenterWidgetFactory<RolesPresenter> rolesPresenterFactory) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.rolesPresenterFactory = rolesPresenterFactory;
		LOG.info("ContentPresenter: instantiated");
	}
	
	@Override
	protected void onBind() {
		super.onBind();
		LOG.info("ContentPresenter.onBind");
		rolesPresenterFactory.get(new AsyncCallback<RolesPresenter>() {
			
			@Override
			public void onSuccess(RolesPresenter result) {
				LOG.info("ContentPresenter.rolesPresenterFactory#get#onSuccess");
				setInSlot(SLOT_NESTED, result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				LOG.severe("ContentPresenter.rolesPresenterFactory#get#onFailure: caught=" + caught.getMessage());
				getView().setError("Error occurred: " + caught.getMessage());
			}
		});
	}
	
	@Override
	protected void onReveal() {
		super.onReveal();
		LOG.info("ContentPresenter.onReveal");
		getView().setBanner("Loading role data...");
		RoleServiceConsumer.get().getRoles(new AsyncCallback<Set<RoleBean>>() {
			
			@Override
			public void onSuccess(Set<RoleBean> result) {
				LOG.info("ContentPresenter#getRoles#onFailure");
				LOG.fine("ContentPresenter#getRoles#onFailure: result=" + result);
				getView().setBanner(null);
				RolesLoadedEvent.fire(ContentPresenter.this, result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				LOG.severe("ContentPresenter#getRoles#onFailure: caught=" + caught.getMessage());
				getView().setError("Error occurred: " + caught.getMessage());
			}
		});
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		LOG.info("ContentPresenter.onReset");
	}
}
