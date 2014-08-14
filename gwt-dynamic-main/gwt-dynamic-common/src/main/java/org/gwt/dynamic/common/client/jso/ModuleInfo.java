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
package org.gwt.dynamic.common.client.jso;

import com.google.gwt.core.client.JavaScriptObject;

public class ModuleInfo extends JavaScriptObject {

	public static final ModuleInfo create() {
		return JavaScriptObject.createObject().cast();
	}
	
	public static final ModuleInfo create(String name, String caption, String description) {
		ModuleInfo result = create();
		result.setName(name);
		result.setCaption(caption);
		result.setDescription(description);
		return result;
	}
	
	protected ModuleInfo() {}
	
	public final native String getName() /*-{
		return this.name;
	}-*/;
	
	public final native void setName(String name) /*-{
		this.name = name;
	}-*/; 

	public final native String getCaption() /*-{
		return this.caption;
	}-*/;
	
	public final native void setCaption(String caption) /*-{
		this.caption = caption;
	}-*/; 

	public final native String getDescription() /*-{
		return this.description;
	}-*/;
	
	public final native void setDescription(String description) /*-{
		this.description = description;
	}-*/; 
}
