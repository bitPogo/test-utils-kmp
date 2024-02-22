/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.gradle.util.test.config.publishing

import org.gradle.api.Project
import tech.antibytes.gradle.publishing.api.PackageConfiguration
import tech.antibytes.gradle.publishing.api.PomConfiguration

class AnnotationsConfigurationJunit5(project: Project) : ConfigBase() {
    val publishing = Publishing(project)

    class Publishing(project: Project) : TestUtilsPublishingConfiguration(project) {
        val packageConfiguration = PackageConfiguration(
            pom = PomConfiguration(
                name = "test-utils-annotations-junit5",
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
