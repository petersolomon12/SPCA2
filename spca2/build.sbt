name := """SPCA2"""
organization := "com.fyp"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.10"

libraryDependencies += guice


libraryDependencies ++= Seq(
  javaWs
)

libraryDependencies ++= Seq(
  guice,
  javaJpa,
  javaJdbc,
  "mysql" % "mysql-connector-java" % "8.0.30",
  "org.hibernate" % "hibernate-core" % "5.5.6",
)