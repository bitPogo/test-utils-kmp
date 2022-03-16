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
                dependsOn(commonMain)
                implementation(Dependency.multiplatform.kotlin.android)

                implementation(Dependency.multiplatform.test.jvm)
                implementation(Dependency.multiplatform.test.junit)
            }
        }
        val androidTest by getting {
            dependencies {
                dependsOn(commonTest)
            }
        }

        val jsMain by getting {
            dependencies {
                dependsOn(commonMain)
                implementation(Dependency.multiplatform.kotlin.js)
                implementation(Dependency.js.nodejs)
                implementation(Dependency.multiplatform.test.js)
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

                implementation(Dependency.multiplatform.test.jvm)
                implementation(Dependency.multiplatform.test.junit)
            }
        }
        val jvmTest by getting {
            dependencies {
                dependsOn(commonTest)
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
