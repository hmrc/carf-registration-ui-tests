/*
 * Copyright 2026 HM Revenue & Customs
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

object IndWithoutIdNonUkAddressPage extends BasePage {
  override val pageUrl: String = baseUrl + "/register/individual-without-id/address-non-uk"

  private val addressLine1ID  = By.id("addressLine1")
  private val townOrCityID    = By.id("townOrCity")
  private val countryID       = By.id("country")
  private val countryOptionID = By.id("country__option--0")

  def enterYourAddress(addressLine1: String, townOrCity: String, countrySubstring: String): Unit = {
    fillFields((addressLine1ID, addressLine1), (townOrCityID, townOrCity))
    selectFromEnhancedDropdownAndContinue(countryID, countrySubstring, countryOptionID)
  }
}
