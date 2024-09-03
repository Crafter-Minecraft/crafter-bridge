import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group = project.group
version = project.version

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":common"))
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
}

tasks {
    val shadowJar by getting(ShadowJar::class) {
        // archiveBaseName.set("${rootProject.name}-${project.name}")
        archiveClassifier.set("")

        mergeServiceFiles()
        minimize()
    }

    withType<Jar> {
        archiveBaseName.set("${rootProject.name}-${project.name}")
    }
}

kotlin.jvmToolchain(21)