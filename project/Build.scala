import sbt._
import sbt.Keys._
import net.virtualvoid.sbt.graph.Plugin._

object ProjectBuild extends Build {
  val sharedSettings = Project.defaultSettings ++ graphSettings ++ Seq(
    organization := "com.tresata",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "2.10.4",
    crossScalaVersions := Seq("2.10.4", "2.11.5"),
    javacOptions ++= Seq("-Xlint:unchecked", "-source", "1.6", "-target", "1.6"),
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-target:jvm-1.6", "-feature", "-Xlint", "-language:_"),
    publishMavenStyle := true,
    pomIncludeRepository := { x => false },
    publishArtifact in Test := false
  )

  lazy val spraySpnego = Project(
    id = "spray-spnego",
    base = file("."),
    settings = sharedSettings ++ Seq(
      name := "spray-spnego",
      libraryDependencies ++= Seq(
        "org.slf4j" % "slf4j-api" % "1.7.5" % "compile",
        "io.spray" %% "spray-routing" % "1.3.2" % "compile",
        "commons-codec" % "commons-codec" % "1.4" % "compile",
        "com.typesafe.akka" %% "akka-actor" % "2.3.4" % "provided",
        "org.scalatest" %% "scalatest" % "2.2.1" % "test"
      )
    )
  )

  lazy val testServer = Project(
    id = "test-server",
    base = file("test-server"),
    settings = sharedSettings ++ Seq(
      name := "test-server",
      libraryDependencies ++= Seq(
        "io.spray" %% "spray-can" % "1.3.2" % "compile",
        "org.slf4j" % "slf4j-log4j12" % "1.7.5" % "compile",
        "com.typesafe.akka" %% "akka-actor" % "2.3.4" % "compile",
        "com.typesafe.akka" %% "akka-slf4j" % "2.3.4" % "compile"
      ),
      publish := { },
      publishLocal := { }
    )
  ).dependsOn(spraySpnego)
}
