/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

import tech.antibytes.gradle.dependency.helper.addCustomRepositories
import tech.antibytes.gradle.dependency.helper.ensureKotlinVersion
import tech.antibytes.gradle.util.test.config.publishing.TestUtilsPublishingConfiguration
import tech.antibytes.gradle.util.test.config.repositories.Repositories.testRepositories

plugins {
    id("tech.antibytes.gradle.setup")

    id("tech.antibytes.gradle.util.test.dependency")
    id("tech.antibytes.gradle.dependency")

    alias(antibytesCatalog.plugins.gradle.antibytes.publishing)
    alias(antibytesCatalog.plugins.gradle.antibytes.dependencyHelper)
    alias(antibytesCatalog.plugins.gradle.antibytes.quality)
}

antiBytesPublishing {
    versioning.set(TestUtilsPublishingConfiguration.versioning)
    repositories.set(TestUtilsPublishingConfiguration.repositories)
}

allprojects {
    repositories {
        addCustomRepositories(testRepositories)
        mavenCentral()
        google()
        jcenter()
    }

    ensureKotlinVersion(excludes = listOf("atomicfu"))
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "7.5.1"
    distributionType = Wrapper.DistributionType.ALL
}
