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
package org.gwt.dynamic.common.client.util;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.piriti.json.client.JsonWriter;

import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public final class JsonHelper {
	
	private static final Logger LOG = Logger.getLogger(JsonHelper.class.getName());
	private static final HashMap<String, JsonWriter<?>> JSON_WRITERS = new HashMap<String, JsonWriter<?>>();
	private static final HashMap<String, JsonReader<?>> JSON_READERS = new HashMap<String, JsonReader<?>>();

	public static void registerJsonBeanReaderWriter(Class<?> clazz, JsonReader<?> jsonReader, JsonWriter<?> jsonWriter) {
		if (clazz == null) return;
		if (jsonReader != null) {
			JSON_READERS.put(clazz.getName(), jsonReader);
			LOG.info("registerJsonBeanReaderWriter: registered new reader for class " + clazz.getName());
		}
		if (jsonWriter != null) {
			JSON_WRITERS.put(clazz.getName(), jsonWriter);
			LOG.info("registerJsonBeanReaderWriter: registered new writer for class " + clazz.getName());
		}
	}

	public static boolean isJsonReaderRegisteredFor(Class<?> clazz) {
		if (clazz == null) return false;
		return JSON_READERS.containsKey(clazz.getName());
	}
	
	public static boolean isJsonWriterRegisteredFor(Class<?> clazz) {
		if (clazz == null) return false;
		return JSON_WRITERS.containsKey(clazz.getName());
	}
	
	@SuppressWarnings("unchecked")
	private static <T> JsonWriter<T> getWriterForBean(T bean) {
		if (bean == null) throw new IllegalArgumentException("Bean to be serialized cannot be a null");
		JsonWriter<T> writer = (JsonWriter<T>) JSON_WRITERS.get(bean.getClass().getName());
		LOG.info("getWriterForBean: bean.class.name=" + bean.getClass().getName() + "; writer=" + writer);
		if (writer == null) throw new IllegalArgumentException("JsonWriter for specified bean isn't registered");
		return writer;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> JsonReader<T> getReaderForBean(Class<T> beanClass) {
		if (beanClass == null) throw new IllegalArgumentException("Bean class to be deserialized cannot be a null");
		JsonReader<T> reader = (JsonReader<T>) JSON_READERS.get(beanClass.getName());
		LOG.info("getReaderForBean: beanClass.name=" + beanClass.getName() + "; reader=" + reader);
		if (reader == null) throw new IllegalArgumentException("JsonReader for specified bean class isn't registered");
		return reader;
	}
	
	public static <T> String toJson(T bean) {
		return getWriterForBean(bean).toJson(bean);
	}
	
	public static <T> String toJson(List<T> list) {
		if (list == null) throw new IllegalArgumentException("Bean list to be serialized cannot be a null");
		if (list.isEmpty()) return "[]";
		return getWriterForBean(list.get(0)).toJson(list, "items")
				.replaceFirst("^\\{\"items\":", "").replaceFirst("\\}$", "");
	}

	public static <T> T fromJsonBean(Class<T> beanClass, String json) {
		JSONValue value = JSONParser.parseStrict(json);
		return getReaderForBean(beanClass).read(value.isObject());
	}
	
	public static <T> List<T> fromJsonList(Class<T> beanClass, String json) {
		JSONValue value = JSONParser.parseStrict(json);
		return getReaderForBean(beanClass).readList(value.isArray());
	}
	
	public static <T> T fromJsonBean(Class<T> beanClass, JSONValue json) {
		if (json == null) throw new NullPointerException();
		return getReaderForBean(beanClass).read(json.isObject());
	}
	
	public static <T> List<T> fromJsonList(Class<T> beanClass, JSONValue json) {
		if (json == null) throw new NullPointerException();
		return getReaderForBean(beanClass).readList(json.isArray());
	}
	
	private JsonHelper() {}
}
