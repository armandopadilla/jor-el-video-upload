lazy val akkaHttpVersion = "10.1.9"
lazy val akkaVersion = "2.6.0-M4"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.jorel",
      scalaVersion := "2.12.8"
    )),
    name := "jor-el-upload-video",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"          % akkaVersion,

      "com.github.seratch" %% "awscala-s3" % "0.8.+",

      "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-testkit"         % akkaVersion     % Test,
      "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"            % "3.0.5"         % Test
    )
  )