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

import static org.gwt.dynamic.common.client.util.JsUtils.toStringJSO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.gwt.dynamic.common.client.features.FeatureCommonConst;
import org.gwt.dynamic.common.client.features.FeatureConsumer;
import org.gwt.dynamic.common.client.jso.ModuleInfo;
import org.gwt.dynamic.common.client.util.BatchRunner;
import org.gwt.dynamic.common.client.util.BatchRunner.CommandSimple;
import org.gwt.dynamic.host.shared.ModuleBean;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ModuleInfoFeatureConsumer extends FeatureConsumer<Void, ModuleInfo> implements FeatureCommonConst {

	private static final Logger LOG = Logger.getLogger(ModuleInfoFeatureConsumer.class.getName());
	
	private final List<ModuleBean> modules;
	private final List<ModuleInfo> navigatorItems = new ArrayList<ModuleInfo>();
	private String moduleName;

	private class ItemCommand extends CommandSimple {

		private final String module;

		ItemCommand(String module) {
			this.module = module;
		}

		@Override
		public void run(final AsyncCallback<Void> callback) {
			moduleName = module;
			call(null, new AsyncCallback<ModuleInfo>() {

				@Override
				public void onSuccess(ModuleInfo result) {
					LOG.info("NavigatorItemFeatureConsume.ItemCommand.run#call#onSuccess: result=" + toStringJSO(result));
					navigatorItems.add(result);
					callback.onSuccess(null);
				}

				@Override
				public void onFailure(Throwable caught) {
					LOG.severe("NavigatorItemFeatureConsume.ItemCommand.run#call#onFailure: " + caught.getMessage());
					navigatorItems.add(ModuleInfo.create(module, module, module));
					callback.onSuccess(null);
//					callback.onFailure(caught);
				}
			});
		}
	}
	
	public ModuleInfoFeatureConsumer(List<ModuleBean> modules) {
		this.modules = modules;
		LOG.info("ModuleInfoFeatureConsumer: instantiated");
	} 
	
	@Override
	public String getModuleName() {
		return moduleName;
	}

	@Override
	public String getType() {
		return FEATURE_NAVIGATOR_ITEM;
	}
	
	public void call(final AsyncCallback<List<ModuleInfo>> callback) {
		LOG.info("ModuleInfoFeatureConsumer.call: modules=" + modules);
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