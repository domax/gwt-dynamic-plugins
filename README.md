gwt-dynamic-plugins
===================

This is a demo project that describes how to build plugin-oriented GWT project. It is not about GWT code split.
Each plugin here is a separate web application that is developed and deployed separately from main/host application.

How to Build and Run
--------------------

Builds all modules for production:
```
cd gwt-dynamic-main
mvn -Pprod clean install
```

Runs host application with modules using built-in Jetty server:
```
cd gwt-dynamic-main/gwt-dynamic-host
mvn -Pprod jetty:run-exploded
```

How to Develop
--------------

First of all, prepare whole project for development:

```
cd gwt-dynamic-main
mvn clean install
```

### Developer mode

1. Prepare stuff for Devmode:
	```
	cd gwt-dynamic-main/gwt-dynamic-solid
	mvn clean prepare-package
	```

2. Run `gwt-dynamic-solid` module in Devmode - but before make sure your Eclipse or JIdea configuration for developer mode
	includes all modules you want to run together.
	For this sample project you have to specify the following GWT modules for `gwt-dynamic-solid` Devmode configuration:
	* `org.gwt.dynamic.host.DynamicHost_dev`
	* `org.gwt.dynamic.module.foo.DynamicModuleFoo_dev`
	* `org.gwt.dynamic.module.bar.DynamicModuleBar_dev`

### Superdev mode

1. Run server-side. You can do that in 2 ways:
	* Using built-in Jetty servlet-container - it is simplest and fastest way. Run in separate console:

		```
		cd gwt-dynamic-main/gwt-dynamic-solid
		mvn -Psuperdev clean prepare-package jetty:run-exploded
		```
	* Using devmode for `gwt-dynamic-solid` Maven module - this way allows you to debug server-side code:
		- Prepare stuff for Devmode:

			```
			cd gwt-dynamic-main/gwt-dynamic-solid
			mvn -Psuperdev clean prepare-package
			```
		- Create new Devmode configuration or copy it if you've got it already.
		- Add new item into classpath: `gwt-dynamic-solid/src/main/profiles/server/resources`
		- Place this item to the upper/first position in the classpath.
		- Run this Devmode configuration.
2. Run Host webapp in Superdev mode in separate console (pre-configured to use port `9999`):
	```
	cd gwt-dynamic-main/gwt-dynamic-host
	mvn -Psuperdev gwt:run-codeserver
	```

3. Run each dynamic module webapps in Superdev mode. For this sample just 2 modules are available yet.

	So, run in separate console `gwt-dynamic-module-foo` webapp (pre-configured to use port `10000`):
	```
	cd gwt-dynamic-main/gwt-dynamic-module-foo
	mvn -Psuperdev gwt:run-codeserver
	```
	Then, run in separate console `gwt-dynamic-module-bar` webapp (pre-configured to use port `10001`):
	```
	cd gwt-dynamic-main/gwt-dynamic-module-bar
	mvn -Psuperdev gwt:run-codeserver
	```

4. Open host webapp in browser (Chrome/Chromium is recommended): http://127.0.0.1:9999/DynamicHost/DynamicHost.html

5. Add bookmarklets to in-place recompilation host and modules (it is optional and you may do it just once):
	* Host webapp (port `9999`):
		`javascript:%7B%20window.__gwt_bookmarklet_params%20%3D%20%7B'server_url'%3A'http%3A%2F%2F127.0.0.1%3A9999%2F'%7D%3B%20var%20s%20%3D%20document.createElement('script')%3B%20s.src%20%3D%20'http%3A%2F%2F127.0.0.1%3A9999%2Fdev_mode_on.js'%3B%20void(document.getElementsByTagName('head')%5B0%5D.appendChild(s))%3B%7D)`

	* Foo Module webapp (port `10000`):
		`javascript:%7B%20window.__gwt_bookmarklet_params%20%3D%20%7B'server_url'%3A'http%3A%2F%2F127.0.0.1%3A10000%2F'%7D%3B%20var%20s%20%3D%20document.createElement('script')%3B%20s.src%20%3D%20'http%3A%2F%2F127.0.0.1%3A10000%2Fdev_mode_on.js'%3B%20void(document.getElementsByTagName('head')%5B0%5D.appendChild(s))%3B%7D)`

	* Bar Module webapp (port `10001`):
		`javascript:%7B%20window.__gwt_bookmarklet_params%20%3D%20%7B'server_url'%3A'http%3A%2F%2F127.0.0.1%3A10001%2F'%7D%3B%20var%20s%20%3D%20document.createElement('script')%3B%20s.src%20%3D%20'http%3A%2F%2F127.0.0.1%3A10001%2Fdev_mode_on.js'%3B%20void(document.getElementsByTagName('head')%5B0%5D.appendChild(s))%3B%7D)`

### Create new module/plugin

You can easily create a new module using Maven archetype provided.

1. Install Maven module archetype and use it to create new plugin:
	* __Install archetype into local Maven repository__ (needs to be run just once):
	```
	cd gwt-dynamic-archetype
	mvn install
	```
	* __Create a new Maven module using archetype__:
	```
	cd gwt-dynamic-main
	mvn archetype:generate \
		-DarchetypeCatalog=local \
		-DarchetypeGroupId=com.domax.gwt \
		-DarchetypeArtifactId=gwt-dynamic-module-archetype
	```
	* It will ask you to enter specific values for new project: `groupId`, `artifactId`, `version` and `package`. Let's assume, you've entered the following:
	```
	groupId: com.domax.gwt
	artifactId: gwt-dynamic-module-sample
	version: 0.0.1-SNAPSHOT
	package: org.gwt.dynamic.module.sample
	```
	* __Correct the created stuff__:
		- _Superdev codeserver service port_: it is `10100` by default, but you have to define the unique port across all
			your plugins and host app - open file `gwt-dynamic-module-sample/pom.xml` and change accordingly a property with
			name `<gwtc.codeServerPort>`.
		- _CSS Prefix_: it is `DMS` by default, but you have to define the unique prefix across all your plugins and host
			app - open file `gwt-dynamic-module-new/src/main/resources/org/gwt/dynamic/module/sample/ModuleSample.gwt.xml` and
			change accordingly configuration property with name `CssResource.obfuscationPrefix`. I recommend to make such
			prefix using first letters of plugin/module name: e.g. `DynamicModuleSuperFeature` => `DMSF`.

2. Add newly created maven module into project:
	* __Include it into main Maven project__ (it should be inserted automatically, but verify it was done correctly):
		open `gwt-dynamic-main/pom.xml` file and correct `<modules>` section there adding new
		`<module>gwt-dynamic-module-sample</module>` element.
	* __Add it into Jetty configuration for production version testing__:
		open `gwt-dynamic-main/gwt-dynamic-host/pom.xml` file and correct `<contextHandlers>` section of `jetty-maven-plugin`
		adding new `<contextHandler>` element there, like that:

		```
		<contextHandler implementation="org.eclipse.jetty.webapp.WebAppContext">
			<war>${basedir}/../gwt-dynamic-module-sample/target/gwt-dynamic-module-sample-${project.version}</war>
			<contextPath>/gwt-dynamic-module-sample</contextPath>
		</contextHandler>
		```
	* __Add it into development environment__:
		open `gwt-dynamic-main/gwt-dynamic-solid/pom.xml` file and correct 3 plugin configurations:
		- __build-helper-maven-plugin__: add new module source path into `add-sources` execution phase configuration -
			just add new `<source>${basedir}/../gwt-dynamic-module-sample/src/main/java</source>` element into
			`<sources>` section.
		- __build-helper-maven-plugin__: add new module source path into `add-resources` execution phase configuration - just add new `<resource>` element into `<sources>` section like that:

			```
			<resource>
				<filtering>true</filtering>
				<directory>${basedir}/../gwt-dynamic-module-sample/src/main/resources</directory>
			</resource>
			```
		- __gwt-maven-plugin__: add new module name into `<modules>` section:
			`<module>org.gwt.dynamic.module.sample.DynamicModuleSample_dev</module>`
3. Update web-service of host application that returns list of available plugins.
  To do that, you have to update all `ModuleServiceProvider.properties` files in `gwt-dynamic-host` and `gwt-dynamic-solid`
	Maven modules. At this moment there are 4 such files:
	```
	gwt-dynamic-host/src/main/profiles/dev/resources/org/gwt/dynamic/host/server/rest/ModuleServiceProvider.properties
	gwt-dynamic-host/src/main/profiles/prod/resources/org/gwt/dynamic/host/server/rest/ModuleServiceProvider.properties
	gwt-dynamic-solid/src/main/profiles/dev/resources/org/gwt/dynamic/host/server/rest/ModuleServiceProvider.properties
	gwt-dynamic-solid/src/main/profiles/superdev/resources/org/gwt/dynamic/host/server/rest/ModuleServiceProvider.properties
	```
	Just open each of them and add there new according object into JSON string. E.g. for `.../dev/.../ModuleServiceProvider.properties` file:
	```
	modules=[{"name":"DynamicModuleFoo","url":""},{"name":"DynamicModuleBar","url":""},{"name":"DynamicModuleSample","url":""}]
	```
	For `.../prod/.../ModuleServiceProvider.properties` file:
	```
	modules=[{"name":"DynamicModuleFoo","url":"gwt-dynamic-module-foo"}, {"name":"DynamicModuleBar","url":"gwt-dynamic-module-bar"}, {"name":"DynamicModuleSample","url":"gwt-dynamic-module-sample"}]
	```
	and for `.../superdev/.../ModuleServiceProvider.properties` file:
	```
	modules=[{"name":"DynamicModuleFoo","url":"http://127.0.0.1:10000"}, {"name":"DynamicModuleBar","url":"http://127.0.0.1:10001"}, {"name":"DynamicModuleSample","url":"http://127.0.0.1:10002"}]
	```

4. Go to the the **"How to Develop"** section above.

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
8. Covered all possible runtime logic with UT.
9. Updated dependencies: Java 7, GWT 2.6.1, RestyGWT 1.4, etc.
10. Added dynamic plugin archetype - so you can create new plugins easily with only one command.

TODO
----

1. Add sample of code split usage in modules.
2. Add sample of usage some MVP framework - e.g. [GWTP](https://github.com/ArcBees/GWTP).
3. Describe classes and methods in JDocs as fully as possible.
4. Add full description of this project and tutorial into Wiki.
5. Apply more or less nice CSS-es to make demo to be presentable.
6. Fix broken UTs for JSON serializer/deserializer and Feature Consumer/Provider
