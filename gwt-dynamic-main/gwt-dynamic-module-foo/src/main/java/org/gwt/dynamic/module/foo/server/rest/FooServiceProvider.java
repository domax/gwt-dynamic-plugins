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
package org.gwt.dynamic.module.foo.server.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.gwt.dynamic.common.server.rest.AbstractServiceProvider;
import org.gwt.dynamic.module.foo.shared.beans.FooData;
import org.gwt.dynamic.module.foo.shared.services.FooServiceConst;

@Path("/" + FooServiceConst.PATH)
public class FooServiceProvider extends AbstractServiceProvider implements FooServiceConst {

	private static final Logger LOG = Logger.getLogger(FooServiceProvider.class.getName());
	private static final String URL = "http://loripsum.net/api/10/long";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public FooData getFooData() throws MalformedURLException, IOException {
		FooData result = new FooData("Foo Lorem Ipsum", null);
		try (Reader r = new InputStreamReader((InputStream) new URL(URL).getContent(), "UTF-8");
				Writer w = new StringWriter()) {
			char[] buf = new char[1024 * 4];
			for (int c = 0; c >= 0; c = r.read(buf))
				w.write(buf, 0, c);
			result.setHtml(w.toString());
		}
		LOG.fine("result=" + result);
		return result;
	}

	public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println(new FooServiceProvider().getFooData());
	}
}
