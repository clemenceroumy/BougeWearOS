plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.composeHotReload)

    // can't use atm because moko-resources not up to date -> https://github.com/icerockdev/moko-resources/issues/843
    //alias(libs.plugins.android.kotlinMultiplatform.library)
    // using deprecated com.android.library until moko-resources is fixed
    id("com.android.library")

    id("dev.icerock.mobile.multiplatform-resources")
}

android {
    namespace = "fr.croumy.bouge.core"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }

    sourceSets["main"].res.srcDir(File(buildDir, "generated/moko-resources/androidMain/res"))
}

kotlin {
    androidTarget()
    // uncomment when using libs.plugins.android.kotlinMultiplatform.library
    /*androidLibrary {
        namespace = "fr.croumy.bouge.core"
        compileSdk = 36
        minSdk = 24

        withJava()
        androidResources { enable = true }
    }*/
    jvm("core")

    sourceSets {
        commonMain {
            dependencies {
                implementation("dev.icerock.moko:resources:0.25.1")
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

multiplatformResources {
    resourcesPackage.set("fr.croumy.bouge.core.mr")
    resourcesClassName.set("SharedRes") //default "MR"
}