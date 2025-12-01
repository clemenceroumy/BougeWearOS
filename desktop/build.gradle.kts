import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
        commonMain.dependencies {
            implementation(project(":core"))

            // COMPOSE MULTIPLATFORM
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // NETWORK
            implementation(libs.ktor.network)
            api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

            // BLUETOOTH
            implementation(libs.kable.core)

            //DEPENDENCY INJECTION (KOIN)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.core.coroutines)
        }
    }
}


compose.desktop {
    application {
        mainClass = "fr.croumy.bouge.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "fr.croumy.bouge"
            packageVersion = "1.0.0"
        }
    }
}
