/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

import io.mockk.core.ValueClassSupport.boxedValue
import tech.antibytes.gradle.util.test.config.publishing.TestUtilsConfiguration
import tech.antibytes.gradle.configuration.isIdea
import tech.antibytes.gradle.configuration.apple.ensureAppleDeviceCompatibility

plugins {
    id(antibytesCatalog.plugins.kotlin.multiplatform.get().pluginId)

    // Android
    id(antibytesCatalog.plugins.android.library.get().pluginId)

    alias(antibytesCatalog.plugins.gradle.antibytes.projectConfiguration)
    alias(antibytesCatalog.plugins.gradle.antibytes.publishing)
    alias(antibytesCatalog.plugins.gradle.antibytes.coverage)
}

group = TestUtilsConfiguration.group

antiBytesPublishing {
    versioning.set(TestUtilsConfiguration.publishing.versioning)
    packaging.set(TestUtilsConfiguration.publishing.packageConfiguration)
    repositories.set(TestUtilsConfiguration.publishing.repositories)
}

android {
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

    ios()
    iosSimulatorArm64()
    ensureAppleDeviceCompatibility()

    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(antibytesCatalog.common.kotlin.stdlib)
                implementation(antibytesCatalog.common.test.kotlin.core)
            }
        }
        val commonTest by getting {
            kotlin.srcDir("${buildDir.absolutePath.trimEnd('/')}/generated/antibytes/commonTest/kotlin")

            dependencies {
                implementation(antibytesCatalog.common.test.kotlin.annotations)
                implementation(libs.kfixture)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(antibytesCatalog.jvm.kotlin.stdlib.jdk8)
                implementation(antibytesCatalog.jvm.test.kotlin.core)
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

        val androidTest by getting {
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
        val jsTest by getting

        val jvmMain by getting {
            dependencies {
                implementation(antibytesCatalog.jvm.test.kotlin.core)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(antibytesCatalog.jvm.test.kotlin.junit4)
            }
        }

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
    namespace = "tech.antibytes.util.test"
}
