/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

import tech.antibytes.gradle.dependency.Dependency
import tech.antibytes.gradle.util.test.config.KtorTestUtilsConfiguration

plugins {
    id("org.jetbrains.kotlin.multiplatform")

    // Android
    id("com.android.library")

    id("tech.antibytes.gradle.configuration")
}

kotlin {
    android()

    js(IR) {
        nodejs()
        browser()
    }

    jvm()

    ios()

    linuxX64()

    sourceSets {
        removeAll { sourceSet ->
            setOf(
                "androidAndroidTestRelease",
                "androidTestFixtures",
                "androidTestFixturesDebug",
                "androidTestFixturesRelease",
            ).contains(sourceSet.name)
        }

        val commonMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.common)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Dependency.multiplatform.test.common)
                implementation(Dependency.multiplatform.test.annotations)

                api(project(":test-utils-fixture"))
                api(project(":test-utils"))
                api(project(":test-utils-coroutine"))
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
                implementation(Dependency.multiplatform.ktor.jvm.core)
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

        val darwinMain by creating {
            dependencies {
                dependsOn(nativeMain)
            }
        }
        val darwinTest by creating {
            dependencies {
                dependsOn(nativeTest)
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

        val iosMain by getting {
            dependencies {
                dependsOn(darwinMain)
            }
        }
        val iosTest by getting {
            dependencies {
                dependsOn(darwinTest)
            }
        }
    }
}
