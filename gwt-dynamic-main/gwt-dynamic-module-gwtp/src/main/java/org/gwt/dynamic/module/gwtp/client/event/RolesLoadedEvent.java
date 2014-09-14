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
package org.gwt.dynamic.module.gwtp.client.event;

import java.util.Set;

import org.gwt.dynamic.module.gwtp.shared.RoleBean;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class RolesLoadedEvent extends GwtEvent<RolesLoadedEvent.Handler> {

	private static Type<Handler> TYPE = new Type<Handler>();
	
	public static interface Handler extends EventHandler {
		void onRolesLoaded(RolesLoadedEvent event);
	}
	
	public static void fire(HasHandlers source, Set<RoleBean> roles) {
		source.fireEvent(new RolesLoadedEvent(roles));
	}
	
	public static Type<Handler> getType() {
		return TYPE;
	}

	private final Set<RoleBean> roles;

	private RolesLoadedEvent(Set<RoleBean> roles) {
		this.roles = roles;
	}

	@Override
	public Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onRolesLoaded(this);
	}
	
	public Set<RoleBean> getRoles() {
		return roles;
	}
}
