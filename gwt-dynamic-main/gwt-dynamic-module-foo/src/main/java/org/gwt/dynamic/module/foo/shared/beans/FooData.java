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
package org.gwt.dynamic.module.foo.shared.beans;

public class FooData {

	private String header;
	private String html;

	public FooData() {}

	public FooData(String header, String html) {
		this.header = header;
		this.html = html;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + ((html == null) ? 0 : html.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		FooData other = (FooData) obj;
		if (header == null) {
			if (other.header != null) return false;
		} else if (!header.equals(other.header)) return false;
		if (html == null) {
			if (other.html != null) return false;
		} else if (!html.equals(other.html)) return false;
		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder("FooData")
				.append(" {header=").append(header)
				.append(", html=").append(html)
				.append("}").toString();
	}
}
