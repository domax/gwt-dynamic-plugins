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
package org.gwt.dynamic.common.client.features;

class FeatureAConsumer extends FeatureConsumer<String, FeatureAResult> {

	@Override
	public String getType() {
		return FeatureTestGWT.FEATURE_TYPE_A;
	}

	@Override
	public String getModuleName() {
		return FeatureTestGWT.MODULE_NAME_TEST;
	}
}
