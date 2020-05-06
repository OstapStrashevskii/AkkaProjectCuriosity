name := "AkkaProjectCuriosity"
version := "0.1"
scalaVersion := "2.13.2"

lazy val akkaHttpVersion = "10.2.0-M1"
lazy val akkaVersion = "2.6.4"
lazy val redisVersion = "3.20"
lazy val playJsonVersion = "2.7.4"
lazy val akkaHttpJsonVersion = "1.29.1"
lazy val loggerVersion = "3.9.2"

val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
val akkaActorTyped = "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaVersion
val playJson = "com.typesafe.play" %% "play-json" % playJsonVersion
val akkaHttpJson = "de.heikoseeberger" %% "akka-http-play-json" % akkaHttpJsonVersion
val logger = "com.typesafe.scala-logging" %% "scala-logging" % loggerVersion
val logBack = "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime



lazy val redisDependencies = Seq("net.debasishg" %% "redisclient" % redisVersion)

lazy val akkaDependencies = Seq(
  akkaHttp,
  akkaActorTyped,
  akkaStream
)

lazy val projectDependencies = Seq(
  logger,
  logBack
)

lazy val datasource =
  (project in file("datasource")).settings(libraryDependencies ++= redisDependencies ++ projectDependencies)

lazy val services =
  (project in file("services")).dependsOn(datasource, apiClient, dto)

lazy val controllers = (project in file("controllers")).settings(name := "controllers", libraryDependencies ++= akkaDependencies ++ projectDependencies).dependsOn(services)

lazy val server = (project in file("server")).settings(name := "server", libraryDependencies ++= akkaDependencies ++ projectDependencies ).dependsOn(controllers, services, datasource, apiClient)

lazy val apiClient = (project in file("client")).settings(name := "client", libraryDependencies ++= akkaDependencies ++ projectDependencies :+ playJson :+ akkaHttpJson :+ logger).dependsOn(dto)

lazy val dto = (project in file("dto")).settings(name := "dto", libraryDependencies ++= akkaDependencies ++ projectDependencies :+ playJson :+ logger)
