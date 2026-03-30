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
import uk.gov.hmrc.test.ui.pages.indContactDetails.*
import uk.gov.hmrc.test.ui.pages.indWithNino.*
import uk.gov.hmrc.test.ui.pages.orgContactDetails.*
import uk.gov.hmrc.test.ui.pages.orgWithUtr.*
import uk.gov.hmrc.test.ui.specs.tags.*

class ProblemSpec extends BaseSpec {

  Feature("Organisation or Individual Registration Problem Page Validation") {

    // **********************************************
    //           Individual Problem Pages
    // **********************************************

    Scenario("1 - Individual user without NINO - Sole trader - having a registered address outside the UK and SAUTR unmatched", RegistrationTests, ZapTests) {

      Given("the Individual user logs in as a sole trader without NINO having a registered address in the UK")
      AuthLoginPage.loginAsIndividualWithoutNino()
      When("the Individual user selects 'Sole trader' in the 'What are you registering as?' page")
      IndRegistrationTypePage.registerIndividualAs("Sole Trader")
      And("the Individual user selects 'No' in the 'Is your registered address in the UK?' page")
      RegisteredAddressInUkPage.select("No")
      And("the Organisation user selects 'Yes' in the 'Do you have a UTR' page")
      HaveUtrPage.select("Yes")
      And("the Individual user enters the Self Assessment UTR in the UTR page")
      UtrPage.enterUtr(unmatchedSaUtr)
      And("the Individual user enters the first name and last name and click Continue button in the 'What is your name' page")
      YourNamePage.enterNamesAndContinue("Carf", "Tester")
      When("The Individual user clicks on 'try again using different details' link in the 'The details you entered did not match our records' page")
      ProblemSoleTraderNotIdentifiedPage.clickOnLink(ProblemBusinessNotIdentifiedPage.tryAgainPartialLink)
      Then("the Individual user should be routed to 'What are you registering as?' page and his previous selection should be retained")
      IndRegistrationTypePage.verifyPreviousSelection("Sole Trader")
    }

    Scenario("2 - Already registered individual knock back page", RegistrationTests, ZapTests) {

      Given("the Individual user logs in as an individual not connected to a business with NINO")
      AuthLoginPage.loginAsIndividualWithNino()
      When("the Individual user selects 'An Individual not connected to a business' in the 'What are you registering as?' page")
      IndRegistrationTypePage.registerIndividualAs("An individual not connected to a business")
      And("the Individual user selects 'Yes' in the 'Do you have a National Insurance number?' page")
      HaveNiNumberPage.select("Yes")
      And("the Individual user enters the NINO in the 'What is your National Insurance number' page")
      NiNumberPage.enterNino(individualNino)
      And("the Individual user enters the 'First name' and 'Last name' in the 'What is your name?' page")
      IndNamePage.enterName("duplicateAlreadyRegistered", "Tester")
      And("the Individual user enters the date of birth in the 'What is your date of birth?' page")
      IndDOBPage.enterDateOfBirth("01", "JAN", "1901")
      And("the Individual user clicks the Continue button in the 'We have confirmed your identity' page")
      IndIdentityConfirmedPage.onPageContinueById()
      And("the Individual user enters the email address in the 'What is your email address' page")
      IndEmailPage.enterEmailAddress("carftester@test.com")
      And("the Individual user selects 'Yes' in the 'Can we contact you by phone' page")
      IndHavePhonePage.select("Yes")
      And("the Individual user enters their phone number in 'What is your phone number' page")
      IndPhonePage.enterIndividualPhoneNumber("1234567890")
      And("the Individual user clicks on 'Confirm and send' in 'Check your answers before you register for cryptoasset reporting' page")
      CheckYourAnswersPage.onPageSubmitById()
      Then("the Individual user is routed to 'Already Registration' page")
      IndAlreadyRegisteredPage.onPage()
    }

    // **********************************************
    //           Organisation Problem Pages
    // **********************************************

    Scenario("3 - Organisation user without CT-UTR enrolment having a registered address in the UK with unmatched business details", RegistrationTests, ZapTests) {
      Given("the Organisation user logs in as Limited Company without CT-UTR enrolment having a registered address in the UK with unmatched business details")
      AuthLoginPage.loginAsOrgAdminWithoutCtUtr()
      When("the Organisation user selects 'Limited Company' in the 'What are you registering as?' page")
      OrgRegistrationTypePage.registerOrganisationAs("Limited Company")
      And("the Organisation user selects 'Yes' in the 'Is your registered address in the UK?' page")
      RegisteredAddressInUkPage.select("Yes")

      And("the Organisation user enters the UTR in the UTR page")
      UtrPage.enterUtr(unMatchedCtUtrForBusinessName)

      And("the user enters the unmatched business name in the 'What is the registered name of your business?' page")
      BusinessNamePage.enterBusinessName("unmatched")
      When("the Organisation user clicks on 'try again using different details' link in the 'The details you entered did not match our records' page")
      ProblemBusinessNotIdentifiedPage.clickOnLink(ProblemBusinessNotIdentifiedPage.tryAgainPartialLink)
      Then("the organisation user should be routed to 'What are you registering as?' page and his previous selection should be retained")
      OrgRegistrationTypePage.verifyPreviousSelection("Limited Company")
    }

    Scenario("4 - Organisation user having CT-UTR enrolment with unmatched business details", RegistrationTests, ZapTests) {

      Given("the Organisation user logs in with CT-UTR enrolment")
      AuthLoginPage.loginAsOrgAdminWithCtUtr()
      When("the Organisation user selects 'No' on the 'Is this your business?' page for the unmatched business details")
      IsThisYourBusinessPage.select("No")
      And("the Organisation user clicks on 'sign in with the Government Gateway user ID for the organisation you wish to register' link")
      ProblemDifferentBusinessPage.clickOnLink(ProblemDifferentBusinessPage.signInLnk)
      Then("the Organisation user should be taken to the GG sign in page")
      SignOutPage.onPage()
    }

    Scenario("5 - Organisation Already registered knock back page", RegistrationTests, ZapTests) {

      Given("the Organisation user logs in as Limited Company without CT-UTR enrolment having a registered address in the UK with matched business details")
      AuthLoginPage.loginAsOrgAdminWithoutCtUtr()
      When("the Organisation user selects 'Limited Company' in the 'What are you registering as?' page")
      OrgRegistrationTypePage.registerOrganisationAs("Limited Company")
      And("the Organisation user selects 'Yes' in the 'Is your registered address in the UK?' page")
      RegisteredAddressInUkPage.select("Yes")
      And("the Organisation user enters the UTR in the UTR page")
      UtrPage.enterUtr(autoMatchedCtUtrForUK)
      And("the user enters the matched business name in the 'What is the registered name of your business?' page")
      BusinessNamePage.enterBusinessName("matched")
      And("the Organisation user selects 'Yes' on the 'Is this your business?' page for the matched business details")
      IsThisYourBusinessPage.select("Yes")
      And("the Organisation user clicks on Continue button on the 'Setting up contact details for cryptoasset reporting' page")
      YourContactDetailsPage.onPageContinueById()
      And("the Organisation user enters the contact name in 'What is the name of the person or team we should contact?' page")
      OrgFirstContactNamePage.enterContactName("duplicateAlreadyRegistered")
      And("the Organisation user enters the first contact's email in the 'What is the email address for the first contact?' page")
      OrgFirstContactEmailPage.enterFirstContactEmail("first.contact@example.com")
      And("the organisation user selects 'No' in the 'Can we contact your first contact by phone?' page")
      OrgFirstContactHavePhonePage.select("No")

      And("the organisation user selects 'No' in 'Is there someone else we can contact if your first contact is not available?' page")
      OrgHaveSecondContactPage.select("No")

      And("the organisation user clicks on 'Confirm and send' in 'Check your answers before you register for cryptoasset reporting' page")
      CheckYourAnswersPage.onPageSubmitById()
      Then("the Organisation user is routed to 'Organisation already registered' page")
      OrgAlreadyRegisteredPage.onPage()
    }
    // ************************************************
    //     Organisation assistant kick-out page
    // ************************************************

    Scenario("6 - Organisation affinity and Assistant credential role", RegistrationTests, ZapTests) {
      Given("the Organisation user logs in as an assistant")
      AuthLoginPage.loginAsOrgAssistant()
      OrgAssistantPage.onPage()
    }
  }
}
