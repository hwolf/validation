plugins {
    id("library-conventions")
    id("publish-conventions")
}

dependencies {
    implementation(project(":kvalidation-core"))

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.tinylog:tinylog-api-kotlin:2.6.2")
    implementation("com.ibm.icu:icu4j:74.1")
}
