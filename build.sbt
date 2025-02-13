ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.16"

Compile / run / mainClass := Some("AnalyticsApp")

lazy val root = (project in file("."))
  .settings(
    name := "Int"
  )

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "3.2.0"
)

