/*
 * Copyright 2013 Maxim Dominichenko
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
package org.gwt.dynamic.common.client.features;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiRenderer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class NavigatorItemFeatureProvider extends FeatureProvider<Void, String>
		implements FeatureCommonConst {

	private static final Logger LOG = Logger.getLogger(NavigatorItemFeatureProvider.class.getName());
	private static final NavigatorItemUiRenderer R = GWT.create(NavigatorItemUiRenderer.class);
	
	interface NavigatorItemUiRenderer extends UiRenderer {
		void render(SafeHtmlBuilder sb, String title, String desc);
	}

	private final String title;
	private final String desc;
	
	public NavigatorItemFeatureProvider(String title, String desc) {
		super(GWT.getModuleName());
		this.title = title;
		this.desc = desc;
		LOG.info("NavigatorItemFeatureProvider: moduleName=" + getModuleName() + "; title=" + title + "; desc=" + desc);
	}

	@Override
	public String getType() {
		return FEATURE_NAVIGATOR_ITEM;
	}

	@Override
	public void call(Void arguments, AsyncCallback<String> callback) {
		LOG.info("NavigatorItemFeatureProvider.call: moduleName=" + getModuleName());
		try {
			SafeHtmlBuilder builder = new SafeHtmlBuilder();
			R.render(builder, title, desc);
			callback.onSuccess(builder.toSafeHtml().asString());
		} catch (Exception e) {
			callback.onFailure(e);
		}
	}
}
