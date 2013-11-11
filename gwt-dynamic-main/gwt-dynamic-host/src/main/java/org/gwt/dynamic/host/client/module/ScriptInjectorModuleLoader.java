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
package org.gwt.dynamic.host.client.module;

import java.util.List;
import java.util.logging.Logger;

import org.gwt.dynamic.common.shared.Utils;
import org.gwt.dynamic.host.shared.beans.ModuleBean;

import com.google.gwt.core.client.ScriptInjector;

public class ScriptInjectorModuleLoader implements ModuleLoader {

	private static final Logger LOG = Logger.getLogger(ScriptInjectorModuleLoader.class.getName());

	@Override
	public void load(String urlBase, List<ModuleBean> modules) {
		LOG.info("ScriptInjectorModuleLoader.load: urlBase=" + urlBase + "; modules=" + modules);
		if (Utils.isEmpty(modules)) return;
		for (ModuleBean module : modules)
			ScriptInjector.fromUrl(urlBase
					+ module.getUrl() + "/" + module.getName() + "/" + module.getName() + ".nocache.js")
					.setRemoveTag(true)
					.setWindow(ScriptInjector.TOP_WINDOW)
					.inject();
	}
}
