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
        hasMinLength(6) messageKey "NAME_MIN_LENGTH"
    }
    UserProfile::age required {
        isGreaterOrEqual(18) messageKey "MUST_BE_FULL_AGED"
    }
    UserProfile::password required {
        hasLength(8, 40) messageKey "PASSWORD_COMPLEXITY"
    }
    UserProfile::confirmPassword required {
        isEqual(UserProfile::password) messageKey "CONFIRM_PASSWORD"
    }
    UserProfile::email ifPresent {
        isEmail()
    }
}
