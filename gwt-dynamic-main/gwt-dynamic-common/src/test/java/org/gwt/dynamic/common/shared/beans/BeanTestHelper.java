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
package org.gwt.dynamic.common.shared.beans;

import java.util.ArrayList;
import java.util.Date;

public final class BeanTestHelper {

	public static final long TS_LONG = 1375197319493l;
	public static final String TS_STRING = "2013-07-30T18:15:19.493+03:00";

	public static final String BEAN_C1 = "{\"name\":\"beanC/1\",\"permissions\":[\"perm1\"]}";
	public static final String BEAN_C2 = "{\"name\":\"beanC/2\",\"permissions\":[\"perm1\",\"perm2\"]}";
	public static final String BEAN_C3 = "{\"name\":\"beanC/3\",\"permissions\":[\"perm1\",\"perm2\",\"perm3\"]}";
	public static final String BEAN_C4 = "{\"name\":\"beanC/4\",\"permissions\":[\"perm1\",\"perm2\",\"perm3\",\"perm4\"]}";
	
	public static final String BEAN_A0 =
			"{\"name\":\"beanA0\",\"ts\":\"" + TS_STRING + "\","
					+ "\"values\":["
					+ "{\"value1\":1.2,\"value2\":3.4,\"role\":" + BEAN_C2 + "},"
					+ "{\"value1\":5.6,\"value2\":7.8,\"role\":" + BEAN_C3 + "}"
					+ "]}";
	
	public static final String BEAN_A1 =
			"{\"name\":\"beanA1\",\"ts\":\"" + TS_STRING + "\","
					+ "\"values\":["
					+ "{\"value1\":2.2,\"value2\":4.4,\"role\":" + BEAN_C3 + "},"
					+ "{\"value1\":6.6,\"value2\":8.8,\"role\":" + BEAN_C4 + "}"
					+ "]}";
	
	public static BeanA createBeanA(int num) {
		BeanA beanA = new BeanA();
		beanA.name = "beanA" + num;
		beanA.ts = new Date(TS_LONG);
		beanA.values = new ArrayList<BeanB>();
		
		BeanB beanB;
		
		beanB = new BeanB();
		beanB.value1 = 1.2 + num;
		beanB.value2 = 3.4 + num;
		beanB.role = createBeanC(2 + num);
		beanA.values.add(beanB);
		
		beanB = new BeanB();
		beanB.value1 = 5.6 + num;
		beanB.value2 = 7.8 + num;
		beanB.role = createBeanC(3 + num);
		beanA.values.add(beanB);
		
		return beanA;
	}

	public static BeanC createBeanC(int count) {
		BeanC bean = new BeanC();
		bean.name = "beanC/" + count;
		bean.permissions = new ArrayList<String>(count > 0 ? count : 0);
		for (int i = 0; i < count; ++i)
			bean.permissions.add("perm" + (i + 1));
		return bean;
	}

	private BeanTestHelper() {}
}
