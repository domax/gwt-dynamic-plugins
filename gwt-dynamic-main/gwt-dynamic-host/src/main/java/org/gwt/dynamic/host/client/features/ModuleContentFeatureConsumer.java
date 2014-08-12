package org.gwt.dynamic.host.client.features;

import java.util.logging.Logger;

import org.gwt.dynamic.common.client.features.FeatureCommonConst;
import org.gwt.dynamic.common.client.features.FeatureConsumer;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ModuleContentFeatureConsumer extends FeatureConsumer<Element, Void> implements FeatureCommonConst {

	private static final Logger LOG = Logger.getLogger(ModuleContentFeatureConsumer.class.getName());

	private String moduleName;
	
	@Override
	public String getModuleName() {
		return moduleName;
	}

	public void call(String moduleName, Element contentElement, AsyncCallback<Void> callback) {
		LOG.info("ModuleContentFeatureConsumer.call: moduleName=" + moduleName);
		this.moduleName = moduleName;
		super.call(contentElement, callback);
	}
	
	@Override
	public String getType() {
		return FEATURE_CONTENT;
	}
}
