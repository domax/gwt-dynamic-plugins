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

import org.gwt.dynamic.common.client.jso.ModuleInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ModuleInfoFeatureProvider extends FeatureProvider<Void, ModuleInfo>
		implements FeatureCommonConst {

	private static final Logger LOG = Logger.getLogger(ModuleInfoFeatureProvider.class.getName());

	private final String title;
	private final String desc;
	
	public ModuleInfoFeatureProvider(String title, String desc) {
		super(GWT.getModuleName());
		this.title = title;
		this.desc = desc;
		LOG.info("ModuleInfoFeatureProvider: moduleName=" + getModuleName() + "; title=" + title + "; desc=" + desc);
	}

	@Override
	public String getType() {
		return FEATURE_NAVIGATOR_ITEM;
	}

	@Override
	public void call(Void arguments, AsyncCallback<ModuleInfo> callback) {
		LOG.info("ModuleInfoFeatureProvider.call: moduleName=" + getModuleName());
		try {
			callback.onSuccess(ModuleInfo.create(getModuleName(), title, desc));
		} catch (Exception e) {
			callback.onFailure(e);
		}
	}
}
