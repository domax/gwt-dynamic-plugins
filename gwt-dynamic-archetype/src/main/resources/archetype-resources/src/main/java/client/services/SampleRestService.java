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
package ${package}.client.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import ${package}.shared.beans.SampleData;
import ${package}.shared.services.SampleServiceConst;

@Path(SampleServiceConst.PATH)
public interface SampleRestService extends RestService, SampleServiceConst {

	@GET
	public void getSampleData(MethodCallback<SampleData> callback);
}
