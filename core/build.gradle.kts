plugins {
    alias(libs.plugins.kotlinMultiplatform)
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

    sourceSets {
        commonMain {
            dependencies {
                implementation("dev.icerock.moko:resources:0.25.1")
            }
        }
    }
}

multiplatformResources {
    resourcesPackage.set("fr.croumy.bouge.core.mr")
    resourcesClassName.set("SharedRes") //default "MR"
}