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
