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

import java.util.logging.Logger;

public class BusyFeatureConsumer extends FeatureConsumer<String, Void> implements FeatureCommonConst {
	
	private static final Logger LOG = Logger.getLogger(BusyFeatureConsumer.class.getName());
	
	private static BusyFeatureConsumer instance;
	
	public static BusyFeatureConsumer get() {
		if (instance == null)
			instance = new BusyFeatureConsumer();
		return instance;
	}

	private BusyFeatureConsumer() {
		LOG.info("BusyFeatureConsumer: instantiated");
	}
	
	@Override
	public String getType() {
		return FEATURE_BUSY;
	}

	@Override
	public String getModuleName() {
		return MODULE_HOST;
	}
	
	public void call(boolean busy) {
		LOG.info("BusyFeatureConsumer.call: busy=" + busy);
		call(Boolean.toString(busy), null);
	}
}
