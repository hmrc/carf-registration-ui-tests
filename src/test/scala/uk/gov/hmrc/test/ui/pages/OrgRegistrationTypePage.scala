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

package uk.gov.hmrc.test.ui.pages

import org.openqa.selenium.By

object OrgRegistrationTypePage extends BasePage {
  override val pageUrl: String = baseUrl + "/register/organisation-registration-type"

  private val limitedCompanyRadioId = By.id("value_0")
  private val partnershipRadioId    = By.id("value_1")
  private val llpRadioId            = By.id("value_2")
  private val unincorporatedRadioId = By.id("value_3")
  private val soleTraderRadioId     = By.id("value_4")

  def getId(registrationType: String): By =
    registrationType match {
      case "Limited Company" => limitedCompanyRadioId
      case "Partnership"     => partnershipRadioId
      case "LLP"             => llpRadioId
      case "Unincorporated"  => unincorporatedRadioId
      case "Sole Trader"     => soleTraderRadioId
    }

  def registerAs(registrationType: String): Unit =
    selectRadioAndContinue(getId(registrationType))
}
