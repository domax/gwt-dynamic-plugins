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

import java.util.logging.Logger;

public class SimpleFeatureConsumer<A, R> extends FeatureConsumer<A, R> {

	private static final Logger LOG = Logger.getLogger(SimpleFeatureConsumer.class.getName());
	
	private final String featureType;
	private final String moduleName;

	public SimpleFeatureConsumer(String featureType, String moduleName) {
		LOG.info("SimpleFeatureConsumer: featureType=" + featureType + "; moduleName=" + moduleName);
		this.featureType = featureType;
		this.moduleName = moduleName;
	}

	@Override
	public String getType() {
		return featureType;
	}

	@Override
	public String getModuleName() {
		return moduleName;
	}
}
