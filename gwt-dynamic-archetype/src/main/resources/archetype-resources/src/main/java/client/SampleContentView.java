#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
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
package ${package}.client;

import java.util.logging.Logger;

import ${package}.shared.beans.SampleData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class SampleContentView extends Composite {

	private static final Logger LOG = Logger.getLogger(SampleContentView.class.getName());

	private static SampleContentViewUiBinder uiBinder = GWT.create(SampleContentViewUiBinder.class);

	interface SampleContentViewUiBinder extends UiBinder<Widget, SampleContentView> {}

	public interface InnerCss extends CssResource {

		String loading();

		String error();
	}

	@UiField public static InnerCss style;
	@UiField HTML content;

	public SampleContentView() {
		initWidget(uiBinder.createAndBindUi(this));
		LOG.info("SampleContentView: instantiated");
	}

	void setSampleData(SampleData sampleData) {
		LOG.info("SampleContentView.setSampleData: sampleData=" + sampleData);
		SafeHtmlBuilder shb = new SafeHtmlBuilder();
		shb.appendHtmlConstant("<h1>").appendEscaped(sampleData.getHeader()).appendHtmlConstant("</h1>");
		shb.appendHtmlConstant(sampleData.getHtml());
		content.setHTML(shb.toSafeHtml());
		content.removeStyleName(style.loading());
	}

	void setError(String message) {
		LOG.info("SampleContentView.setError: message=" + message);
		content.setText(message);
		content.removeStyleName(style.loading());
		content.addStyleName(style.error());
	}
}
