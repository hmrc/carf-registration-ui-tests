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
import uk.gov.hmrc.test.ui.pages.AgentKickOutPage.partialLinkText
import uk.gov.hmrc.test.ui.specs.tags.*

class AgentRegistrationSpec extends BaseSpec {

  Feature("Agent CARF Registration") {

    // All the scenarios and steps will be updated later

    Scenario(
      "Agent affinity and User credential role",
      RegistrationTests,
      ZapTests
    ) {

      Given("the Agent user logs in")
      AuthLoginPage.loginAsAgentAsUser()
      And("the Agent clicks on the link 'sign in with an organisation or individual Government Gateway user ID'")
      AgentKickOutPage.clickOnByPartialLinkText(partialLinkText)
      Then("the Agent user is navigated to the sign-out page")
      SignOutPage.onPage()
    }
  }
}
