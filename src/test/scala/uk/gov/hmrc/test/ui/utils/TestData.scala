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

package uk.gov.hmrc.test.ui.utils

object TestData {

  private val env: String =
    System.getProperty("environment", "local").toLowerCase

  def postcode: String = env match {
    case "local"   => "ZZ01 1ZZ"
    case "qa"      => "QA11 1AA"
    case "staging" => "LU1 5JP"
    case _         => throw new IllegalArgumentException(s"Unsupported environment: $env")
  }

  def propertyNumber: String = env match {
    case "local"   => "3"
    case "qa"      => "5"
    case "staging" => "7"
    case _         => throw new IllegalArgumentException(s"Unsupported environment: $env")
  }
}
