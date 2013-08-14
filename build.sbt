name := "Domino"

version := "1.0"

scalaVersion := "2.10.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor_2.10" % "2.2.0",
  "junit" % "junit" % "4.8.1" % "test",
  "commons-lang" % "commons-lang" % "2.6"
)