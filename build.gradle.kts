/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

import tech.antibytes.gradle.dependency.ensureKotlinVersion
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
        jcenter()
    }

    ensureKotlinVersion(excludes = listOf("atomicfu"))
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "7.5"
    distributionType = Wrapper.DistributionType.ALL
}
