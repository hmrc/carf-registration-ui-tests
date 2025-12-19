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

import org.openqa.selenium.{By, WebDriver}
import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait, Select, Wait}
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.selenium.component.PageObject
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.test.ui.conf.TestConfiguration
import uk.gov.hmrc.test.ui.driver.BrowserDriver
import uk.gov.hmrc.test.ui.utils.IdGenerators

import java.time.Duration

trait BasePage extends BrowserDriver with Matchers with IdGenerators with PageObject {

  val pageUrl: String
  val baseUrl: String         = TestConfiguration.url(
    "carf-registration-frontend"
  )
  val submitButtonId: By      = By.id("submit")
  val continueButtonId: By    = By.id("continue")

  private val yesRadioId = By.id("value")
  private val noRadioId  = By.id("value-no")

  def navigateTo(url: String): Unit = driver.navigate().to(url)

  private def fluentWait: Wait[WebDriver] = new FluentWait[WebDriver](Driver.instance)
    .withTimeout(Duration.ofSeconds(2))
    .pollingEvery(Duration.ofMillis(200))

  def getRadioId(radioOption: String): By =
    radioOption match {
      case "Yes" => yesRadioId
      case "No"  => noRadioId
    }

  def selectRadioAndContinue(id: By): Unit = {
    onPage()
    click(id)
    click(continueButtonId)
  }

  def onPage(url: String = this.pageUrl): Unit = fluentWait.until(ExpectedConditions.urlToBe(url))

  def selectDropdownById(id: By): Select = new Select(driver.findElement(id: By))

  def onPageContinueById(): Unit = {
    onPage()
    click(continueButtonId)
  }

  def clickOnByPartialLinkText(partialLinkText: By): Unit = {
    onPage()
    click(partialLinkText)
  }

  /** Method to loop through each tuple in the list and enter value in one or more text fields
    * @param fieldData
    *   \- variable number of tuples (locator, text)
    */
  def fillFields(fieldData: (By, String)*): Unit = {
    onPage()
    fieldData.foreach { case (locator, value) => sendKeys(locator, value) }
  }

  def fillFieldsAndContinue(fieldData: (By, String)*): Unit = {
    fillFields(fieldData: _*)
    click(continueButtonId)
  }

  /** Method to select from select (dropdown) enhanced with javascript
    * @param inputLocator
    *   \- locator for the select(dropdown)
    * @param searchText
    *   \- text to search for options
    * @param optionLocator
    *   \- option to select from the dropdown list
    */
  def selectFromEnhancedDropdownAndContinue(inputLocator: By, searchText: String, optionLocator: By): Unit = {
    sendKeys(inputLocator, searchText)
    fluentWait.until(ExpectedConditions.elementToBeClickable(optionLocator))
    click(optionLocator)
    click(continueButtonId)
  }

  def verifyOnPageWithPreviousSelection(previousRadioSelection: Option[By]): Unit = {
    onPage()
    previousRadioSelection match {
      case Some(locator) =>
        driver.findElement(locator).isSelected shouldBe true
      case None          =>
        fail("No previous registration type selection was recorded")
    }
  }

}
