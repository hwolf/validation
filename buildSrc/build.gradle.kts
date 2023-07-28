plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    implementation("org.jetbrains.kotlinx.kover:org.jetbrains.kotlinx.kover.gradle.plugin:0.7.3")
    implementation("org.jetbrains.dokka:org.jetbrains.dokka.gradle.plugin:1.8.20")
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:4.3.0.3225")
    //implementation("io.github.gradle-nexus:publish-plugin:1.3.0")
}
