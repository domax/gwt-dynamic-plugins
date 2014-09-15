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
package org.gwt.dynamic.common.server;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class CrossDomainFilter implements ContainerResponseFilter {

	/**
	 * Add the cross domain data to the output if needed
	 * 
	 * @param creq
	 *          The container request (input)
	 * @param cres
	 *          The container request (output)
	 * @return The output request with cross domain if needed
	 */
	@Override
	public ContainerResponse filter(ContainerRequest creq, ContainerResponse cres) {
		cres.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
		cres.getHttpHeaders().add("Access-Control-Allow-Headers",
				"Accept, X-HTTP-Method-Override, Content-Type, Origin, Authorization");
		cres.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
		cres.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		cres.getHttpHeaders().add("Access-Control-Max-Age", "1209600");
		return cres;
	}
}