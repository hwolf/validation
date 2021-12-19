package hwolf.kvalidation.samples.userprofile

import hwolf.kvalidation.validate
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isFalse

class UserProfileTests {

    @Test
    fun example() {
        val invalidUserProfile = UserProfile(
            name = "Mr. X",
            age = 16,
            password = "geheim",
            confirmPassword = "anders",
            email = "keine Mail")

        val result = userProfileValidator.validate(invalidUserProfile)

        expectThat(result.isValid).isFalse()
    }
}
