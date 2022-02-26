plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version Versions.compose_jb
    id("com.google.devtools.ksp") version Versions.ksp
}

kotlin {
    android()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.compiler.kroute.annotations)
                kspAndroid(projects.compiler.kroute)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(projects.common)
            }
        }
    }
}

android {
    setupLibrary()
}