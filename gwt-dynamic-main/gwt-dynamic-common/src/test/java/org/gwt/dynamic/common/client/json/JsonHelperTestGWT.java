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

import static java.util.Arrays.asList;
import static org.gwt.dynamic.common.shared.beans.BeanTestHelper.BEAN_A0;
import static org.gwt.dynamic.common.shared.beans.BeanTestHelper.BEAN_A1;
import static org.gwt.dynamic.common.shared.beans.BeanTestHelper.createBeanA;

import org.gwt.dynamic.common.client.util.JsonHelper;
import org.gwt.dynamic.common.shared.beans.BeanA;

import com.google.gwt.junit.client.GWTTestCase;

public class JsonHelperTestGWT extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "org.gwt.dynamic.common.DynamicCommon";
	}

	@Override
	protected void gwtSetUp() throws Exception {
		JsonRegistrar.registerBeans();
	}

	public void testBeanAWrite() {
		assertEquals(BEAN_A0, JsonHelper.toJson(createBeanA(0)));
	}
	
	public void testBeanARead() {
		assertEquals(createBeanA(0), JsonHelper.fromJsonBean(BeanA.class, BEAN_A0));
	}
	
	public void testBeanAListWrite() {
		assertEquals("[" + BEAN_A0 + "," + BEAN_A1 + "]", JsonHelper.toJson(asList(createBeanA(0), createBeanA(1))));
	}

	public void testBeanAListRead() {
		assertEquals(asList(createBeanA(0), createBeanA(1)),
				JsonHelper.fromJsonList(BeanA.class, "[" + BEAN_A0 + "," + BEAN_A1 + "]"));
	}
}
