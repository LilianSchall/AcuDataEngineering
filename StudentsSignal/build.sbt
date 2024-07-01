ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
      name := "StudentsSignal",
      libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.7.0",
      libraryDependencies += "com.lihaoyi" %% "upickle" % "2.0.0",
      libraryDependencies +=   "com.lihaoyi" %% "ujson" % "1.4.0"
)

ThisBuild / assemblyMergeStrategy := {
    case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
    case PathList("META-INF", xs @ _*) => MergeStrategy.filterDistinctLines
    case "plugin.xml" => MergeStrategy.last
    case _ => MergeStrategy.first
}
