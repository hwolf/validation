plugins {
    id("java-platform")
    id("publish-conventions")
}

dependencies {
    constraints {
        api(project(":kvalidation-core"))
        api(project(":kvalidation-common"))
        api(project(":kvalidation-i18n"))
        api(project(":kvalidation-icu"))
    }
}
