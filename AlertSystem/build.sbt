ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.3"

lazy val root = (project in file("."))
  .settings(
    name := "AlertSystem",
    libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.7.0"
  )
