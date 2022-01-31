plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp") version Versions.ksp
}

dependencies {
    implementation(projects.common)
    implementation(projects.persona)
    implementation(projects.wallet)
    implementation(projects.labs)
    implementation(projects.setting)

    implementation(projects.compiler.kroute)
    ksp(projects.compiler.kroute)
}

android {
    setupApplication()
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    sourceSets {
        getByName("debug") {
            java.srcDir(File("build/generated/ksp/debug/kotlin"))
        }
    }
}
