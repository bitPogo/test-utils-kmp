/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

import tech.antibytes.gradle.util.test.config.publishing.AnnotationsConfiguration
import tech.antibytes.gradle.configuration.isIdea
import tech.antibytes.gradle.configuration.apple.ensureAppleDeviceCompatibility

plugins {
    alias(antibytesCatalog.plugins.gradle.antibytes.kmpConfiguration)
    alias(antibytesCatalog.plugins.gradle.antibytes.androidLibraryConfiguration)
    alias(antibytesCatalog.plugins.gradle.antibytes.publishing)
    alias(antibytesCatalog.plugins.gradle.antibytes.coverage)
}

group = AnnotationsConfiguration.group

antiBytesPublishing {
    versioning.set(AnnotationsConfiguration.publishing.versioning)
    packaging.set(AnnotationsConfiguration.publishing.packageConfiguration)
    repositories.set(AnnotationsConfiguration.publishing.repositories)
}

android {
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

    ios()
    iosSimulatorArm64()
    ensureAppleDeviceCompatibility()

    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(antibytesCatalog.common.kotlin.stdlib)
                api(antibytesCatalog.common.test.kotlin.annotations)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kfixture)
                implementation(antibytesCatalog.common.test.kotlin.core)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(antibytesCatalog.jvm.kotlin.stdlib.jdk8)
                implementation(antibytesCatalog.jvm.test.kotlin.core)
                implementation(antibytesCatalog.jvm.test.kotlin.junit5)
            }
        }

        if (!isIdea()) {
            val androidAndroidTestRelease by getting
            val androidAndroidTest by getting {
                dependsOn(androidAndroidTestRelease)
            }
            val androidTestFixturesDebug by getting
            val androidTestFixturesRelease by getting
            val androidTestFixtures by getting {
                dependsOn(androidTestFixturesDebug)
                dependsOn(androidTestFixturesRelease)
            }

            val androidTest by getting {
                dependsOn(androidTestFixtures)
            }
        }

        val androidTest by getting

        val jsMain by getting {
            dependencies {
                implementation(antibytesCatalog.js.kotlinx.nodeJs)
                implementation(antibytesCatalog.js.test.kotlin.core)
            }
        }
        val jsTest by getting

        val jvmMain by getting {
            dependencies {
                implementation(antibytesCatalog.jvm.test.kotlin.junit5)
                implementation(antibytesCatalog.jvm.test.kotlin.core)
            }
        }
        val jvmTest by getting

        val nativeMain by creating {
            dependsOn(commonMain)
        }

        val nativeTest by creating {
            dependsOn(commonTest)
        }

        val darwinMain by creating {
            dependsOn(nativeMain)
        }

        val darwinTest by creating {
            dependsOn(nativeTest)
        }

        val otherMain by creating {
            dependsOn(nativeMain)
        }

        val otherTest by creating {
            dependsOn(nativeTest)
        }

        val linuxX64Main by getting {
            dependsOn(otherMain)
        }

        val linuxX64Test by getting {
            dependsOn(otherTest)
        }

        val iosMain by getting {
            dependsOn(darwinMain)
        }

        val iosTest by getting {
            dependsOn(darwinTest)
        }

        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(iosTest)
        }
    }
}

android {
    namespace = "tech.antibytes.util.test.annotations"
}

tasks.withType(Test::class.java).configureEach {
    useJUnitPlatform()
}
