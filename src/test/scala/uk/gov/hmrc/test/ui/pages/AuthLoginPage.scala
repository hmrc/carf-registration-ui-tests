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
import uk.gov.hmrc.test.ui.conf.TestConfiguration
import uk.gov.hmrc.test.ui.pages.AuthLoginPage.autoMatchedCtUtr

object AuthLoginPage extends BasePage {
  override val pageUrl: String    = TestConfiguration.url("auth-login-stub") + "/gg-sign-in"
  private val redirectUrl: String = TestConfiguration.url("carf-registration-frontend")

  private val redirectionUrlById: By           = By.id("redirectionUrl")
  private val affinityGroupById: By            = By.id("affinityGroupSelect")
  private val credentialRoleById: By           = By.id("credential-role-select")
  private val ninoValueById: By                = By.id("nino")
  private val presetDropDownById: By           = By.id("presets-dropdown")
  private val presetAddById: By                = By.id("add-preset")
  private val identifierValueCtField: By       = By.id("input-4-0-value")
  private val identifierValueSaField: By       = By.id("input-4-0-value")
  private val authSubmitById: By               = By.id("submit-top")
  private val identifierValueNinoField: String = generateNino(individualNino)
  private val identifierCtValue: String        = generateUtr(autoMatchedCtUtr)
  private val identifierSaValue: String        = generateUtr(validSaUtr)

  private def loadPage: this.type = {
    navigateTo(pageUrl)
    onPage()
    this
  }

  private def selectAffinityGroup(affinityGroup: String): Unit =
    selectDropdownById(affinityGroupById).selectByVisibleText(affinityGroup)

  private def selectCredentialRole(credentialRole: String): Unit =
    selectDropdownById(credentialRoleById).selectByVisibleText(credentialRole)

  private def enterNinoValue(): Unit =
    sendKeys(ninoValueById, identifierValueNinoField)

  private def addCtPreset(): Unit =
    selectDropdownById(presetDropDownById).selectByVisibleText("CT")
    click(presetAddById)
    sendKeys(identifierValueCtField, identifierCtValue)

  private def addSaPreset(): Unit =
    selectDropdownById(presetDropDownById).selectByVisibleText("SA")
    click(presetAddById)
    sendKeys(identifierValueSaField, identifierSaValue)

  private def submitAuthPage(): Unit = click(authSubmitById)

  private def submitAuthIndividualWithNino(affinityGroup: String, credentialRole: String): Unit =
    loadPage
    sendKeys(redirectionUrlById, redirectUrl)
    selectAffinityGroup(affinityGroup)
    selectCredentialRole(credentialRole)
    enterNinoValue()
    submitAuthPage()

  private def submitAuthIndividualSoleTraderWithSaUtr(
    affinityGroup: String,
    credentialRole: String
  ): Unit =
    loadPage
    sendKeys(redirectionUrlById, redirectUrl)
    selectAffinityGroup(affinityGroup)
    selectCredentialRole(credentialRole)
    addSaPreset()
    submitAuthPage()

  def loginAsIndividualUserNoBusinessWithNino(): RegistrationTypePage.type = {
    submitAuthIndividualWithNino("Individual", "User")
    RegistrationTypePage // Need to include the step for the proper page display for the Individual not linked to business
  }

  def loginAsIndividualSoleTraderWithSaUtr(): RegistrationTypePage.type = {
    submitAuthIndividualWithNino("Individual", "User")
    RegistrationTypePage // Need to include the step for the proper page display for the Individual - Sole Trader
  }

  def loginAsIndividualWithoutNino(): RegistrationTypePage.type = {
    submitAuthIndividualSoleTraderWithSaUtr("Individual", "User")
    RegistrationTypePage // Need to include the step for the proper page display for the Individual

  }

  def loginAsAgentAsUser(): AgentKickOutPage.type = {
    submitAuthWithoutEnrolment("Agent", "User")
    AgentKickOutPage // Need to include the step for the proper page display for the Agent
  }

  private def submitAuthWithoutEnrolment(affinityGroup: String, credentialRole: String): Unit =
    loadPage
    sendKeys(redirectionUrlById, redirectUrl)
    selectAffinityGroup(affinityGroup)
    selectCredentialRole(credentialRole)
    submitAuthPage()

  private def submitAuthWithCtEnrolment(affinityGroup: String, credentialRole: String): Unit = {
    loadPage
    sendKeys(redirectionUrlById, redirectUrl)
    selectAffinityGroup(affinityGroup)
    selectCredentialRole(credentialRole)
    addCtPreset()
    submitAuthPage()
  }

  def loginAsOrgAdminWithoutCtUtr(): RegistrationTypePage.type = {
    submitAuthWithoutEnrolment("Organisation", "User")
    RegistrationTypePage // Need to include the step for the proper page display for the Organization
  }

  def loginAsOrgAdminWithCtUtr(): RegistrationTypePage.type = {
    submitAuthWithCtEnrolment("Organisation", "User")
    RegistrationTypePage // Need to include the step for the proper page display
  }

  def loginAsOrgAssistant(): OrgAssistantPage.type = {
    submitAuthWithoutEnrolment("Organisation", "Assistant")
    OrgAssistantPage // Need to include the step for the proper page display
  }
}
