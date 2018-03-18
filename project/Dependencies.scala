import sbt._

object Dependencies
{
  lazy val liftWebkit = "net.liftweb" %% "lift-webkit" % "3.2.0"

  lazy val scalaZ = "org.scalaz" %% "scalaz-core" % "7.2.20"

  lazy val slick = "com.typesafe.slick" %% "slick" % "3.2.2"

  lazy val slickHikariCp = "com.typesafe.slick" %% "slick-hikaricp" % "3.2.2"

  lazy val postgres = "org.postgresql" % "postgresql" % "42.2.1"

  lazy val liftMapper = "net.liftweb" %% "lift-mapper" % "3.2.0"

  lazy val akkaActor = "com.typesafe.akka" %% "akka-actor" % "2.5.11"

  lazy val slf4jNop = "org.slf4j" % "slf4j-nop" % "1.6.4"

  lazy val jodaTime = "joda-time" % "joda-time" % "2.9.9"

  lazy val bCrypt = "org.mindrot" % "jbcrypt" % "0.4"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4"

  lazy val pegdown = "org.pegdown" % "pegdown" % "1.6.0"
}