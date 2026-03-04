package uk.gov.hmrc.test.ui.specs

import uk.gov.hmrc.test.ui.pages.*

class ProblemPagesSpec extends BaseSpec {

  Feature("Knock back and Problem pages validation") {

    Scenario("1 - Already registered individual knock back page") {
      Given("the Individual user logs in as an individual not connected to a business with NINO")
      AuthLoginPage.loginAsIndividualWithNino()
      When("the Individual user selects 'An Individual not connected to a business' in the 'What are you registering as?' page")
      IndRegistrationTypePage.registerIndividualAs("An individual not connected to a business")
      And("the Individual user selects 'Yes' in the 'Do you have a National Insurance number?' page")
      HaveNiNumberPage.select("Yes")
      And("the Individual user enters the NINO in the 'What is your National Insurance number' page")
      NiNumberPage.enterNino(alreadyRegisteredNino)
      And("the Individual user enters the 'First name' and 'Last name' in the 'What is your name?' page")
      IndNamePage.enterName("Carf", "Tester")
      And("the Individual user enters the date of birth in the 'What is your date of birth?' page")
      IndDOBPage.enterDateOfBirth("01", "JAN", "1901")
      And("the Individual user clicks the Continue button in the 'We have confirmed your identity' page")
      ConfirmedIdentityPage.onPageContinueById()
      And("the Individual user enters the email address in the 'What is your email address' page")
      IndEmailPage.enterEmailAddress("carftester@test.com")
      And("the Individual user selects 'Yes' in the 'Can we contact you by phone' page")
      IndHavePhonePage.select("Yes")
      And("the Individual user enters their phone number in 'What is your phone number' page")
      IndPhonePage.enterIndividualPhoneNumber("1234567890")
      And("the Individual user clicks on 'Confirm and send' in 'Check your answers before you register for cryptoasset reporting' page")
      CheckYourAnswersPage.onPageSubmitById()
      Then("the Individual user is routed to 'Already Registration' page")
      IndAlreadyRegisteredPage.onPage()
    }
  }
}
