name := "VxPollerScala"

version := "1.0.0"

scalaVersion := "2.11.4"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies += "io.vertx" % "vertx-core" % "2.1.2"

libraryDependencies += "io.vertx" % "vertx-platform" % "2.1.2"

libraryDependencies += "io.vertx" % "lang-scala_2.10" % "1.1.0-SNAPSHOT"

libraryDependencies += "commons-logging" % "commons-logging" % "1.1"

libraryDependencies += "commons-logging" % "commons-logging-api" % "1.1"

libraryDependencies += "log4j" % "log4j" % "1.2.13"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.10" % "2.1.4"
 
libraryDependencies += "junit" % "junit" % "4.11" % "test"

//io/vertx/lang-scala_2.10/1.1.0-SNAPSHOT

//libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"


