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

import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait, Select, Wait}
import org.openqa.selenium.{By, WebDriver}
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.selenium.component.PageObject
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.test.ui.conf.TestConfiguration
import uk.gov.hmrc.test.ui.driver.BrowserDriver
import uk.gov.hmrc.test.ui.utils.IdGenerators
import scala.jdk.CollectionConverters.*
import java.time.Duration

trait BasePage extends BrowserDriver with Matchers with IdGenerators with PageObject {

  val pageUrl: String
  val baseUrl: String      = TestConfiguration.url("carf-registration-frontend")
  val submitButtonId: By   = By.id("submit")
  val continueButtonId: By = By.id("continue")

  val yesRadioId: By = By.id("value")
  val noRadioId: By  = By.id("value-no")

  def navigateTo(url: String): Unit = driver.navigate().to(url)

  private def fluentWait(timeoutSeconds: Long = 8): Wait[WebDriver] = new FluentWait[WebDriver](Driver.instance)
    .withTimeout(Duration.ofSeconds(timeoutSeconds))
    .pollingEvery(Duration.ofMillis(200))
    .ignoring(classOf[org.openqa.selenium.StaleElementReferenceException])
    .ignoring(classOf[org.openqa.selenium.NoSuchElementException])

  def onPage(pageUrl: String = this.pageUrl, timeoutSeconds: Long = 3): Unit =
    fluentWait(timeoutSeconds).until(ExpectedConditions.urlToBe(pageUrl))

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

//  def selectYes(pageUrl: String = this.pageUrl): Unit = {
//    onPage(pageUrl)
//    assertLocatorPresent(yesRadioId)
//    click(yesRadioId)
//    click(continueButtonId)
//  }
//
//  def selectNo(pageUrl: String = this.pageUrl): Unit = {
//    onPage(pageUrl)
//    assertLocatorPresent(noRadioId)
//    click(noRadioId)
//    click(continueButtonId)
//  }

  def select(option: String, pageUrl: String = this.pageUrl): Unit = {
    onPage(pageUrl)

    val radioId = option.trim.toLowerCase match {
      case "yes" => yesRadioId
      case "no"  => noRadioId
      case other => throw new IllegalArgumentException(s"Invalid option: '$other'. Use 'Yes' or 'No'.")
    }

    assertLocatorPresent(radioId)
    click(radioId)
    click(continueButtonId)
  }
  protected def assertLocatorPresent(locator: By): Unit            = {
    val elements = Driver.instance.findElements(locator).asScala
    require(
      elements.nonEmpty,
      s"Expected element with locator [$locator] to be present, but none was found"
    )
  }

  def selectDropdownById(id: By): Select = new Select(driver.findElement(id: By))

  def onPageContinueById(): Unit = {
    onPage()
    click(continueButtonId)
  }

  def onPageSubmitById(): Unit = {
    onPage()
    click(submitButtonId)
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
  def selectFromEnhancedDropdownAndContinue(inputLocator: By, searchText: String, optionLocator: By, timeoutSeconds: Long = 5): Unit = {
    sendKeys(inputLocator, searchText)
    fluentWait(timeoutSeconds).until(ExpectedConditions.elementToBeClickable(optionLocator))
    click(optionLocator)
    click(continueButtonId)
  }

  def verifyOnPageWithPreviousSelection(previousRadioSelection: By): Unit = {
    onPage()
    driver.findElement(previousRadioSelection).isSelected shouldBe true
  }
}
