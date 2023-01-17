/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

import tech.antibytes.gradle.util.test.config.publishing.CoroutineTestUtilsConfiguration
import tech.antibytes.gradle.configuration.apple.ensureAppleDeviceCompatibility
import tech.antibytes.gradle.configuration.sourcesets.appleWithLegacy
import tech.antibytes.gradle.configuration.sourcesets.linux
import tech.antibytes.gradle.configuration.sourcesets.mingw
import tech.antibytes.gradle.configuration.sourcesets.setupAndroidTest

plugins {
    alias(antibytesCatalog.plugins.gradle.antibytes.kmpConfiguration)
    alias(antibytesCatalog.plugins.gradle.antibytes.androidLibraryConfiguration)
    alias(antibytesCatalog.plugins.gradle.antibytes.publishing)
    alias(antibytesCatalog.plugins.gradle.antibytes.coverage)
}

val publishing = CoroutineTestUtilsConfiguration(project)
group = publishing.group

antibytesPublishing {
    versioning.set(publishing.publishing.versioning)
    packaging.set(publishing.publishing.packageConfiguration)
    repositories.set(publishing.publishing.repositories)
}


android {
    namespace = "tech.antibytes.util.test.coroutine"

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
}

kotlin {
    android()

    js(IR) {
        nodejs()
        browser()
    }

    jvm()

    appleWithLegacy()
    ensureAppleDeviceCompatibility()

    linuxX64()
    mingwX64()

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.RequiresOptIn")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(antibytesCatalog.common.kotlin.stdlib)
                implementation(antibytesCatalog.common.kotlinx.coroutines.core)
                api(antibytesCatalog.common.test.kotlinx.coroutines)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(antibytesCatalog.common.test.kotlin.core)
                implementation(antibytesCatalog.common.stately.concurrency)
                implementation(libs.kfixture)

                implementation(project(":test-utils"))
                implementation(project(":test-utils-annotations-junit4"))
                implementation(project(":test-utils-resourceloader"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(antibytesCatalog.jvm.kotlin.stdlib.jdk8)
                implementation(antibytesCatalog.android.kotlinx.coroutines)
            }
        }

        setupAndroidTest()

        val androidTest by getting {
            dependencies {
                implementation(antibytesCatalog.jvm.test.kotlin.core)
                implementation(antibytesCatalog.jvm.test.kotlin.junit4)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(antibytesCatalog.js.kotlinx.nodeJs)
                implementation(antibytesCatalog.js.test.kotlin.core)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(antibytesCatalog.jvm.test.kotlin.core)
                implementation(antibytesCatalog.jvm.test.kotlin.junit4)
            }
        }

        val nativeMain by creating {
            dependsOn(commonMain)
        }

        val nativeTest by creating {
            dependsOn(commonTest)
        }

        val appleMain by getting {
            dependsOn(nativeMain)
        }
        val appleTest by getting {
            dependsOn(nativeTest)
        }

        val linuxX64Main by getting {
            dependsOn(nativeMain)
        }
        val linuxX64Test by getting {
            dependsOn(nativeTest)
        }

        val mingwX64Main by getting {
            dependsOn(nativeMain)
        }
        val mingwX64Test by getting {
            dependsOn(nativeTest)
        }
    }
}
