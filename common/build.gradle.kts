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

                // Compose
                api("org.jetbrains.compose.ui:ui:${Versions.compose_jb}")
                api("org.jetbrains.compose.ui:ui-util:${Versions.compose_jb}")
                api("org.jetbrains.compose.ui:ui-tooling:${Versions.compose_jb}")
                api("org.jetbrains.compose.foundation:foundation:${Versions.compose_jb}")
                api("org.jetbrains.compose.material:material:${Versions.compose_jb}")
                api("org.jetbrains.compose.material:material-icons-core:${Versions.compose_jb}")
                api("org.jetbrains.compose.material:material-icons-extended:${Versions.compose_jb}")
            }
        }
        val androidMain by getting {
            dependencies {
                // Compose
                api("androidx.navigation:navigation-compose:${Versions.navigation}")
                api("androidx.activity:activity-compose:1.4.0")

                // Lifecycle
                // api("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}")
                // api("androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}")
                // api("androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}")
                // api("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}")
                // api("androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}")

                // Accompanist
                api("com.google.accompanist:accompanist-pager:${Versions.accompanist}")
                // api("com.google.accompanist:accompanist-pager-indicators:${Versions.accompanist}")
            }
        }
    }
}

android {
    setupLibrary()
}