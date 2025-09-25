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

class IndividualRegistrationSpec extends BaseSpec {

  Feature("Individual Registration") {

    // All the scenarios and steps will be updated later

    Scenario(
      "Individual affinity and User credential role with NINO - Individual not linked to any business",
      RegistrationTests,
      ZapTests
    ) {

      Given(
        "the user logs in as an Individual not linked to any business having user role with NINO"
      )
      AuthLoginPage.loginAsIndividualUserNoBusinessWithNino()
    }

    Scenario(
      "Individual affinity and User credential role with NINO - Sole trader",
      RegistrationTests,
      ZapTests
    ) {

      Given("the user logs in as an Individual sole trader having user role with NINO")
      AuthLoginPage.loginAsIndividualUserNoBusinessWithNino()
    }

    Scenario(
      "Individual affinity and User credential role without NINO - Individual not linked to any business",
      RegistrationTests,
      ZapTests
    ) {

      Given("the user logs in as an Individual sole trader having user role without NINO")
      AuthLoginPage.loginAsIndividualWithoutNino()
    }

    Scenario(
      "Individual affinity and User credential role without NINO - Sole trader",
      RegistrationTests,
      ZapTests
    ) {

      Given("the user logs in as Individual sole trader having user role without NINO")
      AuthLoginPage.loginAsIndividualSoleTraderWithSaUtr()
    }
  }
}
