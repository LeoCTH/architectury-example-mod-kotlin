plugins {
    id("architectury.all")
    id("forgified-fabric-loom")
}

val minecraftVersion: String by rootProject
val yarnVersion: String by rootProject

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$yarnVersion")
}
