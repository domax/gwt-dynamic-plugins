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
package org.gwt.dynamic.common.client.features;

import java.util.Date;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FeatureTestGWT extends GWTTestCase {
	
	static final String FEATURE_TYPE_A = "FeatureA";
	static final String MODULE_NAME_TEST = "TestModule";
	static final String ARGS_A_SUCC = "test success";
	static final String ARGS_A_FAIL = "test failure";

	private FeatureAProvider provider;
	private FeatureAConsumer consumer;
	
	public static FeatureAResult createFeatureAResult() {
		FeatureAResult bean = FeatureAResult.create();
		bean.setName("FeatureAResult Test Name");
		bean.setDate(new Date(1375197319493l));
		return bean;
	}
	
	@Override
	public String getModuleName() {
		return "org.gwt.dynamic.common.DynamicCommon";
	}

	@Override
	protected void gwtSetUp() throws Exception {
		consumer = new FeatureAConsumer();
		provider = null;
	}

	@Override
  protected void gwtTearDown() throws Exception {
		if (provider != null) provider.unregister();
  }
	
	public void testFeatureASuccess() {
		assertFalse(consumer.isRegistered());
		provider = new FeatureAProvider();
		assertTrue(consumer.isRegistered());
		consumer.call(ARGS_A_SUCC, new AsyncCallback<FeatureAResult>() {
			
			@Override
			public void onSuccess(FeatureAResult result) {
				assertNotNull(result);
				FeatureAResult expected = createFeatureAResult();
				assertEquals(expected.getName(), result.getName());
				assertNotNull(result.getDate());
				assertEquals(expected.getDate(), result.getDate());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				fail("Throwable: " + caught.getMessage());
			}
		});
	}
	
	public void testFeatureAFailure() {
		assertFalse(consumer.isRegistered());
		provider = new FeatureAProvider();
		assertTrue(consumer.isRegistered());
		consumer.call(ARGS_A_FAIL, new AsyncCallback<FeatureAResult>() {
			
			@Override
			public void onSuccess(FeatureAResult result) {
				fail("Result: " + result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				assertEquals(ARGS_A_FAIL, caught.getMessage());
			}
		});
	}
	
	public void testFeatureAUnregistered() {
		assertFalse(consumer.isRegistered());
		consumer.call(ARGS_A_SUCC, new AsyncCallback<FeatureAResult>() {
			
			@Override
			public void onSuccess(FeatureAResult result) {
				fail("Result: " + result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				assertTrue(caught instanceof RuntimeException);
				assertTrue(caught.getMessage().contains("not supported"));
			}
		});
	}
}
