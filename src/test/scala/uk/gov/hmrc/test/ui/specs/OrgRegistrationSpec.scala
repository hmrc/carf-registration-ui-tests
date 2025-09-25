/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.test.ui.specs

import uk.gov.hmrc.test.ui.pages.*
import uk.gov.hmrc.test.ui.specs.tags.*

class OrgRegistrationSpec extends BaseSpec {

  Feature("Organisation Registration") {
    // All the scenarios and steps will be updated later
    Scenario(
      "Organisation affinity and User credential role without CT-UTR enrolment",
      RegistrationTests,
      ZapTests
    ) {

      Given("the user logs in with an Organisation affinity and user credential role without CT-UTR")
      AuthLoginPage.loginAsOrgAdminWithoutCtUtr()
      When("The user enters information to achieve a match on their organisation's data")
      OrgRegistrationTypePage.registerAs("Limited Company")
    }
  }

  Scenario(
    "Organisation affinity and User credential role with CT-UTR enrolment",
    RegistrationTests,
    ZapTests
  ) {

    Given("the user logs in as an auto-matched Organisation having user role with CT-UTR ")
    AuthLoginPage.loginAsOrgAdminWithCtUtr()
  }

  Scenario(
    "Organisation affinity and Assistant credential role",
    RegistrationTests,
    ZapTests
  ) {

    Given("the user logs in as an Organisation having assistant role ")
    AuthLoginPage.loginAsOrgAdminWithCtUtr()
  }
}
