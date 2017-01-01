import sbt._
import sbt.Keys._

object ProjectBuild extends Build {
  val sharedSettings = Seq(
    organization := "com.tresata",
    version := "0.3.0-SNAPSHOT",
    scalaVersion := "2.11.7",
    javacOptions ++= Seq("-Xlint:unchecked", "-source", "1.7", "-target", "1.7"),
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-target:jvm-1.7", "-feature", "-Xlint", "-language:_"),
    publishMavenStyle := true,
    pomIncludeRepository := { x => false },
    publishArtifact in Test := false
  )

  lazy val spraySpnego = Project(
    id = "spray-spnego",
    base = file(".")
  ).settings(
    sharedSettings ++ Seq(
      name := "spray-spnego",
      libraryDependencies ++= Seq(
        "org.slf4j" % "slf4j-api" % "1.7.5" % "compile",
        "io.spray" %% "spray-routing" % "1.3.3" % "compile",
        "commons-codec" % "commons-codec" % "1.4" % "compile",
        "com.typesafe.akka" %% "akka-actor" % "2.3.11" % "provided",
        "org.scalatest" %% "scalatest" % "2.2.6" % "test"
      )
    )
  )

  lazy val testServer = Project(
    id = "test-server",
    base = file("test-server")
  ).settings(
    sharedSettings ++ Seq(
      name := "test-server",
      libraryDependencies ++= Seq(
        "io.spray" %% "spray-can" % "1.3.3" % "compile",
        "org.slf4j" % "slf4j-log4j12" % "1.7.5" % "compile",
        "com.typesafe.akka" %% "akka-actor" % "2.3.11" % "compile",
        "com.typesafe.akka" %% "akka-slf4j" % "2.3.11" % "compile"
      ),
      publish := { },
      publishLocal := { }
    )
  ).dependsOn(spraySpnego)
}
