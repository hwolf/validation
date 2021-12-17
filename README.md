# validations for Kotlin

[![Build](https://github.com/hwolf/validation/actions/workflows/build.yaml/badge.svg)](https://github.com/hwolf/validation/actions/workflows/build.yaml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=hwolf_validation&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=hwolf_validation)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

**kvalidation** -  Validation library for Kotlin

## Installation

Add to `gradle.build.kts`:

```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/hwolf/validation")
    }
}

dependencies {
    implementation("hwolf.kvalidation:kvalidation-core:0.1.0")
    implementation("hwolf.kvalidation:kvalidation-common:0.1.0")
}
```

## Usage

Suppose you have a class like this:
```kotlin
data class UserProfile(
    val name: String,
    val age: Int?,
    val password: String?,
    val confirmPassword: String?,
    val email: String?
)
```
Using the type-safe DSL you can write a validator:
```kotlin
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
```
and apply it to your data
```kotlin
val invalidUserProfile = UserProfile(
        name = "Mr. X",
        age = 16,
        password = "geheim",
        confirmPassword = "anders",
        email = "keine Mail")
val result = userProfileValidator.validate(invalidUserProfile)
```

## Creating a custom validation

It's possible to create custom validations in three steps:

### 1. Define the constraint

To create a custom constraint, it's necessary to implement the interface 
`hwolf.kvalidation.Constraint`, which has these properties:
* `messageKey`: specifies the name of the key for interpolating messages, the default 
  value is the qualified class name plus `message` suffix.
* `parameters`: specifies a `Map<String, Amy?>` containing the parameters to be 
  replaced in the message, the default values are all class properties, obtained 
  through reflection.

For example:

```kotlin
data class MinLength<T>(val min: T) : Constraint
```

### 2. Create the extension function

The validation logic must be within an extension function of 
`hwolf.kvalidation.ConstraintBuilder<T>.validate.validate(constraint: Constraint, test: (V) -> Boolean)`, where 
* `T` represents the type of the property
* `constraint` the constraint
* `test` a validation function

For example:

```kotlin
fun ConstraintBuilder<String>.hasMinLength(min: Int) =
  validate(MinLength(min)) {
      it >= min
  }
```
