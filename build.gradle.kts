plugins {
    kotlin("jvm") version "2.0.20" apply false
    id("com.gradleup.shadow") version "8.3.0" apply false
}

group = "com.magmigo.craftermc"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.gradleup.shadow")

    repositories {
        mavenCentral()
    }

    group = rootProject.group
    version = rootProject.version
}
