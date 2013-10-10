name := "Domino Crawler"

version := "1.0"

scalaVersion := "2.10.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.8.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.0.1" % "test",
  "org.scalatest" % "scalatest_2.10" % "2.0.M6" % "test",
  "com.typesafe.akka" % "akka-actor_2.10" % "2.2.0",
  "commons-lang" % "commons-lang" % "2.6"
)

unmanagedSourceDirectories in Compile <++= baseDirectory { base =>
  Seq(
    base / ".." / "domino-testweb" / "src"
  )
}