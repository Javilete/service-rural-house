import sbt._
import sbt.Keys._
import scala._

name := "service-rural-house"

organization := "com.rural.house.lg"

version := "0.1.0"

scalaVersion := "2.11.6"

assemblyJarName in assembly := "service-rural-house.jar"

// disable using the Scala version in output paths and artifacts
crossPaths := false

lazy val root = (project in file("."))

libraryDependencies ++= Seq(
  "org.mongodb" % "mongo-java-driver" % "3.0.0",
  "joda-time" % "joda-time" % "2.7",
  "com.rural.house.lg" % "interface-rural-house" % "0.1.3"
)

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)