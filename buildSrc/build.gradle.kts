import tech.antibytes.gradle.util.test.dependency.Dependency
import tech.antibytes.gradle.util.test.dependency.addCustomRepositories

plugins {
    `kotlin-dsl`

    id("tech.antibytes.gradle.util.test.dependency")
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
    addCustomRepositories()
}

dependencies {
    implementation(Dependency.gradle.dependency)
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
    implementation(Dependency.gradle.publishing)
    implementation(Dependency.gradle.coverage)
    implementation(Dependency.gradle.spotless)
    implementation(Dependency.gradle.projectConfig)
}
