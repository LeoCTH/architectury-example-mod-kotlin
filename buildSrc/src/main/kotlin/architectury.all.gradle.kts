plugins {
    `java-library`
    id("architectury-plugin")
    `maven-publish`
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}