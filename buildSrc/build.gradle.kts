plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.23")
    implementation("org.jetbrains.kotlinx.kover:org.jetbrains.kotlinx.kover.gradle.plugin:0.7.6")
    implementation("org.jetbrains.dokka:org.jetbrains.dokka.gradle.plugin:1.9.20")
}
