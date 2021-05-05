plugins {
    id("architectury.module")
}

val archivesBaseName: String by rootProject
val fabricLoaderVersion: String by rootProject
val architecturyVersion: String by rootProject

dependencies {
    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
    modApi("me.shedaniel:architectury:$architecturyVersion")
}

architectury {
    common()
}

@Suppress("UnstableApiUsage")
java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenCommon") {
            artifactId = archivesBaseName

            artifact("remapJar")
            artifact("sourcesJar") {
                builtBy("remapSourcesJar")
            }
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
    }
}
