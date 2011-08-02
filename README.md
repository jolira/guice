Google Guice
================

Extensions for Guice.

Plugins
--------------------

A very simple plugins mechanism that automatically discovers plugins by
looking for META-INF/services/com.google.inject.Module file. This module
file contains the name of the class or classes that should be added when
constructing the injector.

Every JAR used with this mechanism should contains such a META-INF/services/com.google.inject.Module
file. To create the injector, create the PluginManager, which discovers all
META-INF/services/com.google.inject.Module files in the search path, instantiates
all the modules identified there and creates an Injector using the modules.