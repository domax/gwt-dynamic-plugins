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
package org.gwt.dynamic.host.client.features;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.gwt.dynamic.common.client.features.FeatureCommonConst;
import org.gwt.dynamic.common.client.features.FeatureConsumer;
import org.gwt.dynamic.common.client.util.BatchRunner;
import org.gwt.dynamic.common.client.util.BatchRunner.CommandSimple;
import org.gwt.dynamic.host.shared.beans.ModuleBean;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class NavigatorItemFeatureConsumer extends FeatureConsumer<Void, String> implements FeatureCommonConst {

	private static final Logger LOG = Logger.getLogger(NavigatorItemFeatureConsumer.class.getName());
	
	private final List<ModuleBean> modules;
	private final List<SafeHtml> navigatorItems = new ArrayList<SafeHtml>();
	private String moduleName;

	private class ItemCommand extends CommandSimple {

		private final String module;

		ItemCommand(String module) {
			this.module = module;
		}

		@Override
		public void run(final AsyncCallback<Void> callback) {
			moduleName = module;
			call(null, new AsyncCallback<String>() {

				@Override
				public void onSuccess(String result) {
					LOG.info("NavigatorItemFeatureConsume.ItemCommand.run#call#onSuccess: result=" + result);
					navigatorItems.add(SafeHtmlUtils.fromSafeConstant(result));
					callback.onSuccess(null);
				}

				@Override
				public void onFailure(Throwable caught) {
					LOG.severe("NavigatorItemFeatureConsume.ItemCommand.run#call#onFailure: " + caught.getMessage());
					navigatorItems.add(SafeHtmlUtils.fromSafeConstant("<div></div>"));
					callback.onFailure(caught);
				}
			});
		}
	}
	
	public NavigatorItemFeatureConsumer(List<ModuleBean> modules) {
		this.modules = modules;
		LOG.info("NavigatorItemFeatureConsumer: instantiated");
	} 
	
	@Override
	public String getModuleName() {
		return moduleName;
	}

	@Override
	public String getType() {
		return FEATURE_NAVIGATOR_ITEM;
	}
	
	public void call(final AsyncCallback<List<SafeHtml>> callback) {
		LOG.info("NavigatorItemFeatureConsumer.call: modules=" + modules);
		navigatorItems.clear();
		BatchRunner batchRunner = new BatchRunner(BatchRunner.Mode.SEQUENTIAL) {
			@Override
			public void onFinish() {
				if (callback != null) callback.onSuccess(navigatorItems);
			}
		};
		if (modules != null)
			for (ModuleBean module : modules)
				batchRunner.add(new ItemCommand(module.getName()));
		batchRunner.run();
	}
}