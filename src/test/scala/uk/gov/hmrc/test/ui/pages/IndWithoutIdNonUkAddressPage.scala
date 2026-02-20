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
