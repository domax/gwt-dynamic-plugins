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
package org.gwt.dynamic.host.client.main;

import static com.google.gwt.dom.client.BrowserEvents.CLICK;
import static com.google.gwt.dom.client.BrowserEvents.KEYDOWN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.gwt.dynamic.common.client.services.BusyEvent;
import org.gwt.dynamic.common.client.services.BusyEvent.BusyHandler;
import org.gwt.dynamic.host.client.features.ModuleContentFeatureConsumer;
import org.gwt.dynamic.host.shared.beans.ModuleBean;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class MainLayout extends Composite implements BusyHandler {

	private static final Logger LOG = Logger.getLogger(MainLayout.class.getName());
	
	interface MainLayoutUiBinder extends UiBinder<Widget, MainLayout> {}

	private static MainLayoutUiBinder uiBinder = GWT.create(MainLayoutUiBinder.class);
	
	@UiField DockLayoutPanel mainLayout;
	@UiField HorizontalPanel headerPanel;
	@UiField Label header;
	@UiField Label spinner;
	@UiField CaptionPanel centerPanel;
	@UiField DeckLayoutPanel slidePanel;
	@UiField(provided = true) CellList<SafeHtml> cellList =
			new CellList<SafeHtml>(new AbstractCell<SafeHtml>(CLICK, KEYDOWN) {
				@Override
				public void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
					sb.append(value);
				}
			});
	
	private final ListDataProvider<SafeHtml> dataProvider;
	private final Map<SafeHtml, ModuleBean> navigationMap = new HashMap<SafeHtml, ModuleBean>();
	private final ModuleContentFeatureConsumer moduleContentFeatureConsumer = 
			new ModuleContentFeatureConsumer();

	public MainLayout() {
		initWidget(uiBinder.createAndBindUi(this));
		dataProvider = new ListDataProvider<SafeHtml>();
		dataProvider.addDataDisplay(cellList);
		final SingleSelectionModel<SafeHtml> selectionModel = new SingleSelectionModel<SafeHtml>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				final ModuleBean moduleBean = navigationMap.get(selectionModel.getSelectedObject());
				LOG.info("MainLayout.cellList.selectionModel#onSelectionChange: moduleBean=" + moduleBean);
				if (moduleBean != null) {
					centerPanel.setCaptionText(moduleBean.getName());
					
					HTML panel = null;
					String id = "content" + moduleBean.getName();
					for (int i = 0, l = slidePanel.getWidgetCount(); i < l; ++i) {
						Widget w = slidePanel.getWidget(i);
						if (id.equals(w.getElement().getId())) {
							panel = (HTML) w;
							break;
						}
					}
					if (panel == null) {
						LOG.warning("MainLayout.cellList.selectionModel#onSelectionChange: cannot find element with ID " + id);
						return;
					}
					final Widget showWidget = panel;
					moduleContentFeatureConsumer.call(moduleBean.getName(), panel.getElement(), new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							LOG.info("MainLayout.moduleContentFeatureConsumer#onSuccess: moduleBean.name=" + moduleBean.getName());
							slidePanel.showWidget(showWidget);
						}

						@Override
						public void onFailure(Throwable caught) {
							LOG.severe("MainLayout.moduleContentFeatureConsumer#onFailure: " + caught.getMessage());
							slidePanel.showWidget(showWidget);
						}
					});
				}
			}
		});
		LOG.info("MainLayout: instantiated");
	}

	@Override
	public void onChange(BusyEvent event) {
		LOG.info("MainLayout.onChange: event.busy=" + event.isBusy());
		spinner.setStyleName("animate-spin", event.isBusy());
	}
	
	public void setNavigationItems(List<ModuleBean> modules, List<SafeHtml> items) {
		LOG.info("MainLayout.setNavigationItems: modules=" + modules + "; items=" + items);
		if (items == null) items = new ArrayList<SafeHtml>();
		dataProvider.setList(items);
		navigationMap.clear();
		int i = 0;
		slidePanel.clear();
		for (SafeHtml item : items) {
			ModuleBean moduleBean = modules.get(i++);
			navigationMap.put(item, moduleBean);
			HTML panel = new HTML();
			panel.getElement().setId("content" + moduleBean.getName());
			slidePanel.add(panel);
		}
	}
}
