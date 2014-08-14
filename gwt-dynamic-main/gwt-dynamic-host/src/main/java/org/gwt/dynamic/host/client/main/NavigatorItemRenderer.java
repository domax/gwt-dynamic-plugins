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
package org.gwt.dynamic.host.client.main;

import org.gwt.dynamic.common.client.jso.ModuleInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiRenderer;

public class NavigatorItemRenderer {

	private static final NavigatorItemUiRenderer R = GWT.create(NavigatorItemUiRenderer.class);

	interface NavigatorItemUiRenderer extends UiRenderer {
		void render(SafeHtmlBuilder sb, String title, String desc);
	}

	public static void render(SafeHtmlBuilder sb, ModuleInfo value) {
		if (value == null) return;
		R.render(sb, value.getCaption(), value.getDescription());
	}

	private NavigatorItemRenderer() {}
}
