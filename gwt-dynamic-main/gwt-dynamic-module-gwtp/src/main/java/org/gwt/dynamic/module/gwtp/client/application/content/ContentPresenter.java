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

import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class ContentPresenter extends PresenterWidget<ContentPresenter.MyView> implements ContentUiHandlers {

	private static final Logger LOG = Logger.getLogger(ContentPresenter.class.getName());
	
	public interface MyView extends View, HasUiHandlers<ContentUiHandlers> {
		
		void setError(String message);
		
		void setMessage(String message);
	}
	
	@Inject
	public ContentPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		LOG.info("ContentPresenter: instantiated");
	}
	
	@Override
	protected void onBind() {
		super.onBind();
		LOG.info("ContentPresenter.onBind");
		getView().setMessage("Binded");
	}
	
	@Override
	public void onReveal() {
		super.onReveal();
		LOG.info("ContentPresenter.onReveal");
		getView().setMessage("Revealed");
	}
	
	@Override
	public void onReset() {
		super.onReset();
		LOG.info("ContentPresenter.onReset");
		getView().setMessage("Reset");
	}
	
	@Override
	public void onHide() {
		super.onHide();
		LOG.info("ContentPresenter.onHide");
		getView().setMessage("Hidden");
	}
}
