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
package org.gwt.dynamic.module.foo.client;

import java.util.logging.Logger;

import org.gwt.dynamic.module.foo.shared.beans.FooData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class FooContentView extends Composite {

	private static final Logger LOG = Logger.getLogger(FooContentView.class.getName());

	private static FooContentViewUiBinder uiBinder = GWT.create(FooContentViewUiBinder.class);

	interface FooContentViewUiBinder extends UiBinder<Widget, FooContentView> {}

	public interface InnerCss extends CssResource {

		String loading();

		String error();
	}

	@UiField public static InnerCss style;
	@UiField HTML content;

	public FooContentView() {
		initWidget(uiBinder.createAndBindUi(this));
		LOG.info("FooContentView: instantiated");
	}

	void setFooData(FooData fooData) {
		LOG.info("FooContentView.setFooData: fooData=" + fooData);
		SafeHtmlBuilder shb = new SafeHtmlBuilder();
		shb.appendHtmlConstant("<h1>").appendEscaped(fooData.getHeader()).appendHtmlConstant("</h1>");
		shb.appendHtmlConstant(fooData.getHtml());
		content.setHTML(shb.toSafeHtml());
		content.removeStyleName(style.loading());
	}

	void setError(String message) {
		LOG.info("FooContentView.setError: message=" + message);
		content.setText(message);
		content.removeStyleName(style.loading());
		content.addStyleName(style.error());
	}
}
