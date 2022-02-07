/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

import io.mockk.InternalPlatformDsl.toStr
import tech.antibytes.gradle.dependency.Dependency
import tech.antibytes.gradle.util.test.config.FixtureTestUtilsConfiguration

plugins {
    id("org.jetbrains.kotlin.multiplatform")

    // Android
    id("com.android.library")

    id("tech.antibytes.gradle.configuration")
    id("tech.antibytes.gradle.publishing")
    id("tech.antibytes.gradle.coverage")
}

group = FixtureTestUtilsConfiguration.group

antiBytesPublishing {
    packageConfiguration = FixtureTestUtilsConfiguration.publishing.packageConfiguration
    repositoryConfiguration = FixtureTestUtilsConfiguration.publishing.repositories
    versioning = FixtureTestUtilsConfiguration.publishing.versioning
}

kotlin {
    android()

    js(IR) {
        nodejs()
        browser()
    }

    jvm()

    linuxX64()

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.ExperimentalUnsignedTypes")
                optIn("kotlin.RequiresOptIn")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.common)
                implementation(Dependency.multiplatform.uuid)
                implementation(Dependency.multiplatform.dateTime)
            }
        }
        val commonTest by getting {
            kotlin.srcDir("${projectDir.absolutePath.trimEnd('/')}/src-gen/commonTest/kotlin")

            dependencies {
                implementation(Dependency.multiplatform.test.common)
                implementation(Dependency.multiplatform.test.annotations)
                implementation(Dependency.multiplatform.stately.concurrency)
                implementation(Dependency.multiplatform.stately.freeze)
            }
        }

        val androidMain by getting {
            dependencies {
                dependsOn(commonMain)
                implementation(Dependency.multiplatform.kotlin.android)
            }
        }
        val androidTest by getting {
            dependencies {
                dependsOn(commonTest)
                implementation(Dependency.multiplatform.test.jvm)
                implementation(Dependency.multiplatform.test.junit)
            }
        }

        val jsMain by getting {
            dependencies {
                dependsOn(commonMain)
                implementation(Dependency.multiplatform.kotlin.js)
                implementation(Dependency.js.nodejs)
            }
        }
        val jsTest by getting {
            dependencies {
                dependsOn(commonTest)
                implementation(Dependency.multiplatform.test.js)
            }
        }

        val jvmMain by getting {
            dependencies {
                dependsOn(commonMain)
                implementation(Dependency.multiplatform.kotlin.jdk8)
            }
        }
        val jvmTest by getting {
            dependencies {
                dependsOn(commonTest)
                implementation(Dependency.multiplatform.test.jvm)
                implementation(Dependency.multiplatform.test.junit)
            }
        }

        val nativeMain by creating {
            dependencies {
                dependsOn(commonMain)
            }
        }

        val nativeTest by creating {
            dependencies {
                dependsOn(commonTest)
            }
        }

        val otherMain by creating {
            dependencies {
                dependsOn(nativeMain)
            }
        }

        val otherTest by creating {
            dependencies {
                dependsOn(nativeTest)
            }
        }

        val linuxX64Main by getting {
            dependencies {
                dependsOn(otherMain)
            }
        }

        val linuxX64Test by getting {
            dependencies {
                dependsOn(otherTest)
            }
        }
    }
}
