plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.android.kotlinMultiplatform.library)
    id("kotlinx-serialization")
}

kotlin {
    androidLibrary {
        namespace = "fr.croumy.bouge.core"
        compileSdk = 36
        minSdk = 24

        withJava()
        androidResources.enable = true
    }

    jvm("core")

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.serialization.json)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
            }
        }
    }
}