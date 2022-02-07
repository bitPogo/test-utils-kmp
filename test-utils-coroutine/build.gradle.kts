/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

import tech.antibytes.gradle.dependency.Dependency
import tech.antibytes.gradle.util.test.config.TestUtilsConfiguration

plugins {
    id("org.jetbrains.kotlin.multiplatform")

    // Android
    id("com.android.library")

    id("tech.antibytes.gradle.configuration")
    id("tech.antibytes.gradle.publishing")
    id("tech.antibytes.gradle.coverage")
}

group = TestUtilsConfiguration.group

antiBytesPublishing {
    packageConfiguration = TestUtilsConfiguration.publishing.packageConfiguration
    repositoryConfiguration = TestUtilsConfiguration.publishing.repositories
    versioning = TestUtilsConfiguration.publishing.versioning
}

kotlin {
    android()

    js(IR) {
        nodejs()
        browser()
    }

    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.common)
                implementation(Dependency.multiplatform.coroutines.common)
            }
        }
        val commonTest by getting {
            kotlin.srcDir("${projectDir.absolutePath.trimEnd('/')}/src-gen/commonTest/kotlin")

            dependencies {
                implementation(Dependency.multiplatform.test.common)
                implementation(Dependency.multiplatform.test.annotations)

                api(project(":test-utils-fixture"))
                api(project(":test-utils"))
                api(project(":test-utils-annotations"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.android)
                implementation(Dependency.multiplatform.coroutines.android)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(Dependency.multiplatform.test.jvm)
                implementation(Dependency.multiplatform.test.junit)
            }
        }

        val jsMain by getting {
            dependencies {
                dependsOn(commonMain)
                implementation(Dependency.multiplatform.kotlin.js)
                implementation(Dependency.js.nodejs)
                implementation(Dependency.multiplatform.coroutines.js)
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
                implementation(Dependency.multiplatform.kotlin.common)
                implementation(Dependency.multiplatform.kotlin.jdk8)
                implementation(Dependency.multiplatform.coroutines.common)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(Dependency.multiplatform.test.jvm)
                implementation(Dependency.multiplatform.test.junit)
            }
        }
    }
}
