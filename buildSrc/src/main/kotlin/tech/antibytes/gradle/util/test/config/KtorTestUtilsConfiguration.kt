/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.gradle.util.test.config

import tech.antibytes.gradle.publishing.api.PackageConfiguration
import tech.antibytes.gradle.publishing.api.PomConfiguration

object KtorTestUtilsConfiguration {
    const val group = "tech.antibytes.test-utils-kmp"

    val publishing = Publishing

    object Publishing : TestUtilsPublishingConfiguration() {
        val packageConfiguration = PackageConfiguration(
            pom = PomConfiguration(
                name = "ktor-test-utils",
                description = "Convenience tools for Ktor testing on Kotlin Multiplatform.",
                year = 2021,
                url = "https://$gitHubRepositoryPath"
            ),
            developers = listOf(developer),
            license = license,
            scm = sourceControl
        )
    }
}
