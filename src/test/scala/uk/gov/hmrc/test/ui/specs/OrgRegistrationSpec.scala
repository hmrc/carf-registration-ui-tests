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

  Feature("Organisation CARF Registration") {

    // Scenarios covered
    // 1. "Organisation user without CT-UTR enrolment having a registered address in the UK with matched business details"
    // 2. "Organisation user without CT-UTR enrolment having no registered address in the UK"
    // 3. "Organisation user without CT-UTR enrolment registers as a Sole trader having a registered address in the UK"
    // 4. "Organisation user having CT-UTR enrolment with matched business details"

    // *************************************************
    //    Organisation user without CT-UTR enrolment
    // *************************************************

    Scenario("1 - Organisation user without CT-UTR enrolment having a registered address in the UK with matched business details", RegistrationTests, ZapTests) {

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
      OrgFirstContactNamePage.enterContactName("John Doe")
      And("the Organisation user enters the first contact's email in the 'What is the email address for the first contact?' page")
      OrgFirstContactEmailPage.enterFirstContactEmail("first.contact@example.com")
      And("the organisation user selects 'Yes' in the 'Can we contact your first contact by phone?' page")
      OrgFirstContactHavePhonePage.select("Yes")
      And("the organisation user enters '01234567890' in the 'What is the phone number for the first contact?' page")
      OrgFirstContactPhonePage.enterFirstContactPhone("01234567890")
      And("the organisation user selects 'Yes' in 'Is there someone else we can contact if your first contact is not available?' page")
      OrgHaveSecondContactPage.select("Yes")
      And("the organisation user enters the second contact name in 'What is the name of the second person or team we should contact?' page")
      OrgSecondContactNamePage.enterName("Jane Smith")
      And("the organisation user enters the email in 'What is the email address for the second contact?' page")
      OrgSecondContactEmailPage.enterSecondContactEmail("second.contact@example.com")
      And("the organisation user selects 'Yes' in 'Can we contact your second contact by phone?' page")
      OrgSecondContactHavePhonePage.select("Yes")
      And("the organisation user enters the second contact phone number in 'What is your phone number for [second contact name]' page ")
      SecondContactPhonePage.enterSecondContactPhone("01234567890")
      And("the organisation user clicks on 'Confirm and send' in 'Check your answers before you register for cryptoasset reporting' page")
      CheckYourAnswersPage.onPageSubmitById()
      Then("the Organisation user is routed to 'Registration successful' page")
      ConfirmRegistrationPage.onPage()
    }

    Scenario("2 - Organisation user without CT-UTR enrolment having no registered address in the UK", RegistrationTests, ZapTests) {

      Given("the Organisation user logs in as Limited Company without CT-UTR enrolment having no a registered address in the UK")
      AuthLoginPage.loginAsOrgAdminWithoutCtUtr()
      When("the Organisation user selects 'Limited Company' in the 'What are you registering as?' page")
      OrgRegistrationTypePage.registerOrganisationAs("Limited Company")
      And("the Organisation user selects 'No' in the 'Is your registered address in the UK?' page")
      RegisteredAddressInUkPage.select("No")
      And("the Organisation user selects 'No' in the 'Do you have a UTR' page")
      HaveUtrPage.select("No")
      And("the user enters 'Test Business Name' in 'What is the name of your business?'")
      BusinessNameWithoutIDPage.enterBusinessName("Test Business Name")
      And("the Organisation user selects 'Yes' in the 'Does your business trader under a different name?' page")
      HaveTradingNamePage.select("Yes")
      And("the Organisation user enters the trading name in the 'Does your business trade under a different name?' page")
      TradingNameOfYourBusinessPage.enterTradingNameOfBusiness("Test Trading Pvt Ltd")
      And("the Organisation user enters the address in 'What is the main address of your business?' page")
      BusinessAddressPage.enterMainAddressOfBusiness("Boulevard de Parc", "Chessy", "77700", "Fra")
      And("the Organisation user clicks continue on 'Setting up contact details for cryptoasset reporting' page")
      YourContactDetailsPage.onPageContinueById()
      And("the Organisation user enters the contact name in 'What is the name of the person or team we should contact?' page")
      OrgFirstContactNamePage.enterContactName("John Doe")
      And("the Organisation user enters the first contact's email in the 'What is the email address for the first contact?' page")
      OrgFirstContactEmailPage.enterFirstContactEmail("first.contact@example.com")
      And("the organisation user selects 'Yes' in the 'Can we contact your first contact by phone?' page")
      OrgFirstContactHavePhonePage.select("Yes")
      And("the organisation user enters '01234567890' in the 'What is the phone number for the first contact?' page")
      OrgFirstContactPhonePage.enterFirstContactPhone("01234567890")
      And("the organisation user selects 'Yes' in 'Is there someone else we can contact if your first contact is not available?' page")
      OrgHaveSecondContactPage.select("Yes")
      And("the organisation user enters the second contact name in 'What is the name of the second person or team we should contact?' page")
      OrgSecondContactNamePage.enterName("Jane Smith")
      And("the organisation user enters the email in 'What is the email address for the second contact?' page")
      OrgSecondContactEmailPage.enterSecondContactEmail("second.contact@example.com")
      And("the organisation user selects 'Yes' in 'Can we contact your second contact by phone?' page")
      OrgSecondContactHavePhonePage.select("Yes")
      Then("the organisation user is navigated to the 'What is your phone number for [second contact name]' page should be displayed' ")
      SecondContactPhonePage.onPage()
    }

    Scenario("3 - Organisation user without CT-UTR enrolment registers as a Sole trader having a registered address in the UK", RegistrationTests, ZapTests) {

      Given("the Organisation user logs in as Sole Trader without CT-UTR enrolment")
      AuthLoginPage.loginAsOrgAdminWithoutCtUtr()
      When("the Organisation user selects 'Sole Trader' in the 'What are you registering as?' page")
      OrgRegistrationTypePage.registerOrganisationAs("Sole Trader")
      And("the Organisation user selects 'Yes' in the 'Is your registered address in the UK?' page")
      RegisteredAddressInUkPage.select("Yes")
      And("the Organisation user enters the UTR in the UTR page")
      UtrPage.enterUtr(matchedSaUtr)
      And("the Organisation user enters the first name and last name and click Continue button in the 'What is your name' page")
      YourNamePage.enterNamesAndContinue("Carf", "Tester")
      And("the Organisation user selects 'Yes' on the 'Is this your business?' page for the matched business details")
      IsThisYourBusinessPage.select("Yes")
      And("the Organisation user enters the email address in the 'What is your email address?' page")
      IndEmailPage.enterEmailAddress("carftester@test.com")
      And("the Organisation user selects 'Yes' in the 'Can we contact you by phone' page")
      IndHavePhonePage.select("Yes")
      And("the Organisation user enters their phone number in 'What is your phone number' page")
      IndPhonePage.enterIndividualPhoneNumber("1234567890")
      And("the organisation user clicks on 'Confirm and send' in 'Check your answers before you register for cryptoasset reporting' page")
      CheckYourAnswersPage.onPageSubmitById()
      Then("the Organisation user is routed to 'Registration successful' page")
      ConfirmRegistrationPage.onPage()
    }

    // **************************************************
    //      Organisation user with CT-UTR enrolment
    // **************************************************

    Scenario("4 - Organisation user having CT-UTR enrolment with matched business details", RegistrationTests, ZapTests) {

      Given("the Organisation user logs in with CT-UTR enrolment")
      AuthLoginPage.loginAsOrgAdminWithCtUtr()
      When("the Organisation user selects 'Yes' on the 'Is this your business?' page for the matched business details")
      IsThisYourBusinessPage.select("Yes")
      And("the Organisation user clicks on Continue button on the 'Setting up contact details for cryptoasset reporting' page")
      YourContactDetailsPage.onPageContinueById()
      And("the Organisation user enters the contact name in 'What is the name of the person or team we should contact?' page")
      OrgFirstContactNamePage.enterContactName("John Doe")
      And("the Organisation user enters the first contact's email in the 'What is the email address for the first contact?' page")
      OrgFirstContactEmailPage.enterFirstContactEmail("first.contact@example.com")
      And("the organisation user selects 'Yes' in the 'Can we contact your first contact by phone?' page")
      OrgFirstContactHavePhonePage.select("Yes")
      And("the organisation user enters '01234567890' in the 'What is the phone number for the first contact?' page")
      OrgFirstContactPhonePage.enterFirstContactPhone("01234567890")
      And("the organisation user selects 'Yes' in 'Is there someone else we can contact if your first contact is not available?' page")
      OrgHaveSecondContactPage.select("Yes")
      And("the organisation user enters the second contact name in 'What is the name of the second person or team we should contact?' page")
      OrgSecondContactNamePage.enterName("Jane Smith")
      And("the organisation user enters the email in 'What is the email address for the second contact?' page")
      OrgSecondContactEmailPage.enterSecondContactEmail("second.contact@example.com")
      And("the organisation user selects 'Yes' in 'Can we contact your second contact by phone?' page")
      OrgSecondContactHavePhonePage.select("Yes")
      And("the organisation user enters the second contact phone number in 'What is your phone number for [second contact name]' page ")
      SecondContactPhonePage.enterSecondContactPhone("01234567890")
      And("the organisation user clicks on 'Confirm and send' in 'Check your answers before you register for cryptoasset reporting' page")
      CheckYourAnswersPage.onPageSubmitById()
      Then("the Organisation user is routed to 'Registration successful' page")
      ConfirmRegistrationPage.onPage()
    }
  }
}
