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

import java.util.logging.Logger;

import org.fusesource.restygwt.client.MethodCallback;
import org.gwt.dynamic.common.client.services.AbstractServiceConsumer;
import ${package}.shared.beans.SampleData;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SampleServiceConsumer extends AbstractServiceConsumer<SampleRestService> {

	private static final Logger LOG = Logger.getLogger(SampleServiceConsumer.class.getName());
	
	private static SampleServiceConsumer instance;
	
	public static SampleServiceConsumer get() {
		if (instance == null) instance = new SampleServiceConsumer();
		return instance;
	}

	private SampleServiceConsumer() {
		super(GWT.<SampleRestService> create(SampleRestService.class));
		LOG.info("SampleServiceConsumer: instantiated");
	}

	public void getSampleData(AsyncCallback<SampleData> callback) {
		LOG.info("SampleServiceConsumer.getSampleData");
		new Requestor<SampleData>() {
			@Override
			protected void callRest(SampleRestService rest, MethodCallback<SampleData> callback) {
				rest.getSampleData(callback);
			}
		}.call(callback);
	}
}
