plugins {
    `kotlin-dsl`
}

repositories {
    maven("https://maven.fabricmc.net/")
    maven("https://maven.architectury.dev/")
    maven("https://maven.minecraftforge.net/")
    gradlePluginPortal()
}

dependencies {
    implementation("architectury-plugin:architectury-plugin.gradle.plugin:3.1-SNAPSHOT")
    implementation("forgified-fabric-loom:forgified-fabric-loom.gradle.plugin:0.6-SNAPSHOT")
}