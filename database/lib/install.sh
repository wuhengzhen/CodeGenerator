#!/usr/bin/env bash
mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=0.1 -Dpackaging=jar -Dfile=ojdbc8.jar
mvn install:install-file -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc41 -Dversion=6.0.8112 -Dpackaging=jar -Dfile=sqljdbc41-6.0.8112.jar