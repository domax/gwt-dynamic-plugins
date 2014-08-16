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
package org.gwt.dynamic.common.client.widgets;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.impl.Disposable;
import com.google.gwt.core.client.impl.Impl;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AttachDetachException;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class WrapPanel extends ComplexPanel {

	private static Set<Widget> widgetsToDetach = new HashSet<Widget>();
	private static final AttachDetachException.Command maybeDetachCommand = new AttachDetachException.Command() {
		@Override
		public void execute(Widget w) {
			if (w.isAttached()) ((WrapPanel) w).onDetach();
		}
	};

	public static WrapPanel wrap(Element element) {
		if (widgetsToDetach == null) {
			widgetsToDetach = new HashSet<Widget>();
			hookWindowClosing();
		}
		WrapPanel panel = new WrapPanel(element);
		panel.onAttach();
		detachOnWindowClose(panel);
		return panel;
	}

	public static void detachNow(WrapPanel panel) {
		assert widgetsToDetach.contains(panel) : "detachNow() called on a panel not currently in the detach list";
		try {
			panel.onDetach();
		} finally {
			widgetsToDetach.remove(panel);
		}
	}

	public static void detachOnWindowClose(WrapPanel panel) {
		assert !widgetsToDetach.contains(panel) : "detachOnUnload() called twice for the same panel";
		widgetsToDetach.add(panel);
	}

	private static void hookWindowClosing() {
		Impl.scheduleDispose(new Disposable() {
			@Override
			public void dispose() {
				detachWidgets();
			}
		});
		// Catch the window closing event.
		Window.addCloseHandler(new CloseHandler<Window>() {
			@Override
			public void onClose(CloseEvent<Window> closeEvent) {
				detachWidgets();
			}
		});
	}

	private static void detachWidgets() {
		// When the window is closing, detach all widgets that need to be
		// cleaned up. This will cause all of their event listeners
		// to be unhooked, which will avoid potential memory leaks.
		try {
			AttachDetachException.tryCommand(widgetsToDetach, maybeDetachCommand);
		} finally {
			widgetsToDetach.clear();
		}
	}

	private WrapPanel(Element element) {
		setElement(element);
	}

	@Override
	public void add(Widget widget) {
		add(widget, (Element) getElement());
	}
}
