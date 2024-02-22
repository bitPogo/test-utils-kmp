/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

import tech.antibytes.gradle.dependency.helper.addCustomRepositories
import tech.antibytes.gradle.dependency.helper.ensureKotlinVersion
import tech.antibytes.gradle.util.test.config.publishing.TestUtilsPublishingConfiguration
import tech.antibytes.gradle.util.test.config.repositories.Repositories.testRepositories

plugins {
    id("tech.antibytes.gradle.setup")

    alias(antibytesCatalog.plugins.gradle.antibytes.dependencyHelper)
    alias(antibytesCatalog.plugins.gradle.antibytes.publishing)
    alias(antibytesCatalog.plugins.gradle.antibytes.quality)
}

val publishing = TestUtilsPublishingConfiguration(project)

antibytesPublishing {
    versioning.set(publishing.versioning)
    repositories.set(publishing.repositories)
}

allprojects {
    repositories {
        mavenCentral()
        google()
        jcenter()

        addCustomRepositories(testRepositories)
    }

    configurations.all {
        resolutionStrategy.eachDependency {
            if (group == "org.jetbrains.kotlinx" && name == "kotlinx-coroutines-core") {
                useVersion(antibytesCatalog.versions.kotlinx.coroutines.core.toString())
                because("Avoid resolution conflicts")
            }
        }
    }

    ensureKotlinVersion()
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = antibytesCatalog.versions.gradle.gradle.get()
    distributionType = Wrapper.DistributionType.ALL
}
