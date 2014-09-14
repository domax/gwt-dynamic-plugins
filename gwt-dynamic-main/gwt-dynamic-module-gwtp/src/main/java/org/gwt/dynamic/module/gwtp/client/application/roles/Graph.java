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
package org.gwt.dynamic.module.gwtp.client.application.roles;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

final class Graph extends JavaScriptObject {

	static final class Node extends JavaScriptObject {
		
		static native Node create() /*-{
			return {
				"toString" : function() { return JSON.stringify(this); }
			};
		}-*/;

		static Node create(String name, int color) {
			Node result = create();
			result.name(name);
			result.color(color);
			return result;
		}
		
		static Node create(String name) {
			Node result = create();
			result.name(name);
			return result;
		}
		
		static Node create(Node node) {
			return create(node.name(), node.color());
		}
		
		protected Node() {}
		
		native String name() /*-{
			return this.name;
		}-*/;
		
		native void name(String name) /*-{
			this.name = name;
		}-*/;
		
		native int color() /*-{
			return this.color;
		}-*/;
		
		native void color(int color) /*-{
			this.color = color;
		}-*/;
		
		native boolean hasColor() /*-{
			return typeof this.color != 'undefined';
		}-*/;
	}
	
	static final class Link extends JavaScriptObject {
		
		static native Link create() /*-{
			return {
				"toString" : function() { return JSON.stringify(this); }
			};
		}-*/;

		static Link create(int source, int target, int group) {
			Link result = create();
			result.source(source);
			result.target(target);
			result.group(group);
			return result;
		}
		
		protected Link() {}
		
		native int source() /*-{
			return this.source;
		}-*/;
		
		native void source(int source) /*-{
			this.source = source;
		}-*/;
		
		native int target() /*-{
			return this.target;
		}-*/;
		
		native void target(int target) /*-{
			this.target = target;
		}-*/;
		
		native int group() /*-{
			return this.group;
		}-*/;
		
		native void group(int group) /*-{
			this.group = group;
		}-*/;
	}
	
	private static native Graph create0() /*-{
		return {
			"toString" : function() { return JSON.stringify(this); }
		};
	}-*/;
	
	static Graph create() {
		Graph result = create0();
		result.nodes(JavaScriptObject.createArray().<JsArray<Node>> cast());
		result.links(JavaScriptObject.createArray().<JsArray<Link>> cast());
		result.nodeRoles(JavaScriptObject.createArray().<JsArray<Node>> cast());
		result.nodeLabels(JavaScriptObject.createArray().<JsArray<Node>> cast());
		result.nodeSkills(JavaScriptObject.createArray().<JsArray<Node>> cast());
		return result;
	}
	
	protected Graph() {}
	
	native JsArray<Node> nodes() /*-{
		return this.nodes;
	}-*/;
	
	native void nodes(JsArray<Node> nodes) /*-{
		this.nodes = nodes;
	}-*/;
	
	native JsArray<Link> links() /*-{
		return this.links;
	}-*/;
	
	native void links(JsArray<Link> links) /*-{
		this.links = links;
	}-*/;
	
	native JsArray<Node> nodeRoles() /*-{
		return this.nodeRoles;
	}-*/;
	
	native void nodeRoles(JsArray<Node> nodeRoles) /*-{
		this.nodeRoles = nodeRoles;
	}-*/;
	
	native JsArray<Node> nodeLabels() /*-{
		return this.nodeLabels;
	}-*/;
	
	native void nodeLabels(JsArray<Node> nodeLabels) /*-{
		this.nodeLabels = nodeLabels;
	}-*/;
	
	native JsArray<Node> nodeSkills() /*-{
		return this.nodeSkills;
	}-*/;
	
	native void nodeSkills(JsArray<Node> nodeSkills) /*-{
		this.nodeSkills = nodeSkills;
	}-*/;
}