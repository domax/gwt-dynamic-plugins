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
package org.gwt.dynamic.common.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gwt.dynamic.common.shared.Utils;

/**
 * A simple servlet filter implementation of <a
 * href='http://www.gwtproject.org/doc/latest/DevGuideCompilingAndDebugging.html#perfect_caching'>GWT Perfect
 * Caching</a>
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class GWTPerfectCacheFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(GWTPerfectCacheFilter.class.getName());
	private static final String NO_CACHE = ".nocache.";
	private static final String CACHE = ".cache.";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.info("GWTPerfectCacheFilter.init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		String requestUrl = ((HttpServletRequest) request).getRequestURL().toString();
		LOG.fine("GWTPerfectCacheFilter.doFilter: requestUrl=" + requestUrl);
		if (!Utils.isHollow(requestUrl)) {
			if (requestUrl.contains(NO_CACHE)) {
				LOG.info("GWTPerfectCacheFilter.doFilter: requestUrl contains '" + NO_CACHE + "' string.");
				resp.setHeader("ExpiresActive", "on");
				resp.setHeader("ExpiresDefault", "now");
				resp.setHeader("Cache-Control", "public, max-age=0, must-revalidate");
			} else if (requestUrl.contains(CACHE)) {
				LOG.info("GWTPerfectCacheFilter.doFilter: requestUrl contains '" + CACHE + "' string.");
				resp.setHeader("ExpiresActive", "on");
				resp.setHeader("ExpiresDefault", "now plus 1 year");
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		LOG.info("GWTPerfectCacheFilter.destroy");
	}
}
