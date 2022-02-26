pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.mozilla.org/maven2/")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "KRoute"

include(
    ":app",
    ":common",
    ":compiler:kroute",
    ":compiler:kroute:annotations",
    ":labs",
    ":persona",
    ":setting",
    ":wallet",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
