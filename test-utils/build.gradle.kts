/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

import tech.antibytes.gradle.dependency.Dependency
import tech.antibytes.gradle.util.test.config.TestUtilsConfiguration
import tech.antibytes.gradle.configuration.runtime.AntiBytesTestConfigurationTask
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

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

    ios()
    iosSimulatorArm64()

    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.common)

                implementation(Dependency.multiplatform.test.common)
                implementation(Dependency.multiplatform.test.annotations)
            }
        }
        val commonTest by getting {
            kotlin.srcDir("${buildDir.absolutePath.trimEnd('/')}/generated/antibytes/commonTest/kotlin")

            dependencies {
                api(project(":test-utils-fixture"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.android)

                implementation(Dependency.multiplatform.test.jvm)
                implementation(Dependency.multiplatform.test.junit)
            }
        }

        val androidAndroidTestRelease by getting
        val androidTestFixtures by getting
        val androidTestFixturesDebug by getting
        val androidTestFixturesRelease by getting

        val androidTest by getting {
            dependsOn(androidAndroidTestRelease)
            dependsOn(androidTestFixtures)
            dependsOn(androidTestFixturesDebug)
            dependsOn(androidTestFixturesRelease)
        }

        val jsMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.js)
                implementation(Dependency.js.nodejs)
                implementation(Dependency.multiplatform.test.js)
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(Dependency.multiplatform.test.js)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.jdk8)

                implementation(Dependency.multiplatform.test.jvm)
                implementation(Dependency.multiplatform.test.junit)
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

val generateTestConfig by tasks.creating(AntiBytesTestConfigurationTask::class.java) {
    packageName.set("tech.antibytes.util.test.config")
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
