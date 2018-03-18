import Dependencies._

lazy val root = (project in file("."))
  .settings(
    inThisBuild(List(
      organization := "com.ruchij",
      scalaVersion := "2.12.4"
    )),
    name := "lift-web",
    libraryDependencies ++= Seq(
      liftWebkit,
      scalaZ,
      liftMapper,
      akkaActor,
      slick, slickHikariCp, postgres,
      jodaTime,
      slf4jNop,
      bCrypt,
      rediscala,

      scalaTest % Test,
      pegdown % Test
    ),
    buildInfoKeys := Seq[BuildInfoKey](name, scalaVersion, sbtVersion),
    buildInfoPackage := "com.eed3si9n"
  )

enablePlugins(JettyPlugin)
enablePlugins(BuildInfoPlugin)

coverageEnabled := true

testOptions in Test +=
  Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-results")

addCommandAlias("testWithCoverage", "; clean; test; coverageReport")