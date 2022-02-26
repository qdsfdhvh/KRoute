plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose") version Versions.compose_jb
    id("com.google.devtools.ksp") version Versions.ksp
}

dependencies {
    implementation(projects.common)
    implementation(projects.persona)
    implementation(projects.wallet)
    implementation(projects.labs)
    implementation(projects.setting)

    implementation(projects.compiler.kroute.annotations)
    ksp(projects.compiler.kroute)
}

android {
    setupApplication()
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    sourceSets["debug"].java.srcDir("build/generated/ksp/debug/kotlin")
}
