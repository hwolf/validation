plugins {
    id("library-conventions")
}

apply(plugin = "java-library")
apply(plugin = "org.jetbrains.dokka")

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
}
