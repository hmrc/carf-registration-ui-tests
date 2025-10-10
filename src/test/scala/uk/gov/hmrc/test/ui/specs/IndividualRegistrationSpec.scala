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
    // 1.       "Individual user with NINO - Individual not connected to any business", --> This journey converges with the journey 2 ("Individual user with NINO - Sole trader") at CARF-163
    // 2.       "Individual user with NINO - Sole trader", --> //Do not continue this journey further.
    // 3.       "Individual user without NINO - Individual not connected to any business", --> This journey converges with the journey 5 ("Individual user without NINO - Sole trader - having no registered address in the UK") at CARF-169
    // 4.       "Individual user without NINO - Sole trader - having a registered address in the UK", --> This journey converges with the Organisation user sole trader journey at CARF-121 - TODO: Check if this can be removed?
    // 5.       "Individual user without NINO - Sole trader - having no registered address in the UK", --> //Do not continue this journey further.

    // *******************************************
    //          Individual user with NINO
    // *******************************************

    Scenario(
      "Individual user with NINO - Individual not connected to any business",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Individual user logs in as an individual not connected to a business with NINO")
      AuthLoginPage.loginAsIndividualWithNino()
      When(
        "the Individual user selects 'An Individual not connected to a business' in the Individual registration type page"
      )
      IndRegistrationTypePage.registerIndividualAs("An individual not connected to a business")
      And("the Individual user selects 'Yes' in the 'Do you have a National Insurance number?' page")
      HaveNiNumberPage.haveNinoYesOrNo("Yes")
      Then("the Individual user is asked to enter their NINO in the 'What is your National Insurance number' page")
      NiNumberPage
        .onPage() // TODO: This page is currently a placeholder. Script to enter NINO to be added once the page is ready

    }

    Scenario(
      "Individual user with NINO - Sole trader",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Individual user logs in as sole trader with NINO")
      AuthLoginPage.loginAsIndividualWithNino()
      When("the Individual user selects 'Sole trader' in the Individual registration type page")
      IndRegistrationTypePage.registerIndividualAs("Sole Trader")
      And("the Individual user selects 'No' in the 'Is your registered address in the UK?' page")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("No")
      And("the Individual user selects 'No' in the 'Do you have a UTR' page")
      HaveUtrPage.haveUtrYesOrNo("No")
      And("the Individual user selects 'Yes' in the 'Do you have a National Insurance number' page")
      HaveNiNumberPage.haveNinoYesOrNo("Yes")
      Then("the user is asked to enter their NINO in the 'What is your National Insurance number' page")
      NiNumberPage.onPage() // Do not continue this journey further. It converges with the previous journey here.
    }

    // *******************************************
    //          Individual user without NINO
    // *******************************************

    Scenario(
      "Individual user without NINO - Individual not connected to any business",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Individual user logs in as an individual not connected to a business without NINO")
      AuthLoginPage.loginAsIndividualWithoutNino()
      When(
        "the Individual user selects 'An Individual not connected to a business' in the Individual registration type page"
      )
      IndRegistrationTypePage.registerIndividualAs("An individual not connected to a business")
      And("the Individual user selects 'No' in the 'Do you have a National Insurance number?' page")
      HaveNiNumberPage.haveNinoYesOrNo("No")
      Then("the Individual user is asked to enter their name in the 'What is your name' page")
      IndWithoutIdNamePage
        .onPage() // TODO: This page is currently a placeholder for CARF-169. Script to enter name to be added once the page is ready
    }

    Scenario(
      "Individual user without NINO - Sole trader - having a registered address in the UK",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Individual user logs in as a sole trader without NINO having a registered address in the UK")
      AuthLoginPage.loginAsIndividualWithoutNino()
      When("the Individual user selects 'Sole trader' in the Individual registration type page")
      IndRegistrationTypePage.registerIndividualAs("Sole Trader")
      And("the Individual user selects 'Yes' in the 'Is your registered address in the UK?' page")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("Yes")
      And("the Individual user enters the Self Assessment UTR in the UTR page")
      UtrPage.enterUtr(validSaUtr)
      Then("the page 'What is your name' should be displayed- this is still under development")
      YourNamePage.onPage()
    }

    Scenario(
      "Individual user without NINO - Sole trader - having no registered address in the UK",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Individual user logs in as a sole trader without NINO having no registered address in the UK")
      AuthLoginPage.loginAsIndividualWithoutNino()
      When("the Individual user selects 'Sole trader' in the Individual registration type page")
      IndRegistrationTypePage.registerIndividualAs("Sole Trader")
      And("the Individual user selects 'No' in the 'Is your registered address in the UK?'page")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("No")
      And("the Individual user selects 'No' in 'Do you have a UTR' page")
      HaveUtrPage.haveUtrYesOrNo("No")
      And("the user selects 'No' in the '/have-ni-number' page")
      HaveNiNumberPage.haveNinoYesOrNo("No")
      Then("the user is asked to enter their name in the 'What is your name' page")
      IndWithoutIdNamePage.onPage() // Do not continue this journey further. It converges with journey 3 here.
    }
  }
}
