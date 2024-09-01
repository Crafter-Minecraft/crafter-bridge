import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group = project.group
version = project.version

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":common"))
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("${rootProject.name}-${project.name}")
    archiveClassifier.set("")

    mergeServiceFiles()
    minimize()
}

kotlin.jvmToolchain(21)