/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hwolf.kvalidation.samples

import hwolf.kvalidation.i18n.messageInterpolator
import hwolf.kvalidation.validate
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.contains
import java.util.*

class UserProfileTests {

    @Test
    fun example() {
        val invalidUserProfile = UserProfile(
            name = "Mr. X",
            age = 16,
            password = "geheim",
            confirmPassword = "anders",
            email = "keine Mail")

        val violations = userProfileValidator.validate(invalidUserProfile)

        val messageInterpolator = messageInterpolator("hwolf.kvalidation.samples.UserProfileTests")
        val messages = violations.violations.map {
            messageInterpolator.interpolate(it, Locale.GERMAN)
        }
        expectThat(messages).contains(
            "Your name must have at least 6 characters",
            "You must be older than 18",
            "The password must have between 8 and 40 characters",
            "Please repeat the password from field password",
            "The field  must contain a valid email address")
    }
}
