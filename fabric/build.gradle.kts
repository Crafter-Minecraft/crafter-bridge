import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.fabricmc.loom.task.RemapJarTask

plugins {
    id("fabric-loom") version "1.7-SNAPSHOT"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

version = property("mod_version")!!
group = property("maven_group")!!

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")

    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")
    modCompileOnly("net.fabricmc:fabric-language-kotlin:${property("fabric_kotlin_version")}")

    listOf(project(":common"), "com.charleskorn.kaml:kaml:0.61.0").forEach {
        implementation(it)
        shadow(it)
    }
}


val shadowJar by tasks.getting(ShadowJar::class) {
    archiveBaseName.set("${rootProject.name}-${project.name}")
    archiveClassifier.set("")
    configurations = listOf(project.configurations.shadow.get())

    minimize()
}

tasks.getByName<RemapJarTask>("remapJar") {
    input.set(shadowJar.archiveFile.get())
    archiveBaseName.set("crafter-bridge-fabric")

    dependsOn(tasks.getByName("shadowJar"))
}

kotlin.jvmToolchain(21)