/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.gradle.util.test.config.publishing

import tech.antibytes.gradle.publishing.api.PackageConfiguration
import tech.antibytes.gradle.publishing.api.PomConfiguration

object AnnotationsConfiguration : ConfigBase() {
    val publishing = Publishing

    object Publishing : TestUtilsPublishingConfiguration() {
        val packageConfiguration = PackageConfiguration(
            pom = PomConfiguration(
                name = "test-utils-annotations",
                description = "Convenience tools for generated fixtures for Kotlin Multiplatform.",
                year = 2022,
                url = "https://$gitHubRepositoryPath",
            ),
            developers = listOf(developer),
            license = license,
            scm = sourceControl,
        )
    }
}