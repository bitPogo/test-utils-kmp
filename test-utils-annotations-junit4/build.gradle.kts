/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

import tech.antibytes.gradle.util.test.config.publishing.AnnotationsConfigurationJunit4
import tech.antibytes.gradle.configuration.apple.ensureAppleDeviceCompatibility
import tech.antibytes.gradle.configuration.sourcesets.nativeWithLegacy
import tech.antibytes.gradle.configuration.sourcesets.setupAndroidTest

plugins {
    alias(antibytesCatalog.plugins.gradle.antibytes.kmpConfiguration)
    alias(antibytesCatalog.plugins.gradle.antibytes.androidLibraryConfiguration)
    alias(antibytesCatalog.plugins.gradle.antibytes.publishing)
    alias(antibytesCatalog.plugins.gradle.antibytes.coverage)
}

val publishing = AnnotationsConfigurationJunit4(project)
group = publishing.group

antibytesPublishing {
    versioning.set(publishing.publishing.versioning)
    packaging.set(publishing.publishing.packageConfiguration)
    repositories.set(publishing.publishing.repositories)
}

android {
    namespace = "tech.antibytes.util.test.annotations"

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
}

kotlin {
    android()

    jvm()

    js(IR) {
        nodejs()
        browser()
    }

    nativeWithLegacy()
    ensureAppleDeviceCompatibility()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(antibytesCatalog.common.kotlin.stdlib)

                implementation(antibytesCatalog.common.test.kotlin.core)
                api(antibytesCatalog.common.test.kotlin.annotations)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kfixture)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(antibytesCatalog.jvm.kotlin.stdlib.jdk8)
                implementation(antibytesCatalog.jvm.test.kotlin.core)
                implementation(antibytesCatalog.jvm.test.kotlin.junit4)

                implementation(antibytesCatalog.android.test.robolectric)
            }
        }

        setupAndroidTest()

        val jsMain by getting {
            dependencies {
                implementation(antibytesCatalog.js.test.kotlin.core)
                implementation(antibytesCatalog.js.kotlinx.nodeJs)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(antibytesCatalog.jvm.test.kotlin.core)
                implementation(antibytesCatalog.jvm.test.kotlin.junit4)
            }
        }
    }
}
