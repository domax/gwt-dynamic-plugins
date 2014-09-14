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

import static org.gwt.dynamic.common.shared.Utils.isEmpty;

import java.util.Set;
import java.util.logging.Logger;

import org.gwt.dynamic.module.gwtp.shared.RoleBean;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class RolesView extends ViewWithUiHandlers<RolesUiHandlers>
		implements RolesPresenter.MyView {

	private static final Logger LOG = Logger.getLogger(RolesView.class.getName());
	private static final String ROOT_ROLE = "Team Roles";

	interface Binder extends UiBinder<Widget, RolesView> {}

	public interface InnerCss extends CssResource {
		
		String role();

		String link();
		
		String label();
		
		String skill();
	}

	@UiField public static InnerCss style;
	@UiField SimplePanel content;

	private Graph graphData;
	
	@Inject
	public RolesView(final Binder binder) {
		initWidget(binder.createAndBindUi(this));
		Window.addResizeHandler(new ResizeHandler() {
			
		  Timer resizeTimer = new Timer() {  
		    @Override
		    public void run() {
		    	update();
		    }
		  };

		  @Override
		  public void onResize(ResizeEvent event) {
		    resizeTimer.cancel();
		    resizeTimer.schedule(300);
		  }
		});
		LOG.info("RolesView: instantiated");
	}
	
	@Override
	public void setData(Set<RoleBean> roles) {
		LOG.fine("RolesView.setData: roles=" + roles);
//		hideHint();
		graphData = isEmpty(roles) ? null : buildD3Graph(roles);
		update();
	}
	
	@Override
	public void update() {
		LOG.fine("RolesView.update");
		int width = content.getElement().getClientWidth();
		int height = content.getElement().getClientHeight();
		if (width == 0 || height == 0 || graphData == null) return;
		D3.select(content).select("svg").remove();
		Selection	svg = D3.select(content)
					.append("svg")
					.attr("width", width)
					.attr("height", height);
		renderChart(svg, width, height, graphData);
	}
	
	private Graph buildD3Graph(Set<RoleBean> rolesWithSkills) {
		Graph graph = Graph.create();
		int source, target;
		Graph.Node node;
		int root = -1;
		int c = 0;
		for (RoleBean profileRoles : rolesWithSkills) {
			node = Graph.Node.create(profileRoles.getRoleName(), c++);
			graph.nodes().push(node);
			source = graph.nodes().length() - 1;
			if (ROOT_ROLE.equals(profileRoles.getRoleName()))
				root = source;
			Set<String> skills = profileRoles.getSkills();
			if (!isEmpty(skills))
				for (String skill : skills) {
					node = Graph.Node.create(skill);
					graph.nodes().push(node);
					graph.nodeSkills().push(node);
					target = graph.nodes().length() - 1;
					graph.links().push(Graph.Link.create(source, target, 1));
				}
		}
		c = graph.nodes().length();
		for (int i = 0; i < c; ++i) {
			node = graph.nodes().get(i);
			if (node.hasColor()) {
				graph.nodeRoles().push(node);
				source = i;
				if (root >= 0 && root != i)
					graph.links().push(Graph.Link.create(root, i, 0));
				node = Graph.Node.create(node);
				graph.nodes().push(node);
				graph.nodeLabels().push(node);				
				target = graph.nodes().length() - 1;
				graph.links().push(Graph.Link.create(source, target, 2));
			}
		}
		return graph;
	}
	
	private native void renderChart(Selection svg, int width, int height, Graph graph) /*-{
		var style = @org.gwt.dynamic.module.gwtp.client.application.roles.RolesView::style;
		var cssLink = style.@org.gwt.dynamic.module.gwtp.client.application.roles.RolesView.InnerCss::link()();
		var cssRole = style.@org.gwt.dynamic.module.gwtp.client.application.roles.RolesView.InnerCss::role()();
		var cssLabel = style.@org.gwt.dynamic.module.gwtp.client.application.roles.RolesView.InnerCss::label()(); 
		var cssSkill = style.@org.gwt.dynamic.module.gwtp.client.application.roles.RolesView.InnerCss::skill()();

		var that = this;
		var showHint = $entry(this.@org.gwt.dynamic.module.gwtp.client.application.roles.RolesView::showHint(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;));
		
		var d3 = $wnd.d3;
		
		var color = d3.scale.category10();
		var distances = [150, 80, 30];
		var strengths = [0.8, 0.5, 0.2];
		
		var force = d3.layout.force()
			.charge(-500)
			.linkDistance(function(d) { return distances[d.group]; })
			.linkStrength(function(d) { return strengths[d.group]; })
			.size([width, height])
			.nodes(graph.nodes)
			.links(graph.links)
			.start();
		
		var link = svg.selectAll("." + cssLink)
			.data(graph.links).enter()
			.append("line")
			.attr("class", cssLink)
			.style("stroke", function(d) {
				return d.group == 2 ? color(d.source.color) : "#999";
			});
		
		var role = svg.selectAll("." + cssRole)
			.data(graph.nodeRoles).enter()
			.append("circle")
			.attr("class", cssRole)
			.attr("r", "1.2em")
			.style("stroke", function(d) { return color(d.color); })
			.on("click", function(d) {
				if (d3.event.defaultPrevented) return;
				showHint.call(that, "role", d.name, color(d.color));
			})
			.call(force.drag);
		
		var label = svg.selectAll("." + cssLabel)
			.data(graph.nodeLabels).enter()
			.append("g")
			.attr("class", cssLabel)
			.on("click", function(d) {
				if (d3.event.defaultPrevented) return;
				showHint.call(that, "role", d.name, color(d.color));
			})
			.call(force.drag);
		label.append("rect")
			.attr("rx", "0.5em")
			.attr("ry", "0.5em")
			.style("fill", function(d) { return color(d.color); });
		label.append("text")
			.text(function(d) { return d.name; });
		
		var skill = svg.selectAll("." + cssSkill)
			.data(graph.nodeSkills).enter()
			.append("g")
			.attr("class", cssSkill)
			.on("click", function(d) {
				if (d3.event.defaultPrevented) return;
				showHint.call(that, "skill", d.name, null);
			})
			.call(force.drag);
		skill.append("rect")
			.attr("rx", "0.3em")
			.attr("ry", "0.3em");
		skill.append("text")
			.text(function(d) { return d.name; });
		
		var updateNodePos = function(d) {
			return "translate(" + d.x + "," + d.y + ")";
		};
		
		var updateLabel = function(d) {
			var m = typeof d.color == 'undefined' ? 0 : 10;
			var b = this.childNodes[1].getBBox();
			var r = this.childNodes[0];
			if (!r.getAttribute("width")) r.setAttribute("width", b.width + m);
			if (!r.getAttribute("height")) r.setAttribute("height", b.height + m);
			r.setAttribute("transform", "translate(" + (b.x - m/2) + "," + (b.y - m/2) + ")");
		};
		
		force.on("tick", function() {
			role.attr("transform", updateNodePos);
			link
				.attr("x1", function(d) { return d.source.x; })
				.attr("y1", function(d) { return d.source.y; })
				.attr("x2", function(d) { return d.target.x; })
				.attr("y2", function(d) { return d.target.y; });
			label
				.attr("transform", updateNodePos)
				.each(updateLabel);
			skill
				.attr("transform", updateNodePos)
				.each(updateLabel);
		});
	}-*/;
	
	private void showHint(String type, String term, String color) {
		LOG.info("RolesView.showHint: type=" + type + "; term=" + term + "; color=" + color);
	}
}
