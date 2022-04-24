Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / javacOptions ++= Seq(
  "-source",
  "8",
  "-target",
  "8"
)
ThisBuild / scalacOptions ++= Seq(
  "-encoding",
  "UTF-8",
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:postfixOps",
  "-language:higherKinds",
  "-language:existentials",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Ywarn-dead-code",
  "-Ywarn-unused",
  "-Yrepl-class-based",
  "-Yrangepos",
  "-explaintypes"
)

lazy val commonSettings = Seq(
  scalaVersion := "2.13.8",
  organization := "org.alexr",
  libraryDependencies ++= Seq(
    "io.circe" %%% "circe-generic" % "0.14.1"
  )
)

lazy val shared = (crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Pure) in file("shared"))
  .settings(commonSettings)
  .jsSettings()
  .jvmSettings()

lazy val backend = (project in file("backend"))
  .settings(commonSettings)
  .settings(
    name := "backend",
    version := "0.0.1",
    libraryDependencies ++= Seq(
      "org.http4s"    %% "http4s-dsl"          % "1.0.0-M32",
      "org.http4s"    %% "http4s-circe"        % "1.0.0-M32",
      "org.http4s"    %% "http4s-blaze-server" % "1.0.0-M32",
      "ch.qos.logback" % "logback-classic"     % "1.2.11"
    )
  )
  .dependsOn(shared.jvm)

lazy val frontend = (project in file("frontend"))
  .enablePlugins(ScalaJSPlugin)
  .enablePlugins(ScalaJSBundlerPlugin) // reload ; frontend/clean ; frontend/fastOptJS/webpack
  .settings(commonSettings)
  .settings(
    name := "frontend",
    version := "0.0.1",
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.http4s" %%% "http4s-ember-client" % "1.0.0-M32",
      "org.http4s" %%% "http4s-circe"        % "1.0.0-M32"
    ),
    Compile / npmDependencies ++= Seq(
      // TODO: here is no any fatal errors, but a lot of warnings
      //  and of course, the part dependent on ember-client - DOESN'T WORK
      //  nut fs2 part WORKS WELL
      "buffer"   -> "^6.0.3",
      "crypto"   -> "^1.0.1",
      "net"      -> "^1.0.2",
      "os"       -> "^0.1.2",
      "punycode" -> "^2.1.1",
      "stream"   -> "^0.0.2",
      "tls"      -> "^0.0.1"
    )
  )
  .dependsOn(shared.js)
