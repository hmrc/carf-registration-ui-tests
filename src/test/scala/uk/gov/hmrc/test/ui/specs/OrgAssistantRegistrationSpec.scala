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

class OrgAssistantRegistrationSpec extends BaseSpec {

  Feature("Organisation Assistant CARF Registration") {

    Scenario("1 - Organisation affinity and Assistant credential role", RegistrationTests, ZapTests) {
      Given("the Organisation user logs in as an assistant")
      AuthLoginPage.loginAsOrgAssistant()
      OrgAssistantPage.onPage()
    }
  }
}
