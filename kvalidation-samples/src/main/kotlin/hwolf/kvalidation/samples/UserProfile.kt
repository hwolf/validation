package hwolf.kvalidation.samples

import hwolf.kvalidation.common.isEmail
import hwolf.kvalidation.hasLength
import hwolf.kvalidation.isEqual
import hwolf.kvalidation.isGreaterOrEqual
import hwolf.kvalidation.isNotBlank
import hwolf.kvalidation.validator

data class UserProfile(
    val name: String,
    val age: Int?,
    val password: String?,
    val confirmPassword: String?,
    val email: String?
)

val userProfileValidator = validator<UserProfile> {
    UserProfile::name {
        isNotBlank()
        hasMinLength(6)
    }
    UserProfile::age required {
        isGreaterOrEqual(18)
    }
    UserProfile::password required {
        hasLength(8, 40)
    }
    UserProfile::confirmPassword required {
        isEqual(UserProfile::password)
    }
    UserProfile::email ifPresent {
        isEmail()
    }
}
