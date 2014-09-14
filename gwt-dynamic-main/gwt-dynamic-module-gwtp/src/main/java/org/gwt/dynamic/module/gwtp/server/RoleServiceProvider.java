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
package org.gwt.dynamic.module.gwtp.server;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.gwt.dynamic.common.server.rest.AbstractServiceProvider;
import org.gwt.dynamic.module.gwtp.shared.RoleBean;
import org.gwt.dynamic.module.gwtp.shared.RoleServiceConst;

@Path("/" + RoleServiceConst.PATH)
public class RoleServiceProvider extends AbstractServiceProvider implements RoleServiceConst {

	private static final Logger LOG = Logger.getLogger(RoleServiceProvider.class.getName());

	/**
	 * Source: http://www.skillsyouneed.com/ips/group-roles.html
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Set<RoleBean> getRoles() {
		LOG.info("getRoles");
		Set<RoleBean> result = new HashSet<>();
		result.add(new RoleBean("Team Roles", null));
		result.add(new RoleBean("Shaper", 
				new HashSet<>(Arrays.asList("dynamic", "argumentative", "provocative", "impatient"))));
		result.add(new RoleBean("Implementer", 
				new HashSet<>(Arrays.asList("conscientious", "practical", "organised"))));
		result.add(new RoleBean("Completer", 
				new HashSet<>(Arrays.asList("task-orientated", "anxious", "perfectionist"))));
		result.add(new RoleBean("Coordinator", 
				new HashSet<>(Arrays.asList("calm", "positive", "charismatic", "communicative"))));
		result.add(new RoleBean("Team Worker", 
				new HashSet<>(Arrays.asList("popular", "able to negotiate", "indecisive"))));
		result.add(new RoleBean("Resource Investigator", 
				new HashSet<>(Arrays.asList("communicator", "able to negotiate", "curious", "sociable", "open", "flexible", "innovative", "unrealistic"))));
		result.add(new RoleBean("Plant", 
				new HashSet<>(Arrays.asList("intellectual", "individualistic", "innovative", "creative", "impracticable", "introvert", "poor communication", "loner"))));
		result.add(new RoleBean("Monitor Evaluator", 
				new HashSet<>(Arrays.asList("clever", "unemotional", "decision maker", "critical thinker"))));
		result.add(new RoleBean("Specialist", 
				new HashSet<>(Arrays.asList("expert", "narrow", "single-minded", "professional"))));
		LOG.fine("getRoles: result=" + result);
		return result;
	}

}
