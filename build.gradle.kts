plugins {
    application
    kotlin("jvm") version "1.6.10"
}

group = "org.yttr"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

repositories {
    mavenCentral()
}

tasks.create("stage") {
    dependsOn("installDist")
}

internal val ktorVersion: String by project
internal val kotlinVersion: String by project
internal val logbackVersion: String by project

dependencies {
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    // Ktor server
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-cio:$ktorVersion")
    implementation("io.ktor:ktor-server-sessions:$ktorVersion")
    implementation("io.ktor:ktor-html-builder:$ktorVersion")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-css:1.0.0-pre.291-kotlin-1.6.10")
    // Webjars
    implementation("io.ktor:ktor-webjars:$ktorVersion")
    implementation("org.webjars.npm:hotwired__turbo:7.1.0")
    // Tests
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation(kotlin("test"))
}
