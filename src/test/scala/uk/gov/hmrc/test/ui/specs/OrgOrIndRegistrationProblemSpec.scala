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
import uk.gov.hmrc.test.ui.pages.ProblemBusinessNotIdentifiedPage.tryAgainPartialLink
import uk.gov.hmrc.test.ui.specs.tags.*
import uk.gov.hmrc.test.ui.pages.ProblemDifferentBusinessPage.signInLnk

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
      And("the Individual user selects 'No' in the 'Is your registered address in the UK?' page")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("No")
      And("the Organisation user selects 'Yes' in the 'Do you have a UTR' page")
      HaveUtrPage.selectUtrYesOrNo("Yes")
      And("the Individual user enters the Self Assessment UTR in the UTR page")
      UtrPage.enterUtr(unmatchedSaUtr)
      And(
        "the Individual user enters the first name and last name and click Continue button in the 'What is your name' page"
      )
      YourNamePage.enterNamesAndContinue("Carf", "Tester")
      When(
        "The Individual user clicks on 'try again using different details' link in the 'The details you entered did not match our records' page"
      )
      SoleTraderNotIdentifiedPage.clickOnByPartialLinkText(tryAgainPartialLink)
      Then(
        "the Individual user should be routed to 'What are you registering as?' page and his previous selection should be retained"
      )
      IndRegistrationTypePage.verifyPreviousSelection("Sole Trader")
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
      When(
        "the Organisation user clicks on 'try again using different details' link in the 'The details you entered did not match our records' page"
      )
      ProblemBusinessNotIdentifiedPage.clickOnByPartialLinkText(tryAgainPartialLink)
      Then(
        "the organisation user should be routed to 'What are you registering as?' page and his previous selection should be retained"
      )
      OrgRegistrationTypePage.verifyPreviousSelection("Limited Company")
    }

    Scenario(
      "Organisation user having CT-UTR enrolment with unmatched business details",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Organisation user logs in with CT-UTR enrolment")
      AuthLoginPage.loginAsOrgAdminWithCtUtr()
      When("the Organisation user selects 'No' on the 'Is this your business?' page for the unmatched business details")
      IsThisYourBusinessPage.yourBusinessYesOrNo("No")
      And(
        "the Organisation user clicks on 'sign in with the Government Gateway user ID for the organisation you wish to register' link"
      )
      ProblemDifferentBusinessPage.clickOnByPartialLinkText(signInLnk)
      Then("the Organisation user should be taken to the GG sign in page")
      SignOutPage.onPage()
    }
    // ************************************************
    //     Organisation assistant kick-out page
    // ************************************************

    Scenario(
      "Organisation assistant kick-out page",
      RegistrationTests,
      ZapTests
    ) {

      Given("the user logs in as an Organisation having assistant role ")
      AuthLoginPage.loginAsOrgAdminWithCtUtr()
    }
  }
}
