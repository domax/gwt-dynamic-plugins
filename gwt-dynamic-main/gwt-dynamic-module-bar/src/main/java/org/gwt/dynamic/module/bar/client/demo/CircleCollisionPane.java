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
package org.gwt.dynamic.module.bar.client.demo;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class CircleCollisionPane extends Composite {

	private static final Logger LOG = Logger.getLogger(CircleCollisionPane.class.getName());
	
	private static final int START_DELAY = 300;
	private static final int RUN_DELAY = 15 * 1000;
	
	@UiField SimplePanel collisionContainer;
	@UiField Button pauseBtn;
	@UiField Button resetBtn;

	private CircleCollisionGameScene gameScene;
	private Timer startTimer = new Timer() {
		@Override
		public void run() {
			gameScene.start();
			runTimer.schedule(RUN_DELAY);
		}
	};
	private Timer runTimer = new Timer() {
		@Override
		public void run() {
			pause();
		}
	};

	private static CircleCollisionPaneUiBinder uiBinder = GWT.create(CircleCollisionPaneUiBinder.class);

	interface CircleCollisionPaneUiBinder extends UiBinder<Widget, CircleCollisionPane> {}

	public CircleCollisionPane() {
		initWidget(uiBinder.createAndBindUi(this));
		LOG.info("CircleCollisionPane: instantiated");
		
		Window.addResizeHandler(new ResizeHandler() {

		  Timer resizeTimer = new Timer() {  
		    @Override
		    public void run() {
					pause();
					gameScene = null;
					reset();
		    }
		  };

		  @Override
		  public void onResize(ResizeEvent event) {
		    resizeTimer.cancel();
		    resizeTimer.schedule(300);
		  }
		});
	}

	@UiHandler("pauseBtn")
	void onPauseBtnClick(ClickEvent event) {
		pause();
	}

	@UiHandler("resetBtn")
	void onResetBtnClick(ClickEvent event) {
		reset();
	}

	private boolean ensureGameScene() {
		runTimer.cancel();
		startTimer.cancel();
		if (gameScene == null) {
			gameScene = new CircleCollisionGameScene(collisionContainer);
			LOG.info("CircleCollisionPane.ensureGameScene: collisionContaine.sizer=" 
					+ collisionContainer.getOffsetWidth() + "x" + collisionContainer.getOffsetHeight());
			return false;
		}
		return true;
	}
	
	public void start() {
		LOG.info("CircleCollisionPane.start");
		if (ensureGameScene()) gameScene.cancel();
		pauseBtn.setVisible(true);
		resetBtn.setVisible(false);
		startTimer.schedule(START_DELAY);
	}
	
	public void reset() {
		LOG.info("CircleCollisionPane.reset");
		if (ensureGameScene()) gameScene.reset();
		pauseBtn.setVisible(true);
		resetBtn.setVisible(false);
		startTimer.schedule(START_DELAY);
	}
	
	public void pause() {
		LOG.info("CircleCollisionPane.pause");
		if (ensureGameScene()) gameScene.cancel();
		pauseBtn.setVisible(false);
		resetBtn.setVisible(true);
	}
}
