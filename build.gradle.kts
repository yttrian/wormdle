plugins {
    alias(libs.plugins.ktor)
}

tasks.create("stage") {
    dependsOn("installDist")
}
