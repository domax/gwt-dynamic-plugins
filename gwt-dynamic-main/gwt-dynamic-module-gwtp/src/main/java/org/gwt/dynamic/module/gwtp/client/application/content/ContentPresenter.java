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

import org.gwt.dynamic.module.gwtp.client.application.nested.NestedPresenter;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.ModulePresenter;
import com.gwtplatform.mvp.client.View;

public class ContentPresenter extends ModulePresenter<ContentPresenter.MyView> implements ContentUiHandlers {

	private static final Logger LOG = Logger.getLogger(ContentPresenter.class.getName());
	public static final Object SLOT_NESTED = new Object();
	
	public interface MyView extends View, HasUiHandlers<ContentUiHandlers> {}
	
	private final NestedPresenter nestedPresenter;
	
	@Inject
	public ContentPresenter(final EventBus eventBus, final MyView view, NestedPresenter nestedPresenter) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.nestedPresenter = nestedPresenter;
		LOG.info("ContentPresenter: instantiated");
	}
	
	@Override
	protected void onBind() {
		super.onBind();
		LOG.info("ContentPresenter.onBind");
		setInSlot(SLOT_NESTED, nestedPresenter);
	}
	
	@Override
	protected void onReveal() {
		super.onReveal();
		LOG.info("ContentPresenter.onReveal");
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		LOG.info("ContentPresenter.onReset");
	}
}
