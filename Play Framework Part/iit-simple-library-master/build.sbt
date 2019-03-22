name := """library"""
organization := "com.iit"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.6"

libraryDependencies += guice
libraryDependencies += javaJdbc


libraryDependencies ++= Seq(
  "org.mongodb" % "mongodb-driver" % "3.0.4"
)

