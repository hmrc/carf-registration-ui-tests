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
    // 1.       "Organisation user without CT-UTR enrolment having a registered address in the UK with matched business details"
    // 2.       "Organisation user without CT-UTR enrolment having no registered address in the UK" //placeholder CARF-148
    // 3.       "Organisation user without CT-UTR enrolment registers as a Sole trader" //placeholder for CARF-125
    // 4.       "Organisation user without CT-UTR enrolment having a registered address in the UK with unmatched business details"
    // 5.       "Organisation user having CT-UTR enrolment with matched business details" //placeholder for CARF 177
    // 6.       "Organisation user having CT-UTR enrolment with unmatched business details" //placeholder for 127
    // 7.       "Organisation assistant kick-out page"

    // **************************************************
    //    Organisation user without CT-UTR enrolment
    // **************************************************

    Scenario(
      "Organisation user without CT-UTR enrolment having a registered address in the UK with matched business details",
      RegistrationTests,
      ZapTests
    ) {

      Given(
        "the Organisation user logs in as Limited Company without CT-UTR enrolment having a registered address in the UK with matched business details"
      )
      AuthLoginPage.loginAsOrgAdminWithoutCtUtr()
      When("the Organisation user selects 'Limited Company' in the 'What are you registering as?' page")
      OrgRegistrationTypePage.registerOrganisationAs("Limited Company")
      And("the Organisation user selects 'Yes' in the 'Is your registered address in the UK?' page")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("Yes")
      And("the Organisation user enters the UTR in the UTR page")
      UtrPage.enterUtr(autoMatchedCtUtrForUK)
      And("the user enters the matched business name in the 'What is the registered name of your business?' page")
      BusinessNamePage.enterBusinessName("matched")
      And("the Organisation user selects 'Yes' on the 'Is this your business?' page for the matched business details")
      IsThisYourBusinessPage.yourBusinessYesOrNo("Yes")
      And(
        "the Organisation user clicks on Continue button on the 'Setting up contact details for cryptoasset reporting' page"
      )
      YourContactDetailsPage.onPageContinueById()
      Then("the Organisation user is navigated to 'What is the name of the person or team we should contact?' page")
      ContactNamePage.onPage()
    }

    Scenario(
      "Organisation user without CT-UTR enrolment having no registered address in the UK",
      RegistrationTests,
      ZapTests
    ) {

      Given(
        "the Organisation user logs in as Limited Company without CT-UTR enrolment having no a registered address in the UK"
      )
      AuthLoginPage.loginAsOrgAdminWithoutCtUtr()
      When("the Organisation user selects 'Limited Company' in the 'What are you registering as?' page")
      OrgRegistrationTypePage.registerOrganisationAs("Limited Company")
      And("the Organisation user selects 'No' in the 'Is your registered address in the UK?' page")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("No")
      And("the Organisation user selects 'No' in the 'Do you have a UTR' page")
      HaveUtrPage.haveUtrYesOrNo("No")
      Then("the page 'What is the name of your business?' is displayed - this is still under development CARF-148")
      BusinessNameWithoutIDPage.onPage()
    }

    Scenario(
      "Organisation user without CT-UTR enrolment registers as a Sole trader",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Organisation user logs in as Sole Trader without CT-UTR enrolment")
      AuthLoginPage.loginAsOrgAdminWithoutCtUtr()
      When("the Organisation user selects 'Sole Trader' in the 'What are you registering as?' page")
      OrgRegistrationTypePage.registerOrganisationAs("Sole Trader")
      And("the Organisation user selects 'Yes' in the 'Is your registered address in the UK?' page")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("Yes")
      And("the Organisation user enters the UTR in the UTR page")
      UtrPage.enterUtr(validSaUtr)
      Then("the page 'What is your name' should be displayed- this is still under development")
      YourNamePage.onPage()
    }

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

    // ***************************************************
    //      Organisation user with CT-UTR enrolment
    // ***************************************************

    Scenario(
      "Organisation user having CT-UTR enrolment with matched business details",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Organisation user logs in with CT-UTR enrolment")
      AuthLoginPage.loginAsOrgAdminWithCtUtr()
      When("the Organisation user selects 'Yes' on the 'Is this your business?' page for the matched business details")
      IsThisYourBusinessPage.yourBusinessYesOrNo("Yes")
      And(
        "the Organisation user clicks on Continue button on the 'Setting up contact details for cryptoasset reporting' page"
      )
      YourContactDetailsPage.onPageContinueById()
      Then("the Organisation user is navigated to 'What is the name of the person or team we should contact?' page")
      ContactNamePage.onPage()
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

    // *************************************************
    //     Organisation assistant kick-out page
    // *************************************************

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
