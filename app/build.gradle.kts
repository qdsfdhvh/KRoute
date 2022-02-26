plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose") version Versions.compose_jb
}

dependencies {
    implementation(projects.common)
    implementation(projects.persona)
    implementation(projects.wallet)
    implementation(projects.labs)
    implementation(projects.setting)
}

android {
    setupApplication()
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}
