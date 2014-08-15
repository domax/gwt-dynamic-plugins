#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
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
package ${package}.server.rest;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.gwt.dynamic.common.server.rest.AbstractServiceProvider;
import ${package}.shared.beans.SampleData;
import ${package}.shared.services.SampleServiceConst;

@Path("/" + SampleServiceConst.PATH)
public class SampleServiceProvider extends AbstractServiceProvider implements SampleServiceConst {

	private static final Logger LOG = Logger.getLogger(SampleServiceProvider.class.getName());
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SampleData getSampleData() {
		SampleData result = new SampleData("Just a Sample Header", "<p>First sample paragraph.</p> <p>Second one.</p>");
		LOG.fine("result=" + result);
		return result;
	}
}
