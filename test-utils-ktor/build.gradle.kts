/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

import tech.antibytes.gradle.util.test.config.publishing.KtorTestUtilsConfiguration
import tech.antibytes.gradle.configuration.apple.ensureAppleDeviceCompatibility
import tech.antibytes.gradle.configuration.sourcesets.nativeCoroutine
import tech.antibytes.gradle.dependency.helper.implementation

plugins {
    alias(antibytesCatalog.plugins.gradle.antibytes.kmpConfiguration)
    alias(antibytesCatalog.plugins.gradle.antibytes.androidLibraryConfiguration)
    alias(antibytesCatalog.plugins.gradle.antibytes.publishing)
    alias(antibytesCatalog.plugins.gradle.antibytes.coverage)
}

val publishing = KtorTestUtilsConfiguration(project)
group = publishing.group

antibytesPublishing {
    versioning.set(publishing.publishing.versioning)
    packaging.set(publishing.publishing.packageConfiguration)
    repositories.set(publishing.publishing.repositories)
}

android {
    namespace = "tech.antibytes.util.test.ktor"

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

    nativeCoroutine()
    ensureAppleDeviceCompatibility()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(antibytesCatalog.common.kotlin.stdlib)

                implementation(antibytesCatalog.common.ktor.client.core) {
                    exclude(
                        group = "org.jetbrains.kotlinx",
                        module = "kotlinx-coroutines-core"
                    )
                }
                implementation(antibytesCatalog.common.test.ktor.client.mockClient)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(antibytesCatalog.common.test.kotlin.core)
                implementation(antibytesCatalog.common.test.kotlin.annotations)
                implementation(libs.kfixture)

                implementation(projects.testUtils)
                implementation(projects.testUtilsAnnotationsJunit4)
                implementation(projects.testUtilsCoroutine)
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
                implementation(antibytesCatalog.js.test.kotlin.core)
                implementation(antibytesCatalog.js.kotlinx.nodeJs)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(antibytesCatalog.jvm.test.kotlin.junit4)
            }
        }
    }
}
