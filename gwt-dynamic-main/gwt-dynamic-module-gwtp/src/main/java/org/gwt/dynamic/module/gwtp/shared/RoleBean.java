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
package org.gwt.dynamic.module.gwtp.shared;

import java.io.Serializable;
import java.util.Set;

public class RoleBean implements Serializable {

	private static final long serialVersionUID = -7216823018475384605L;

	private String roleName;
	private Set<String> skills;

	public RoleBean() {}

	public RoleBean(String roleName, Set<String> skills) {
		this.roleName = roleName;
		this.skills = skills;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<String> getSkills() {
		return skills;
	}

	public void setSkills(Set<String> skills) {
		this.skills = skills;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		result = prime * result + ((skills == null) ? 0 : skills.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		RoleBean other = (RoleBean) obj;
		if (roleName == null) {
			if (other.roleName != null) return false;
		} else if (!roleName.equals(other.roleName)) return false;
		if (skills == null) {
			if (other.skills != null) return false;
		} else if (!skills.equals(other.skills)) return false;
		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder("RoleBean")
				.append(" {roleName=").append(roleName)
				.append(", skills=").append(skills)
				.append("}").toString();
	}
}
