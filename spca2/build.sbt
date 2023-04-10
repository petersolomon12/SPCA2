name := """SPCA2"""
organization := "com.fyp"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.9"

libraryDependencies += guice

libraryDependencies ++= Seq(
  javaWs
)
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.12.0"

libraryDependencies ++= Seq(
  guice,
  javaJpa,
  javaJdbc,
  "mysql" % "mysql-connector-java" % "8.0.30",
  "org.hibernate" % "hibernate-core" % "5.5.6",
  javaWs % "test",
  "org.awaitility" % "awaitility" % "4.0.1" % "test",
  "org.assertj" % "assertj-core" % "3.14.0" % "test",
  "io.jsonwebtoken" % "jjwt-api" % "0.11.1",
  "com.stripe" % "stripe-java" % "22.12.0",
  "io.jsonwebtoken" % "jjwt-impl" % "0.11.1",
  "org.json" % "json" % "20180130",
  "com.google.code.gson" % "gson" % "1.7.1",
  "io.jsonwebtoken" % "jjwt-jackson" % "0.11.1",
  "org.mockito" % "mockito-core" % "3.1.0" % "test",
)
