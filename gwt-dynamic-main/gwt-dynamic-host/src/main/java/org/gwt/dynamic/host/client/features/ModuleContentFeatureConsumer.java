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
package org.gwt.dynamic.host.client.features;

import java.util.logging.Logger;

import org.gwt.dynamic.common.client.features.FeatureCommonConst;
import org.gwt.dynamic.common.client.features.FeatureConsumer;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ModuleContentFeatureConsumer extends FeatureConsumer<Element, Void> implements FeatureCommonConst {

	private static final Logger LOG = Logger.getLogger(ModuleContentFeatureConsumer.class.getName());

	private String moduleName;
	
	@Override
	public String getModuleName() {
		return moduleName;
	}

	public void call(String moduleName, Element contentElement, AsyncCallback<Void> callback) {
		LOG.info("ModuleContentFeatureConsumer.call: moduleName=" + moduleName);
		this.moduleName = moduleName;
		super.call(contentElement, callback);
	}
	
	@Override
	public String getType() {
		return FEATURE_CONTENT;
	}
}
