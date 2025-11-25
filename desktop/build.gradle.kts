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

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.ktor.network)

            api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            implementation("com.juul.kable:kable-core:0.40.0")
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
