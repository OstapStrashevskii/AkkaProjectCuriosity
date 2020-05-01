name := "AkkaProjectCuriosity"

version := "0.1"

scalaVersion := "2.13.2"

lazy val akkaHttpVersion = "10.2.0-M1"
lazy val akkaVersion = "2.6.4"

val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
val akkaActorTyped = "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaVersion


lazy val akkaDependencies = Seq(
  akkaHttp,
  akkaActorTyped,
  akkaStream
)

//libraryDependencies ++= akkaDependencies

//lazy val root = (project in file("."))
//  .settings(
//    name := "AkkaCuriosity",
//  )

lazy val datasource =
  project in file("datasource")

lazy val services =
  project in file("services")

lazy val controllers = (project in file("controllers")).settings(name := "Hello", libraryDependencies ++= akkaDependencies)

lazy val server = (project in file("server")).settings(name := "Hello", libraryDependencies ++= akkaDependencies ).dependsOn(controllers)



//"com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
//"ch.qos.logback" % "logback-classic" % "1.2.3",
//
//"com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
//"com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
//"com.typesafe.akka" %% "akka-stream" % akkaVersion,
//
//"com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
//"com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
//"com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
//"com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
//"org.scalatest" %% "scalatest" % "3.1.0" % Test,




