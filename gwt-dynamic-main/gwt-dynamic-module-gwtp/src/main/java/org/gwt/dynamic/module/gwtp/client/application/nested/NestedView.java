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
package org.gwt.dynamic.module.gwtp.client.application.nested;

import java.util.logging.Logger;

import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class NestedView extends ViewWithUiHandlers<NestedUiHandlers>
implements NestedPresenter.MyView {

	private static final Logger LOG = Logger.getLogger(NestedView.class.getName());

	interface Binder extends UiBinder<Widget, NestedView> {}

	public interface InnerCss extends CssResource {}

	@UiField public static InnerCss style;
	@UiField HTML content;

	@Inject
	public NestedView(final Binder binder) {
		initWidget(binder.createAndBindUi(this));
		LOG.info("NestedView: instantiated");
	}
}
