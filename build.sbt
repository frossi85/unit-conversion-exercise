import Dependencies._

ThisBuild / scalaVersion     := "2.12.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.1"

val dependencies = Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "org.json4s" %% "json4s-native" % "3.6.10",
  scalaTest % Test
)

lazy val root = (project in file("."))
  .settings(
    name := "Desktop",
    libraryDependencies ++= dependencies
  )