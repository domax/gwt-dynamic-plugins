package org.gwt.dynamic.common.client.features;

import java.util.logging.Logger;

import org.gwt.dynamic.common.client.widgets.WrapPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class ModuleContentFeatureProvider extends FeatureProvider<Element, Void> implements FeatureCommonConst {

	private static final Logger LOG = Logger.getLogger(ModuleContentFeatureProvider.class.getName());
	private final ViewHandler viewHandler;
	
	public static interface ViewHandler {
		Widget getView();
	}
	
	public ModuleContentFeatureProvider(ViewHandler viewHandler) {
		super(GWT.getModuleName());
		this.viewHandler = viewHandler;
		LOG.info("ModuleContentFeatureProvider: instantiated");
	}

	@Override
	public String getType() {
		return FEATURE_CONTENT;
	}

	@Override
	public void call(Element contentElement, AsyncCallback<Void> callback) {
		LOG.info("ModuleContentFeatureProvider.call: moduleName=" + getModuleName());
		try {
			if (contentElement == null) throw new IllegalArgumentException("Content element should be specified");
			if (viewHandler == null) throw new IllegalStateException("View Handler wasn't defined");
			Widget w = viewHandler.getView();
			if (w == null) throw new IllegalStateException("View wasn't defined");
			
			if (contentElement.getChildCount() > 0) {
				LOG.fine("ModuleContentFeatureProvider.call: moduleName=" + getModuleName() + "; content exists");
				if (callback != null) callback.onSuccess(null);
				return;
			}
			
//			contentElement.appendChild(w.getElement());
			WrapPanel.wrap(contentElement).add(w);
			
			LOG.fine("ModuleContentFeatureProvider.call: moduleName=" + getModuleName() + "; content created");
			if (callback != null) callback.onSuccess(null);
		} catch (Exception e) {
			LOG.severe("ModuleContentFeatureProvider.call: " + e.getMessage());
			if (callback != null) callback.onFailure(e);
		}
	}
}
