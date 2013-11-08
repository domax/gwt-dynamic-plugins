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
package org.gwt.dynamic.common.client.util;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsDate;

import java.util.Date;

public final class JsUtils {

	/**
	 * Assigns arbitrary property in given JSO as {@link JavaScriptObject} value.
	 * 
	 * @param jso
	 *          JSO to be modified
	 * @param name
	 *          Property name that should be assigned/changed
	 * @param value
	 *          New property value
	 */
	public static native void setProperty(JavaScriptObject jso, String name, JavaScriptObject value) /*-{
		if (jso != null && name != null && name.length > 0)
			jso[name] = value;
	}-*/;

	/**
	 * Assigns arbitrary property in given JSO as String value.
	 * 
	 * @param jso
	 *          JSO to be modified
	 * @param name
	 *          Property name that should be assigned/changed
	 * @param value
	 *          New property value
	 */
	public static native void setProperty(JavaScriptObject jso, String name, String value) /*-{
		if (jso != null && name != null && name.length > 0)
			jso[name] = value;
	}-*/;

	/**
	 * Assigns arbitrary property in given JSO as number value.
	 * 
	 * @param jso
	 *          JSO to be modified
	 * @param name
	 *          Property name that should be assigned/changed
	 * @param value
	 *          New property value
	 */
	public static native void setProperty(JavaScriptObject jso, String name, double value) /*-{
		if (jso != null && name != null && name.length > 0)
			jso[name] = value;
	}-*/;

	/**
	 * Assigns arbitrary property in given JSO as {@link Date} value.
	 * 
	 * @param jso
	 *          JSO to be modified
	 * @param name
	 *          Property name that should be assigned/changed
	 * @param value
	 *          New property value
	 */
	public static void setProperty(JavaScriptObject jso, String name, Date value) {
		if (value != null)
			setProperty(jso, name, JsDate.create(value.getTime()));
	}

	/**
	 * Returns arbitrary property from given JSO by specified name as String value.
	 * 
	 * @param jso
	 *          JSO where from property should be given
	 * @param name
	 *          Addressed property name
	 * @return {@link JavaScriptObject} value of specified property or null if specified property name is undefined.
	 */
	public static native JavaScriptObject getPropertyJSO(JavaScriptObject jso, String name) /*-{
		return jso != null && name != null && name.length > 0 ? jso[name] : null;
	}-*/;

	/**
	 * Returns arbitrary property from given JSO by specified name as String value.
	 * 
	 * @param jso
	 *          JSO where from property should be given
	 * @param name
	 *          Addressed property name
	 * @return String value of specified property or null if specified property name is undefined.
	 */
	public static native String getPropertyString(JavaScriptObject jso, String name) /*-{
		return jso != null && name != null && name.length > 0 ? String(jso[name]) : null;
	}-*/;

	/**
	 * Returns arbitrary property from given JSO by specified name as double value.
	 * 
	 * @param jso
	 *          JSO where from property should be given
	 * @param name
	 *          Addressed property name
	 * @return {@link JavaScriptObject} value of specified property or null if specified property name is undefined.
	 */
	public static native double getPropertyNumber(JavaScriptObject jso, String name) /*-{
		return jso != null && name != null && name.length > 0 && jso[name] != null ? Number(jso[name]) : NaN;
	}-*/;
	
	/**
	 * Returns arbitrary property from given JSO by specified name as {@link JsDate} value.
	 * 
	 * @param jso
	 *          JSO where from property should be given
	 * @param name
	 *          Addressed property name
	 * @return {@link JavaScriptObject} value of specified property or null if specified property name is undefined.
	 */
	public static native JsDate getPropertyJsDate(JavaScriptObject jso, String name) /*-{
		if (jso == null || name == null || name.length <= 0 || jso[name] == null) return null;
		var d = jso[name];
		if (!(d instanceof Date)) d = new Date(jso[name]);
		if (isNaN(d.getTime())) d = new Date(Number(jso[name]));
		return isNaN(d.getTime()) ? null : d;
	}-*/;

	/**
	 * Returns arbitrary property from given JSO by specified name as {@link Date} value.
	 * 
	 * @param jso
	 *          JSO where from property should be given
	 * @param name
	 *          Addressed property name
	 * @return {@link JavaScriptObject} value of specified property or null if specified property name is undefined.
	 */
	public static Date getPropertyDate(JavaScriptObject jso, String name) {
		JsDate d = getPropertyJsDate(jso, name);
		return d != null ? new Date((long) d.getTime()) : null;
	}
	
	private JsUtils() {}
}
