plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = Package.group
version = Package.versionName

kotlin {
    android()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.wallet.export)
                api(projects.labs.export)
                api(projects.persona.export)
                api(projects.setting.export)

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
                api("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}")
                api("androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}")
                api("androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}")
                api("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}")
                api("androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}")

                api("androidx.navigation:navigation-compose:${Versions.navigation}")
                api("androidx.activity:activity-compose:1.4.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
    }
}

android {
    setupLibrary()
}