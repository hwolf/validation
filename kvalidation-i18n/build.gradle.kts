apply(plugin = "java-library")
apply(plugin = "org.jetbrains.dokka")

dependencies {
    implementation(project(":kvalidation-core"))

    implementation("org.tinylog:tinylog-api-kotlin:2.5.0")
    implementation("io.pebbletemplates:pebble:3.1.5")
}
