/*
 * Copyright (c) 2021 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by LGPL v2.1
 */

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
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "7.3.2"
    distributionType = Wrapper.DistributionType.ALL
}
