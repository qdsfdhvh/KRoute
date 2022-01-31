pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "KRoute"

include(
    ":app",
    ":common",
    ":compiler:kroute",
    ":labs",
    ":labs:export",
    ":persona",
    ":persona:export",
    ":setting",
    ":setting:export",
    ":wallet",
    ":wallet:export",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
