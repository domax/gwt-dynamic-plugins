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

import org.gwt.dynamic.host.shared.beans.ModuleBean;

import com.google.gwt.core.shared.GWT;

public interface ModuleLoader {

	public static final ModuleLoader INSTANCE = GWT.create(ModuleLoader.class);

	void load(String urlBase, List<ModuleBean> modules);
}
