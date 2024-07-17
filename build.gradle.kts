plugins {
    id("idea")
    id("java-library")
    alias(libraries.plugins.idea.ext)
    alias(libraries.plugins.moddev)
    alias(libraries.plugins.gradleutils)
}

changelog {
    from(libraries.versions.minecraft.get().substring(2))
    disableAutomaticPublicationRegistration()
}

val MOD_NAME = "Modded Featured Flag - Example Mod"
val MOD_ID = "flag_example_mod"
val MOD_VERSION = gradleutils.version.toString()

val JAVA_VERSION = JavaLanguageVersion.of(libraries.versions.java.get())

val IS_CI = System.getenv("CI_BUILD").toBoolean()

println("$MOD_NAME v$MOD_VERSION")

if(IS_CI) println("CI Build Detected!")

base {
    group = "dev.apexstudios"
    base.archivesName = MOD_ID
    version = MOD_VERSION
}

idea.module {
    if(!IS_CI) {
        isDownloadSources = true
        isDownloadJavadoc = true
    }

    excludeDirs.addAll(files(
        ".gradle",
        ".idea",
        ".build",
        "gradle",
        "run",
        "src/data/generated/.cache"
    ))
}

sourceSets {
    main {
        resources {
            setSrcDirs(files("src/main/resources", "src/data/generated"))

            exclude(".cache")
        }
    }

    test {
        java.setSrcDirs(files())
        resources.setSrcDirs(files())
    }

    create("data") {
        java.setSrcDirs(files("src/data/java"))
        resources.setSrcDirs(files())
        neoForge.addModdingDependenciesTo(this)

        val main = sourceSets["main"]
        compileClasspath += main.output
        runtimeClasspath += main.output
    }
}

java {
    withSourcesJar()

    toolchain {
        vendor = if(IS_CI) JvmVendorSpec.ADOPTIUM else JvmVendorSpec.JETBRAINS
        languageVersion.set(JAVA_VERSION)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(JAVA_VERSION.asInt())

    if(!IS_CI) {
        options.compilerArgs.addAll(arrayOf("-Xmaxerrs", "9000"))
    }

    javaToolchains.compilerFor {
        languageVersion.set(JAVA_VERSION)
    }
}

tasks.withType<ProcessResources> {
    val replacements = mapOf(
        Pair("MOD_NAME", MOD_NAME),
        Pair("MOD_ID", MOD_ID),
        Pair("MOD_VERSION", MOD_VERSION)
    )

    inputs.properties(replacements)

    filesMatching("META-INF/neoforge.mods.toml") {
        expand(replacements)
    }
}

repositories {
    // mavenLocal()
    maven("https://maven.apexstudios.dev/releases")
    maven("https://prmaven.neoforged.net/NeoForge/pr1322")
}

neoForge {
    version = libraries.versions.neoforge

    parchment {
        mappingsVersion = libraries.versions.parchment.mappings
        minecraftVersion = libraries.versions.parchment.minecraft
    }

    if(file("src/main/resources/META-INF/accesstransformer.cfg").exists()) {
        accessTransformers.from("src/main/resources/META-INF/accesstransformer.cfg")
    }

    mods {
        create("main") {
            sourceSet(sourceSets["main"])
        }

        create("data") {
            sourceSet(sourceSets["main"])
            sourceSet(sourceSets["data"])
        }
    }

    runs {
        configureEach {
            logLevel.convention(org.slf4j.event.Level.DEBUG)
            sourceSet.convention(sourceSets["main"])
            mods.convention(listOf(neoForge.mods["main"]))
        }

        create("client") {
            client()
            gameDirectory.set(file("run/client"))
        }

        create("clientAuth") {
            client()
            gameDirectory.set(file("run/client"))
            mainClass.set("net.covers1624.devlogin.DevLogin")
            programArguments.addAll("--launch_target", "cpw.mods.bootstraplauncher.BootstrapLauncher")
        }

        create("server") {
            server()
            gameDirectory.set(file("run/server"))
        }

        create("data") {
            data()
            gameDirectory.set(file("run/client"))
            sourceSet.set(sourceSets["data"])
            mods.set(listOf(neoForge.mods["data"]))

            programArguments.addAll(
                "--mod", MOD_ID,
                "--client", "--server", "--validate",
                "--output", file("src/data/generated").absolutePath,
                "--existing", file("src/main/resources").absolutePath
            )
        }

        configureEach {
            if(!type.get().equals("data", true)) {
                jvmArguments.addAll(
                    "-XX:+AllowEnhancedClassRedefinition",
                    "-XX:+IgnoreUnrecognizedVMOptions",
                    "-XX:+AllowRedefinitionToAddDeleteMethods"
                )
            }
        }
    }
}

dependencies {
    if(!IS_CI) {
        runtimeOnly(libraries.devlogin)
    }
}