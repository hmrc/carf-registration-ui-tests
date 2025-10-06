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

    // Scenarios covered
    // 1.       "Individual user with NINO - Individual not connected to any business",
    // 2.       "Individual user with NINO - Sole trader",
    // 3.       "Individual user without NINO - Individual not connected to any business",
    // 4.       "Individual user without NINO - Sole trader - having a registered address in the UK",
    // 5.       "Individual user without NINO - Sole trader - having no registered address in the UK",

    Scenario(
      "Individual user with NINO - Individual not connected to any business",
      RegistrationTests,
      ZapTests
    ) {

      Given("The Individual user logs in as an individual not connected to a business with NINO")
      AuthLoginPage.loginAsIndividualWithNino()
      When("the user selects 'An Individual not connected to a business' in the '/individual-registration-type' page")
      IndRegistrationTypePage.registerIndividualAs("An individual not connected to a business")
      And("the user selects 'Yes' in the '/have-ni-number' page")
      HaveNiNumberPage.haveNinoYesOrNo("Yes")
      Then("the user is asked to enter their NINO in the '/ni-number' page")
      NiNumberPage
        .onPage() // TODO: This page is currently a placeholder. Script to enter NINO to be added once the page is ready

    }

    Scenario(
      "Individual user with NINO - Sole trader",
      RegistrationTests,
      ZapTests
    ) {

      Given("The Individual user logs in as sole trader with NINO")
      AuthLoginPage.loginAsIndividualWithNino()
      When("the user selects 'Sole trader' in the '/individual-registration-type' page")
      IndRegistrationTypePage.registerIndividualAs("Sole Trader")
      And("the user selects 'No' in the '/registered-address-in-uk' page")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("No")
      Then(
        "the '/have-utr' page should be displayed - this is still under development -- CARF123"
      ) // TODO: Remove this code and continue with the journey once CARF123 is developed
      HaveUtrPage.onPage() // TODO: Remove this code and continue with the journey once CARF123 is developed
      /*
      And("the user selects 'No' in the '/have-utr' page")
      //TODO: Add the step for selecting 'No' in the /have-utr page
      And("the user selects 'Yes' in the '/have-ni-number' page")
      HaveNiNumberPage.haveNinoYesOrNo("Yes")
      Then("the user is asked to enter their NINO in the '/ni-number' page")
      NiNumberPage.onPage()
       */
    }

    Scenario(
      "Individual user without NINO - Individual not connected to any business",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Individual user logs in as an individual not connected to a business without NINO")
      AuthLoginPage.loginAsIndividualWithoutNino()
      When("the user selects 'An Individual not connected to a business' in the '/individual-registration-type' page")
      IndRegistrationTypePage.registerIndividualAs("An individual not connected to a business")
      And("the user selects 'No' in the '/have-ni-number' page")
      HaveNiNumberPage.haveNinoYesOrNo("No")
      Then("the user is asked to enter their name in the '/without-id/name' page")
      IndWithoutIdNamePage
        .onPage() // TODO: This page is currently a placeholder. Script to enter name to be added once the page is ready
    }

    Scenario(
      "Individual user without NINO - Sole trader - having a registered address in the UK",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Individual user logs in as a sole trader without NINO having a registered address in the UK")
      AuthLoginPage.loginAsIndividualWithoutNino()
      When("the user selects 'Sole trader' in the '/individual-registration-type' page")
      IndRegistrationTypePage.registerIndividualAs("Sole Trader")
      And("the Individual user selects Yes in the '/registered-address-in-uk' page")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("Yes")
      And("the Individual user enters the UTR in the '/utr' page")
      UtrPage.enterUtr(validSaUtr)
      Then("the '/your-name' page should be displayed- this is still under development")
      YourNamePage.onPage()
    }

    Scenario(
      "Individual user without NINO - Sole trader - having no registered address in the UK",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Individual user logs in as a sole trader without NINO having no registered address in the UK")
      AuthLoginPage.loginAsIndividualWithoutNino()
      When("the user selects 'Sole trader' in the '/individual-registration-type' page")
      IndRegistrationTypePage.registerIndividualAs("Sole Trader")
      And("the user selects 'No' in the '/registered-address-in-uk' page")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("No")
      Then("the page 'Do you have a UTR?' should be displayed - this is still under development--CARF123")
      Then(
        "the '/have-utr' page should be displayed - this is still under development -- CARF123"
      ) // TODO: Remove this code and continue with the journey once CARF123 is developed
      HaveUtrPage.onPage() // TODO: Remove this code and continue with the journey once CARF123 is developed
      /*
      And("the user selects 'No' in the '/have-utr' page")
      //TODO: Add the step for selecting 'No' in the /have-utr page
      And("the user selects 'No' in the '/have-ni-number' page")
      HaveNiNumberPage.haveNinoYesOrNo("No")
      Then("the user is asked to enter their name in the '/without-id/name' page")
      IndWithoutIdNamePage.onPage()
       */
    }
  }
}
