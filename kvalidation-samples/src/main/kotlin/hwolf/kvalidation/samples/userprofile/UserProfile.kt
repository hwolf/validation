package hwolf.kvalidation.samples.userprofile

import hwolf.kvalidation.*
import hwolf.kvalidation.common.isEmail

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
        hasLength(6, 20)
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
