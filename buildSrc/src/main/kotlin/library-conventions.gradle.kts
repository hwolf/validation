import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlinx.kover")
    id("org.jetbrains.dokka")
    id("org.sonarqube")
    //id("io.github.gradle-nexus.publish-plugin")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation(platform("io.strikt:strikt-bom:0.34.1"))
    testImplementation(platform("io.kotest:kotest-bom:5.6.2"))

    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-framework-datatest")
    testImplementation("io.kotest:kotest-property")
    testImplementation("io.strikt:strikt-core")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("org.tinylog:tinylog-impl:2.6.2")
    testImplementation("org.tinylog:slf4j-tinylog:2.6.2") {
        exclude(module = "org.slf4j")
    }
}

koverReport {
    defaults {
        xml {
            setReportFile(layout.buildDirectory.file("kover/result.xml"))
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_9)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.register<Jar>("sourceJar") {
    dependsOn(tasks.classes)
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

tasks.register<Jar>("dokkaJar") {
    dependsOn(tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(tasks.dokkaHtml)
}

sonarqube {
    properties {
        property("sonar.projectKey", "hwolf_kvalidation")
        property("sonar.organization", "hwolf")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "**/build/kover/result.xml")
        property("sonar.sources", "src/main")
        property("sonar.tests", "src/test")
    }
}
