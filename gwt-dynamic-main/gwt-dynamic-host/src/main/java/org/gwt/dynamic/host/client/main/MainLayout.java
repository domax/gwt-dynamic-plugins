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

import java.util.logging.Logger;

import org.gwt.dynamic.common.client.services.BusyEvent;
import org.gwt.dynamic.common.client.services.BusyEvent.BusyHandler;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class MainLayout extends Composite implements BusyHandler {

	private static final Logger LOG = Logger.getLogger(MainLayout.class.getName());
	
	interface MainLayoutUiBinder extends UiBinder<Widget, MainLayout> {}

	private static MainLayoutUiBinder uiBinder = GWT.create(MainLayoutUiBinder.class);
	
	@UiField DockLayoutPanel mainLayout;
	@UiField HorizontalPanel headerPanel;
	@UiField Label header;
	@UiField Label spinner;
	@UiField CaptionPanel centerPanel;
	@UiField SimplePanel contentPanel;
	@UiField(provided = true) CellList<Object> cellList = 
			new CellList<Object>(new AbstractCell<Object>(CLICK, KEYDOWN) {
		@Override
		public void render(Context context, Object value, SafeHtmlBuilder sb) {
			// TODO
		}
	});

	public MainLayout() {
		initWidget(uiBinder.createAndBindUi(this));
		LOG.info("MainLayout: instantiated");
	}

	@Override
	public void onChange(BusyEvent event) {
		LOG.info("MainLayout.onChange: event.busy=" + event.isBusy());
		spinner.setStyleName("animate-spin", event.isBusy());
	}
}
