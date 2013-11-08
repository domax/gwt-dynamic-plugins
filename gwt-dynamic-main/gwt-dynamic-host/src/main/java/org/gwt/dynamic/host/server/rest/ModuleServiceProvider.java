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
package org.gwt.dynamic.host.server.rest;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.gwt.dynamic.common.server.rest.AbstractServiceProvider;
import org.gwt.dynamic.common.shared.Utils;
import org.gwt.dynamic.host.shared.services.ModuleService;

@Path("/")
public class ModuleServiceProvider extends AbstractServiceProvider implements ModuleService {
	
	private static final String PKEY_MODULES = "modules";

	@GET
	@Path("/modules")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<String> getModules() {
		String modules = PROPS.getProperty(PKEY_MODULES);
		return Utils.isHollow(modules) ? null : Arrays.asList(modules.split("\\s*,\\s*"));
	}
}
