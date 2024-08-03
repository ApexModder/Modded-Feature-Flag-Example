dependencyResolutionManagement {
    versionCatalogs.create("libraries") {
        version("java", "21")
        version("minecraft", "1.21.0")
        version("neoforge", "21.0.169-pr-1322-pr-modded-flags")
        version("parchment-mappings", "2024.07.07")
        version("parchment-minecraft", "1.21")

        plugin("moddev", "net.neoforged.moddev").version("1.0.11")
        library("devlogin", "net.covers1624", "DevLogin").version("0.1.0.5")
        plugin("idea-ext", "org.jetbrains.gradle.plugin.idea-ext").version("1.1.8")
        plugin("gradleutils", "net.neoforged.gradleutils").version("3.0.0")
    }
}

pluginManagement.repositories {
    gradlePluginPortal()
    maven("https://maven.neoforged.net/releases")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")
}

rootProject.name = "Modded-Feature-Flag-Example"