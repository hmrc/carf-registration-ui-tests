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

package uk.gov.hmrc.test.ui.pages.indWithoutNino

import org.openqa.selenium.By
import uk.gov.hmrc.test.ui.pages.BasePage
object IndWithoutIdChooseAddress extends BasePage {

  override val pageUrl: String = baseUrl + "/register/individual-without-id/choose-address"

  private val secondAddressRadioButtonId = By.id("value-2")
  private val noneOfTheseRadioButtonId   = By.id("value-none")

  def selectAddressAs(addressButton: String): Unit = {
    val radioId = addressButton.trim.toLowerCase match {
      case "second address" => secondAddressRadioButtonId
      case "none of these"  => noneOfTheseRadioButtonId
      case other            =>
        throw new IllegalArgumentException(
          s"Invalid address option: '$addressButton'. Use 'Second Address' or 'None of these'."
        )
    }
    selectRadioAndContinue(radioId)
  }
}
