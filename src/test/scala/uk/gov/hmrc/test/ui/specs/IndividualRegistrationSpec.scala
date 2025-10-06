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

  Feature("Individual CARF Registration") {

    // All the scenarios and steps will be updated later

    Scenario(
      "Individual user with NINO - Individual not connected to any business",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Individual user logs in as an individual not connected to a business with NINO")
      AuthLoginPage.loginAsIndividualWithNino()

    }

    Scenario(
      "Individual user with NINO - Sole trader",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Individual user logs in as sole trader with NINO")
      AuthLoginPage.loginAsIndividualWithNino()

    }

    Scenario(
      "Individual user without NINO - Individual not connected to any business",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Individual user logs in as an individual not connected to a business without NINO")
      AuthLoginPage.loginAsIndividualWithoutNino()
      When("the Individual user selects which type of Individual they are")
      IndRegistrationTypePage.registerIndividualAs("An individual not connected to a business")
      Then("the page 'Do you have a National Insurance Number' should be displayed")
      HaveNiNumberPage.onPage()
    }

    Scenario(
      "Individual user registering as a Sole trader without NINO having a registered address in the UK",
      RegistrationTests,
      ZapTests
    ) {

      Given(
        "the Individual user logs in as a sole trader without NINO having a registered address in the UK"
      )
      AuthLoginPage.loginAsIndividualWithoutNino()
      When("the Individual user selects which type of Individual they are")
      IndRegistrationTypePage.registerIndividualAs("Sole Trader")
      And("the Individual user selects Yes for the registered address in the UK")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("Yes")
      And("the Individual user enters the UTR in the UTR page")
      UtrPage.enterUtr(validSaUtr)
      Then("the Business Name page should be displayed- this is still under development")
      BusinessNamePage.onPage()
    }

    Scenario(
      "Individual user registering as a Sole trader without NINO having no registered address in the UK",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Individual user logs in as a sole trader without NINO having no registered address in the UK")
      AuthLoginPage.loginAsIndividualWithoutNino()
      When("the Individual user selects which type of Individual they are")
      IndRegistrationTypePage.registerIndividualAs("Sole Trader")
      And("the Individual user selects No for the registered address in the UK")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("No")
      Then("the page 'Do you have a UTR?' should be displayed - this is still under development--CARF123")
      HaveUtrPage.onPage()
    }
  }
}
