plugins {
    id("library-conventions")
}

dependencies {
    implementation(project(":kvalidation-core"))

    implementation("org.tinylog:tinylog-api-kotlin:2.6.2")
    implementation("io.pebbletemplates:pebble:3.2.1")
}
