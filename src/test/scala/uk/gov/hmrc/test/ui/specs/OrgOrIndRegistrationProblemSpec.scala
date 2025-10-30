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

class OrgOrIndRegistrationProblemSpec extends BaseSpec {

  Feature("Organisation or Individual Registration Problem Page Validation") {

    // **********************************************
    //           Individual Problem Pages
    // **********************************************

    Scenario(
      "Individual user without NINO - Sole trader - having a registered address outside the UK and SAUTR unmatched",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Individual user logs in as a sole trader without NINO having a registered address in the UK")
      AuthLoginPage.loginAsIndividualWithoutNino()
      When("the Individual user selects 'Sole trader' in the 'What are you registering as?' page")
      IndRegistrationTypePage.registerIndividualAs("Sole Trader")
      And("the Individual user selects 'Yes' in the 'Is your registered address in the UK?' page")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("Yes")
      And("the Individual user enters the Self Assessment UTR in the UTR page")
      UtrPage.enterUtr(unmatchedSaUtr)
      And(
        "the Individual user enters the first name and last name and click Continue button in the 'What is your name' page"
      )
      YourNamePage.enterNamesAndClickContinue()
      Then("The page 'The details you entered did not match our records' should be displayed")
      SoleTraderNotIdentifiedPage.onPage()
    }

    // **********************************************
    //           Organisation Problem Pages
    // **********************************************

    Scenario(
      "Organisation user without CT-UTR enrolment having a registered address in the UK with unmatched business details",
      RegistrationTests,
      ZapTests
    ) {
      Given(
        "the Organisation user logs in as Limited Company without CT-UTR enrolment having a registered address in the UK with unmatched business details"
      )
      AuthLoginPage.loginAsOrgAdminWithoutCtUtr()
      When("the Organisation user selects 'Limited Company' in the 'What are you registering as?' page")
      OrgRegistrationTypePage.registerOrganisationAs("Limited Company")
      And("the Organisation user selects 'Yes' in the 'Is your registered address in the UK?' page")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("Yes")
      And("the Organisation user enters the UTR in the UTR page")
      UtrPage.enterUtr(unMatchedCtUtrForBusinessName)
      And("the user enters the unmatched business name in the 'What is the registered name of your business?' page")
      BusinessNamePage.enterBusinessName("unmatched")
      Then(
        "the Organisation user is navigated to 'The details you entered did not match our records' page"
      )
      ProblemBusinessNotIdentifiedPage.onPage()
    }

    // this is a problem page scenario that can be kept in another spec for all organisation error pages if this is not covered in unit tests
    Scenario(
      "Organisation user having CT-UTR enrolment with unmatched business details",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Organisation user logs in with CT-UTR enrolment")
      AuthLoginPage.loginAsOrgAdminWithCtUtr()
      When("the Organisation user selects 'No' on the 'Is this your business?' page for the unmatched business details")
      IsThisYourBusinessPage.yourBusinessYesOrNo("No")
      Then(
        "the Organisation user is navigated to 'You're unable to use this service with this Government Gateway user ID' page"
      )
      ProblemDifferentBusinessPage.onPage()
    }
  }
}
