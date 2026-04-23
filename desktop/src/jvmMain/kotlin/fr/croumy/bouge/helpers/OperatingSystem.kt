package fr.croumy.bouge.helpers

enum class OperatingSystem {
    Windows, MacOS, Linux, Unknown
}

val currentOS: OperatingSystem get() {
    val os = System.getProperty("os.name").lowercase()
    return when {
        os.contains("windows") -> OperatingSystem.Windows
        os.contains("mac") -> OperatingSystem.MacOS
        os.contains("linux") -> OperatingSystem.Linux
        else -> OperatingSystem.Unknown
    }
}