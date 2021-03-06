lazy val sharedSettings = Seq(
  organization := "com.tresata",
  version := "0.3.0-SNAPSHOT",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-target:jvm-1.7", "-feature", "-language:_", "-Xlint:-package-object-classes,-adapted-args,_",
    "-Ywarn-unused-import", "-Ywarn-dead-code", "-Ywarn-value-discard", "-Ywarn-unused"),
  scalacOptions in (Test, compile) := (scalacOptions in (Test, compile)).value.filter(_ != "-Ywarn-value-discard").filter(_ != "-Ywarn-unused"),
  scalacOptions in (Compile, console) := (scalacOptions in (Compile, console)).value.filter(_ != "-Ywarn-unused-import"),
  scalacOptions in (Test, console) := (scalacOptions in (Test, console)).value.filter(_ != "-Ywarn-unused-import"),
  publishMavenStyle := true,
  pomIncludeRepository := { x => false },
  publishArtifact in Test := false
)

lazy val `spray-spnego` = (project in file(".")).settings(
  sharedSettings
).settings(
  name := "spray-spnego",
  libraryDependencies ++= Seq(
    "org.slf4j" % "slf4j-api" % "1.7.5" % "compile",
    "io.spray" %% "spray-routing" % "1.3.3" % "compile",
    "commons-codec" % "commons-codec" % "1.4" % "compile",
    "com.typesafe.akka" %% "akka-actor" % "2.3.15" % "provided",
    "org.scalatest" %% "scalatest" % "2.2.6" % "test"
  )
)

lazy val `test-server` = (project in file("test-server")).settings(
  sharedSettings
).settings(
  name := "test-server",
  libraryDependencies ++= Seq(
    "io.spray" %% "spray-can" % "1.3.3" % "compile",
    "org.slf4j" % "slf4j-log4j12" % "1.7.5" % "compile",
    "com.typesafe.akka" %% "akka-actor" % "2.3.15" % "compile",
    "com.typesafe.akka" %% "akka-slf4j" % "2.3.15" % "compile"
  ),
  publish := { },
  publishLocal := { }
).dependsOn(`spray-spnego`)
