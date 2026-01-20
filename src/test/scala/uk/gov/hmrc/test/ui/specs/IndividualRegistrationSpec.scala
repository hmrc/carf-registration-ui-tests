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

    Scenario("1 - Individual user with NINO - Individual not connected to any business", RegistrationTests, ZapTests) {

      Given("the Individual user logs in as an individual not connected to a business with NINO")
      AuthLoginPage.loginAsIndividualWithNino()
      When("the Individual user selects 'An Individual not connected to a business' in the 'What are you registering as?' page")
      IndRegistrationTypePage.registerIndividualAs("An individual not connected to a business")
      And("the Individual user selects 'Yes' in the 'Do you have a National Insurance number?' page")
      HaveNiNumberPage.selectNinoYesOrNo("Yes")
      And("the Individual user enters the NINO in the 'What is your National Insurance number' page")
      NiNumberPage.enterNino(individualNino)
      And("the Individual user enters the 'First name' and 'Last name' in the 'What is your name?' page")
      IndNamePage.enterName("Carf", "Tester")
      And("the Individual user enters the date of birth in the 'What is your date of birth?' page")
      IndDOBPage.enterDateOfBirth("01", "JAN", "1901")
      And("the Individual user clicks the Continue button in the 'We have confirmed your identity' page")
      ConfirmedIdentityPage.onPageContinueById()
      And("the Individual user enters the email address in the 'What is your email address' page")
      IndEmailPage.enterEmailAddress("carftester@test.com")
      And("the Individual user selects 'Yes' in the 'Can we contact you by phone' page")
      IndHavePhonePage.contactByPhoneYesOrNo("Yes")
      And("the Individual user enters their phone number in 'What is your phone number' page")
      IndPhonePage.enterIndividualPhoneNumber("1234567890")
      And("the Individual user clicks on 'Confirm and send' in 'Check your answers before you register for cryptoasset reporting' page")
      CheckYourAnswersPage.onPageSubmitById()
      Then("the Individual user is routed to 'Registration successful' page")

    }

    Scenario("2 -Individual user with NINO - Sole trader", RegistrationTests, ZapTests) {

      Given("the Individual user logs in as sole trader with NINO")
      AuthLoginPage.loginAsIndividualWithNino()
      When("the Individual user selects 'Sole trader' in the 'What are you registering as?' page")
      IndRegistrationTypePage.registerIndividualAs("Sole Trader")
      And("the Individual user selects 'No' in the 'Is your registered address in the UK?' page")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("No")
      And("the Individual user selects 'No' in the 'Do you have a UTR' page")
      HaveUtrPage.selectUtrYesOrNo("No")
      And("the Individual user selects 'Yes' in the 'Do you have a National Insurance number' page")
      HaveNiNumberPage.selectNinoYesOrNo("Yes")
      Then("the Individual user is asked to enter their NINO in the 'What is your National Insurance number' page")
      NiNumberPage.onPage() // Do not continue this journey further. It converges with the previous journey here.
    }

    // *******************************************
    //          Individual user without NINO
    // *******************************************

    Scenario("3 - Individual user without NINO - Individual not connected to any business", RegistrationTests, ZapTests) {

      Given("the Individual user logs in as an individual not connected to a business without NINO")
      AuthLoginPage.loginAsIndividualWithoutNino()
      When("the Individual user selects 'An Individual not connected to a business' in the 'What are you registering as?' page")
      IndRegistrationTypePage.registerIndividualAs("An individual not connected to a business")
      And("the Individual user selects 'No' in the 'Do you have a National Insurance number?' page")
      HaveNiNumberPage.selectNinoYesOrNo("No")
      And("the Individual user is asked to enter their name in the 'What is your name' page")
      IndWithoutIdNamePage.enterNamesAndContinue("John", "Doe")
      And("the Individual user enters the date of birth in 'What is your date of birth' page")
      IndWithoutIdDOBPage.enterDateOfBirthAndContinue("1", "1", "1990")
      Then("the Individual user is routed to 'Do you live in the UK, Jersey, Guernsey or the Isle of Man?' page")
      IndWhereDoYouLivePage.onPage()
    }

    Scenario("4 - Individual user without NINO - Sole trader - having a registered address in the UK", RegistrationTests, ZapTests) {

      Given("the Individual user logs in as a sole trader without NINO having a registered address in the UK")
      AuthLoginPage.loginAsIndividualWithoutNino()
      When("the Individual user selects 'Sole trader' in the 'What are you registering as?' page")
      IndRegistrationTypePage.registerIndividualAs("Sole Trader")
      And("the Individual user selects 'Yes' in the 'Is your registered address in the UK?' page")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("Yes")
      And("the Individual user enters the Self Assessment UTR in the UTR page")
      UtrPage.enterUtr(matchedSaUtr)
      And("the Individual user enters the first name and last name and click Continue button in the 'What is your name' page")
      YourNamePage.enterNamesAndContinue("Carf", "Tester")
      And("the Individual user selects 'Yes' on the 'Is this your business?' page for the matched business details")
      IsThisYourBusinessPage.yourBusinessYesOrNo("Yes")
      And("the Individual user enters the email address in the 'What is your email address?' page")
      IndEmailPage.enterEmailAddress("carftester@test.com")
      And("the Individual user selects 'Yes' in the 'Can we contact you by phone' page")
      IndHavePhonePage.contactByPhoneYesOrNo("Yes")
      And("the Individual user enters their phone number in 'What is your phone number' page")
      IndPhonePage.enterIndividualPhoneNumber("1234567890")
      Then("the Individual user is routed to 'Check your answers before you register for cryptoasset reporting' page")
      // TODO: Add this after 321 is implemented: CheckYourAnswersPage.onPage()
    }

    Scenario("5 - Individual user without NINO - Sole trader - having no registered address in the UK", RegistrationTests, ZapTests) {

      Given("the Individual user logs in as a sole trader without NINO having no registered address in the UK")
      AuthLoginPage.loginAsIndividualWithoutNino()
      When("the Individual user selects 'Sole trader' in the 'What are you registering as?' page")
      IndRegistrationTypePage.registerIndividualAs("Sole Trader")
      And("the Individual user selects 'No' in the 'Is your registered address in the UK?'page")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("No")
      And("the Individual user selects 'No' in 'Do you have a UTR' page")
      HaveUtrPage.selectUtrYesOrNo("No")
      And("the user selects 'No' in the '/have-ni-number' page")
      HaveNiNumberPage.selectNinoYesOrNo("No")
      Then("the user is asked to enter their name in the 'What is your name' page")
      IndWithoutIdNamePage.onPage() // Do not continue this journey further. It converges with journey 3 here.
    }

  }
}
