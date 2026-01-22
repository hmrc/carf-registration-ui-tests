lazy val testSuite = (project in file("."))
  .disablePlugins(JUnitXmlReportPlugin) // Required to prevent https://github.com/scalatest/scalatest/issues/1427
  .settings(
    name := "carf-registration-ui-tests",
    version := "0.1.0",
    scalacOptions ++= Seq("-feature"),
    libraryDependencies ++= Dependencies.test,
    scalaVersion := "3.3.5",
    scalafmtOnCompile := true
  )
