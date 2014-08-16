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

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsDate;

public class FeatureAResult extends JavaScriptObject {

	public static final native FeatureAResult create() /*-{
		return {
			"toString" : function() { return JSON.stringify(this); }
		};
	}-*/;

	protected FeatureAResult() {}
	
	public final native String getName() /*-{
		return this.name;
	}-*/;
	
	public final native void setName(String name) /*-{
		this.name = name;
	}-*/;
	
	public final Date getDate() {
		JsDate result = date();
		return result != null ? new Date((long) result.getTime()) : null;
	}
	
	public final void setDate(Date date) {
		date(date != null ? JsDate.create(date.getTime()) : null);
	}
	
	public final native JsDate date() /*-{
		return this.date;
	}-*/;
	
	public final native void date(JsDate date) /*-{
		this.date = date;
	}-*/;
}
