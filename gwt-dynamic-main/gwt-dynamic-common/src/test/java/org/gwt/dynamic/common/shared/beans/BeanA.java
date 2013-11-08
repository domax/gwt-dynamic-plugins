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

import java.util.Date;
import java.util.List;

public class BeanA {

	public String name;
	public Date ts;
	public List<BeanB> values;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ts == null) ? 0 : ts.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		BeanA other = (BeanA) obj;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		if (ts == null) {
			if (other.ts != null) return false;
		} else if (!ts.equals(other.ts)) return false;
		if (values == null) {
			if (other.values != null) return false;
		} else if (!values.equals(other.values)) return false;
		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder("BeanA")
				.append(" {name=").append(name)
				.append(", ts=").append(ts)
				.append(", values=").append(values)
				.append("}").toString();
	}
}
