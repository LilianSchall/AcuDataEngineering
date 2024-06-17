ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "Analyzer",
    // https://mvnrepository.com/artifact/org.apache.spark/spark-core
    libraryDependencies += "org.apache.spark" %% "spark-core" % "3.3.1",
    libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.3.1",
    libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "3.2.0",
    libraryDependencies += "org.postgresql" % "postgresql" % "42.2.20"
  )
