/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

import tech.antibytes.gradle.dependency.Version
import tech.antibytes.gradle.util.test.config.TestUtilsPublishingConfiguration
import tech.antibytes.gradle.util.test.dependency.addCustomRepositories

plugins {
    id("tech.antibytes.gradle.util.test.dependency")

    id("tech.antibytes.gradle.dependency")

    id("tech.antibytes.gradle.util.test.script.quality-spotless")

    id("org.owasp.dependencycheck")

    id("tech.antibytes.gradle.publishing")
}

antiBytesPublishing {
    versioning = TestUtilsPublishingConfiguration.versioning
    repositoryConfiguration = TestUtilsPublishingConfiguration.repositories
}

allprojects {
    repositories {
        addCustomRepositories()
        mavenCentral()
        google()
    }

    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin" && requested.name == "kotlin-stdlib-jdk8" && requested.version == "1.5.30") {
                useVersion(Version.kotlin.stdlib)
                because("Avoid resolution conflicts")
            }

            if (requested.group == "org.jetbrains.kotlinx" && requested.name == "kotlinx-coroutines-core" && requested.version == "1.5.0-native-mt") {
                useVersion(Version.kotlin.coroutines)
                because("Avoid resolution conflicts")
            }

            if (requested.group == "org.jetbrains.kotlinx" && requested.name == "kotlinx-coroutines-core-jvm" && requested.version == "1.5.0-native-mt") {
                useVersion(Version.kotlin.coroutines)
                because("Avoid resolution conflicts")
            }

            if (requested.group == "org.jetbrains.kotlinx" && requested.name == "kotlinx-coroutines-core-android" && requested.version == "1.5.0-native-mt") {
                useVersion(Version.kotlin.coroutines)
                because("Avoid resolution conflicts")
            }
        }
    }
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "7.3.3"
    distributionType = Wrapper.DistributionType.ALL
}
