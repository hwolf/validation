
plugins {
    id("org.sonarqube") version "4.3.0.3225"
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
