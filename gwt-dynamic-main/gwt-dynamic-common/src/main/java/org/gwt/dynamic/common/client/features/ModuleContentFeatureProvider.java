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
package org.gwt.dynamic.common.client.features;

import java.util.logging.Logger;

import org.gwt.dynamic.common.client.widgets.WrapPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class ModuleContentFeatureProvider extends FeatureProvider<Element, Void> implements FeatureCommonConst {

	private static final Logger LOG = Logger.getLogger(ModuleContentFeatureProvider.class.getName());
	private final ViewHandler viewHandler;
	
	public static interface ViewHandler {
		Widget getView();
	}
	
	public ModuleContentFeatureProvider(ViewHandler viewHandler) {
		super(GWT.getModuleName());
		this.viewHandler = viewHandler;
		LOG.info("ModuleContentFeatureProvider: instantiated");
	}

	@Override
	public String getType() {
		return FEATURE_CONTENT;
	}

	@Override
	public void call(Element contentElement, AsyncCallback<Void> callback) {
		LOG.info("ModuleContentFeatureProvider.call: moduleName=" + getModuleName());
		try {
			if (contentElement == null) throw new IllegalArgumentException("Content element should be specified");
			if (viewHandler == null) throw new IllegalStateException("View Handler wasn't defined");
			Widget w = viewHandler.getView();
			if (w == null) throw new IllegalStateException("View wasn't defined");
			
			if (contentElement.getChildCount() > 0) {
				LOG.fine("ModuleContentFeatureProvider.call: moduleName=" + getModuleName() + "; content exists");
				if (callback != null) callback.onSuccess(null);
				return;
			}
			
			WrapPanel.wrap(contentElement).add(w);
			
			LOG.fine("ModuleContentFeatureProvider.call: moduleName=" + getModuleName() + "; content created");
			if (callback != null) callback.onSuccess(null);
		} catch (Exception e) {
			LOG.severe("ModuleContentFeatureProvider.call: " + e.getMessage());
			if (callback != null) callback.onFailure(e);
		}
	}
}
