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
    Scenario(
      "Organisation user without CT-UTR enrolment having a registered address in the UK",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Organization user logs in without CT-UTR having a registered address in the UK")
      AuthLoginPage.loginAsOrgAdminWithoutCtUtr()
      When("the Organization user enters information to achieve a match on their organisation's data")
      OrgRegistrationTypePage.registerOrganizationAs("Limited Company")
      And("the Organization user selects Yes for the registered address in the UK")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("Yes")
      And("the Organization user enters the UTR in the UTR page")
      UtrPage.enterUtr(matchingCtUtr)
      Then("the page Business Name should be displayed- this is still under development")
      BusinessNamePage.onPage()
    }

    Scenario(
      "Organisation user without CT-UTR enrolment having no registered address in the UK",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Organization user logs in without CT-UTR having no registered address in the UK")
      AuthLoginPage.loginAsOrgAdminWithoutCtUtr()
      When("the Organization user enters information to achieve a match on their organisation's data")
      OrgRegistrationTypePage.registerOrganizationAs("Limited Company")
      And("the Organization user selects No for the registered address in the UK")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("No")
      Then("the page 'Do you have a UTR?' should be displayed - this is still under development--CARF123")
      HaveUtrPage.onPage()
    }

    Scenario(
      "Organisation user with CT-UTR enrolment",
      RegistrationTests,
      ZapTests
    ) {

      Given("the user logs in as an auto-matched Organisation having user role with CT-UTR ")
      AuthLoginPage.loginAsOrgAdminWithCtUtr()
    }

    Scenario(
      "Organisation assistant kick-out page",
      RegistrationTests,
      ZapTests
    ) {

      Given("the user logs in as an Organisation having assistant role ")
      AuthLoginPage.loginAsOrgAdminWithCtUtr()
    }

    Scenario(
      "Organisation user without CT-UTR enrolment registers as a Sole trader",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Organisation user logs in without CT-UTR registers as a Sole trader")
      AuthLoginPage.loginAsOrgAdminWithoutCtUtr()
      When("the user enters information to achieve a match on their sole trader business' data")
      OrgRegistrationTypePage.registerOrganizationAs("Sole Trader")
      And("the Organization user selects No for the registered address in the UK")
      RegisteredAddressInUkPage.registeredAddressInUkYesOrNo("Yes")
      And("the Organization user enters the UTR in the UTR page")
      UtrPage.enterUtr(validSaUtr)
      Then("the page 'Your Name' page should be displayed")
      YourNamePage.onPage()
    }
  }
}
