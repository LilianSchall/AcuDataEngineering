ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
      name := "StudentsSignal",
      libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.7.0",
        libraryDependencies += "com.lihaoyi" %% "ujson" % "3.1.1"
)
