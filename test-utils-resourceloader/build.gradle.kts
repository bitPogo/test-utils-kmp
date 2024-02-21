/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

import tech.antibytes.gradle.util.test.config.publishing.ResourceLoaderConfiguration
import tech.antibytes.gradle.configuration.runtime.AntiBytesTestConfigurationTask
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import tech.antibytes.gradle.configuration.apple.ensureAppleDeviceCompatibility
import tech.antibytes.gradle.configuration.sourcesets.native

plugins {
    alias(antibytesCatalog.plugins.gradle.antibytes.kmpConfiguration)
    alias(antibytesCatalog.plugins.gradle.antibytes.androidLibraryConfiguration)
    alias(antibytesCatalog.plugins.gradle.antibytes.publishing)
    alias(antibytesCatalog.plugins.gradle.antibytes.coverage)
}

val publishing = ResourceLoaderConfiguration(project)
group = publishing.group

antibytesPublishing {
    versioning.set(publishing.publishing.versioning)
    packaging.set(publishing.publishing.packageConfiguration)
    repositories.set(publishing.publishing.repositories)
}

android {
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
}

kotlin {
    androidTarget()

    js(IR) {
        nodejs()
        browser()
    }

    jvm()

    native()
    ensureAppleDeviceCompatibility()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(antibytesCatalog.common.kotlin.stdlib)
            }
        }
        val commonTest by getting {
            kotlin.srcDir("${buildDir.absolutePath.trimEnd('/')}/generated/antibytes/commonTest/kotlin")

            dependencies {
                implementation(antibytesCatalog.common.test.kotlin.annotations)
                implementation(libs.kfixture)
                implementation(antibytesCatalog.common.test.kotlin.core)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(antibytesCatalog.jvm.kotlin.stdlib.jdk8)
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(antibytesCatalog.jvm.test.kotlin.junit4)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(antibytesCatalog.js.kotlinx.nodeJs)
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(antibytesCatalog.js.test.kotlin.core)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(antibytesCatalog.jvm.test.kotlin.junit4)
            }
        }
    }
}

android {
    namespace = "tech.antibytes.util.test.testloader"
}

val generateTestConfig by tasks.creating(AntiBytesTestConfigurationTask::class.java) {
    mustRunAfter("clean")
    packageName.set("tech.antibytes.util.test.resourceloader.config")
    stringFields.set(
        mapOf(
            "projectDir" to project.projectDir.absolutePath
        )
    )
}

tasks.withType(KotlinCompile::class.java) {
    if (this.name.contains("Test")) {
        this.dependsOn(generateTestConfig)
    }
}

tasks.withType(KotlinNativeCompile::class.java) {
    if (this.name.contains("Test")) {
        this.dependsOn(generateTestConfig)
    }
}

tasks.withType(Kotlin2JsCompile::class.java) {
    if (this.name.contains("Test")) {
        this.dependsOn(generateTestConfig)
    }
}
