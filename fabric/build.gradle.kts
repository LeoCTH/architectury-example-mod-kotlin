plugins {
    id("architectury.module")
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

configurations {
    create("shadowCommon") // Don't use shadow from the shadow plugin because we don't want IDEA to index this.
}

architectury {
    platformSetupLoomIde()
    fabric()
}

val fabricLoaderVersion: String by rootProject
val fabricApiVersion: String by rootProject
val architecturyVersion: String by rootProject
val archivesBaseName: String by rootProject

repositories {
    maven("https://maven.fabricmc.net/")
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
    modApi("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")
    modApi("me.shedaniel:architectury-fabric:$architecturyVersion")

    implementation(project(path = ":common")) {
        isTransitive = false
    }
    configurations["developmentFabric"](project(path = ":common")) {
        isTransitive = false
    }
    configurations["shadowCommon"](project(path = ":common", configuration = "transformProductionFabric")) {
        isTransitive = false
    }
}

tasks {
    jar {
        archiveClassifier.set("dev")
    }

    named<Jar>("sourcesJar") {
        val commonSources = project(":common").tasks.getByName<Jar>("sourcesJar")
        dependsOn(commonSources)
        from(commonSources.archiveFile.map { zipTree(it) })
    }

    named<net.fabricmc.loom.task.RemapJarTask>("remapJar") {
        input.set(shadowJar.get().archiveFile)
        dependsOn("shadowJar")
        archiveClassifier.set("fabric")
    }

    shadowJar {
        configurations = listOf(project.configurations.getByName("shadowCommon"))
        archiveClassifier.set("dev-shadow")
    }

    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            // mutable map because this API is dumb and tries to write into this map.
            expand(mutableMapOf("version" to project.version))
        }
    }
}

@Suppress("UnstableApiUsage")
java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenFabric") {
            artifactId = "$archivesBaseName-${project.name}"

            artifact("remapJar") {
                classifier = null
            }
            artifact("sourcesJar") {
                builtBy("remapSourcesJar")
            }
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
    }
}