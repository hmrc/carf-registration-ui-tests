import sbt.*

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"         %% "ui-test-runner"      % "0.58.0" % Test,
    "uk.gov.hmrc"         %% "domain-test-play-30" % "13.0.0" % Test
  )

}
