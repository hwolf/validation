apply(plugin = "java-library")
apply(plugin = "org.jetbrains.dokka")

dependencies {
    implementation(project(":kvalidation-core"))

    implementation("org.passay:passay:1.6.1")
}
