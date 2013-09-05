name := "Domino-testweb"

version := "1.0"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.8.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.0.1" % "test",
  "org.scalatest" % "scalatest_2.10" % "2.0.M6" % "test",
  "commons-lang" % "commons-lang" % "2.6",
  "com.google.code.maven-play-plugin.org.playframework" % "play" % "1.2.6"
)