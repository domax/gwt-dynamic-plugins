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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.gwt.dynamic.common.server.rest.AbstractServiceProvider;
import org.gwt.dynamic.common.shared.Utils;
import org.gwt.dynamic.host.shared.beans.ModuleBean;
import org.gwt.dynamic.host.shared.services.ModuleServiceConst;

@Path("/")
public class ModuleServiceProvider extends AbstractServiceProvider implements ModuleServiceConst {

	private static final Logger LOG = Logger.getLogger(ModuleServiceProvider.class.getName());
	
	private final ObjectMapper mapper = new ObjectMapper();

	@GET
	@Path("/" + PKEY_MODULES)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ModuleBean> getModules() {
		String modules = PROPS.getProperty(PKEY_MODULES);
		if (Utils.isHollow(modules)) return null;
		try {
			return mapper.readValue(modules, new TypeReference<List<ModuleBean>>() {});
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "ModuleServiceProvider.getModules:", e);
			return null;
		}
	}
}
