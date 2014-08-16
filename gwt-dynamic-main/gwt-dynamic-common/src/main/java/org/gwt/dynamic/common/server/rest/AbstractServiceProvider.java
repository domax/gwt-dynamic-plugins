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
package org.gwt.dynamic.common.server.rest;

import java.util.Properties;
import java.util.logging.Logger;

public abstract class AbstractServiceProvider {

	private static final Logger LOG = Logger.getLogger(AbstractServiceProvider.class.getName());
	protected static final Properties PROPS = new Properties();
	
	public AbstractServiceProvider() {
		if (PROPS.isEmpty()) {
			String className = getClass().getSimpleName();
			String propertiesPath = getClass().getName().replaceAll("\\.", "/") + ".properties";
			try {
				PROPS.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesPath));
				LOG.info(className + ": properties=" + PROPS);
			} catch (Exception e) {
				LOG.warning(className + ": Properties '" + propertiesPath + "' initialization failed: " + e.getMessage());
			}
			if (PROPS.isEmpty()) PROPS.setProperty("", "");
		}
	}
}
