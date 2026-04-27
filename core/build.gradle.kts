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

                implementation(libs.runtime)
                implementation(libs.foundation)
                implementation(libs.material3)
                implementation(libs.ui)
                implementation(libs.components.resources)
                implementation(libs.ui.tooling.preview)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
            }
        }
    }

}

compose.resources {
    publicResClass = true
    packageOfResClass = "bouge.core.generated.resources"
    generateResClass = auto
}
