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
package org.gwt.dynamic.common.shared.beans;

public class BeanB {

	public double value1;
	public double value2;
	public BeanC role;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		long temp;
		temp = new Double(value1).longValue();
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = new Double(value2).longValue();
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		BeanB other = (BeanB) obj;
		if (role == null) {
			if (other.role != null) return false;
		} else if (!role.equals(other.role)) return false;
		if (new Double(value1).longValue() != new Double(other.value1).longValue()) return false;
		if (new Double(value2).longValue() != new Double(other.value2).longValue()) return false;
		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder("BeanB")
				.append(" {value1=").append(value1)
				.append(", value2=").append(value2)
				.append(", role=").append(role)
				.append("}").toString();
	}
}
