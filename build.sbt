import Dependencies._

ThisBuild / scalaVersion     := "2.13.3"
ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "com.frossi85"
ThisBuild / organizationName := "frossi85"

exportJars := true

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.1"

val dependencies = Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "org.json4s" %% "json4s-native" % "3.6.10",
  "ch.qos.logback"    % "logback-classic"           % "1.2.3",

  "com.typesafe.akka" %% "akka-http-testkit"        % AkkaHttpVersion % Test,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion     % Test,
  scalaTest % Test
)

lazy val root = (project in file("."))
  .settings(
    name := "interview-project",
    libraryDependencies ++= dependencies,
    mainClass in assembly := Some("com.frossi85.UnitConverterApplication"),
    assemblyJarName in assembly := "app.jar",
  )

