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
package org.gwt.dynamic.common.client.json;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.piriti.json.client.JsonWriter;

import org.gwt.dynamic.common.client.util.JsonHelper;
import org.gwt.dynamic.common.shared.beans.BeanA;
import org.gwt.dynamic.common.shared.beans.BeanB;
import org.gwt.dynamic.common.shared.beans.BeanC;

import com.google.gwt.core.client.GWT;

public final class JsonRegistrar {

	interface BeanAJsonWriter extends JsonWriter<BeanA> {}
	interface BeanAJsonReader extends JsonReader<BeanA> {}
	interface BeanBJsonWriter extends JsonWriter<BeanB> {}
	interface BeanBJsonReader extends JsonReader<BeanB> {}
	interface BeanCJsonWriter extends JsonWriter<BeanC> {}
	interface BeanCJsonReader extends JsonReader<BeanC> {}

	private static boolean registered;

	public static void registerBeans() {
		if (!registered) {
			JsonHelper.registerJsonBeanReaderWriter(BeanA.class,
					GWT.<BeanAJsonReader> create(BeanAJsonReader.class),
					GWT.<BeanAJsonWriter> create(BeanAJsonWriter.class));
			JsonHelper.registerJsonBeanReaderWriter(BeanB.class,
					GWT.<BeanBJsonReader> create(BeanBJsonReader.class),
					GWT.<BeanBJsonWriter> create(BeanBJsonWriter.class));
			JsonHelper.registerJsonBeanReaderWriter(BeanC.class,
					GWT.<BeanCJsonReader> create(BeanCJsonReader.class),
					GWT.<BeanCJsonWriter> create(BeanCJsonWriter.class));
			registered = true;
		}
	}

	private JsonRegistrar() {}
}
