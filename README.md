gwt-dynamic-plugins
===================

This is a demo project that describes how to build plugin-oriented GWT project. It is not about GWT code split.
Each plugin here is a separate web application that is developed and deployed separately from main/host application.

How to Build and Run
--------------------

`mvn -Pprod clean install` # builds all modules for production

`cd gwt-dynamic-host && mvn -Pprod jetty:run-exploded` # runs host application with modules using built-in Jetty server

How to Develop
--------------

### Developer mode

1. Prepare stuff for Devmode:
	```
	cd gwt-dynamic-solid
	mvn clean prepare-package
	```

2. Run `gwt-dynamic-solid` module in Devmode - but before make sure your Eclipse or JIdea configuration for developer mode
	includes all modules you want to run together.
	For this sample project you have to specify the following GWT modules for `gwt-dynamic-solid` Devmode configuration:
	* `org.gwt.dynamic.host.DynamicHost_dev`
	* `org.gwt.dynamic.module.foo.DynamicModuleFoo_dev`
	* `org.gwt.dynamic.module.bwr.DynamicModuleBar_dev`

### Superdev mode

1. Run server-side. You can do that in 2 ways:
	* Using built-in Jetty servlet-container - it is simplest and fastest way. Run in separate console:

		```
		cd gwt-dynamic-solid
		mvn -Pserver clean prepare-package jetty:run-exploded
		```
	* Using devmode for `gwt-dynamic-solid` Maven module - this way allows you to debug server-side code:
		- Prepare stuff for Devmode:

			```
			cd gwt-dynamic-solid
			mvn -Pserver clean prepare-package
			```
		- Create new Devmode configuration or copy it if you've got it already.
		- Add new item into classpath: `gwt-dynamic-solid/src/main/profiles/server/resources`
		- Place this item to the upper/first position in the classpath.
		- Run this Devmode configuration.
2. Run Host webapp in Superdev mode in separate console (pre-configured to use port `9999`):
	```
	cd gwt-dynamic-host
	mvn gwt:run-codeserver
	```

3. Run each dynamic module webapps in Superdev mode. For this sample just 2 modules are available yet.

	So, run in separate console `gwt-dynamic-module-foo` webapp (pre-configured to use port `10000`):
	```
	cd gwt-dynamic-module-foo
	mvn gwt:run-codeserver
	```
	Then, run in separate console `gwt-dynamic-module-bar` webapp (pre-configured to use port `10001`):
	```
	cd gwt-dynamic-module-bar
	mvn gwt:run-codeserver
	```

4. Open host webapp in browser (Chrome/Chromium is recommended): http://127.0.0.1:9999/DynamicHost/DynamicHost.html

5. Add bookmarklets to in-place recompilation host and modules (it is optional and you may do it just once):
	* Host webapp (port `9999`):
		`javascript:%7B%20window.__gwt_bookmarklet_params%20%3D%20%7B'server_url'%3A'http%3A%2F%2F127.0.0.1%3A9999%2F'%7D%3B%20var%20s%20%3D%20document.createElement('script')%3B%20s.src%20%3D%20'http%3A%2F%2F127.0.0.1%3A9999%2Fdev_mode_on.js'%3B%20void(document.getElementsByTagName('head')%5B0%5D.appendChild(s))%3B%7D)`

	* Foo Module webapp (port `10000`):
		`javascript:%7B%20window.__gwt_bookmarklet_params%20%3D%20%7B'server_url'%3A'http%3A%2F%2F127.0.0.1%3A10000%2F'%7D%3B%20var%20s%20%3D%20document.createElement('script')%3B%20s.src%20%3D%20'http%3A%2F%2F127.0.0.1%3A10000%2Fdev_mode_on.js'%3B%20void(document.getElementsByTagName('head')%5B0%5D.appendChild(s))%3B%7D)`

	* Bar Module webapp (port `10001`):
		`javascript:%7B%20window.__gwt_bookmarklet_params%20%3D%20%7B'server_url'%3A'http%3A%2F%2F127.0.0.1%3A10001%2F'%7D%3B%20var%20s%20%3D%20document.createElement('script')%3B%20s.src%20%3D%20'http%3A%2F%2F127.0.0.1%3A10001%2Fdev_mode_on.js'%3B%20void(document.getElementsByTagName('head')%5B0%5D.appendChild(s))%3B%7D)`

DONE
----

1. Made 5 Maven modules:
	* __gwt-dynamic-common__ - a part that is used in host application and in modules/plugins;
	* __gwt-dynamic-host__ - a startup application that have the main layout and loads modules/plugins dynamically;
	* __gwt-dynamic-module-foo__ - the 1st example of module/plugin.
	* __gwt-dynamic-module-bar__ - the 2nd example of module/plugin.
	* __gwt-dynamic-solid__ - a module that joins source code and resources from all modules above -
		to allow developing project like a solid app.
2. Resolved clashes in CSS rules that are generated by host and module apps.
3. Performed dynamic loading modules/plugins that are deployed beside of host app.  
4. Created an easy way to communicate between host and modules.
5. Added all needed stuff to allow deal with Java beans and JSON as simple as possible
   ([Piriti](https://github.com/hpehl/piriti)).
6. Added into project all needed stuff to make RESTful web-services as easy as possible
   ([RestyGWT](http://restygwt.fusesource.org/)).
7. Added possibility to develop/run/debug all the code as a solid project, but keeping it in the modules.
	 Both GWT dev modes are supported: classic Devmode and new Superdev mode.

TODO
----

1. Add sample of code split usage in modules.
2. Add sample of usage some MVP framework - e.g. [GWTP](https://github.com/ArcBees/GWTP).
3. Cover all possible runtime logic with UT:
	* BatchRunner;
	* FeatureConsumers/Providers that send data in serialized form;
	* REST ServiceProviders;
	* REST ServiceConsumers;
	* Presenters logic.
4. Describe classes and methods in JDocs as fully as possible.
5. Add full description of this project and tutorial into Wiki.
6. Apply more or less nice CSS-es to make demo to be presentable.
7. Add dynamic plugin archetype.
