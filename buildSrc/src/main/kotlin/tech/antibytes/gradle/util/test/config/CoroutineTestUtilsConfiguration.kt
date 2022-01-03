/*
 * Copyright (c) 2021 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by LGPL v2.1
 */

package tech.antibytes.gradle.util.test.config

import tech.antibytes.gradle.publishing.api.PackageConfiguration
import tech.antibytes.gradle.publishing.api.PomConfiguration

object CoroutineTestUtilsConfiguration {
    const val group = "tech.antibytes.test-utils-kmp"

    val publishing = Publishing

    object Publishing : TestUtilsPublishingConfiguration() {
        val packageConfiguration = PackageConfiguration(
            pom = PomConfiguration(
                name = "test-utils-coroutine",
                description = "Convenience tools for testing on Kotlin Multiplatform.",
                year = 2021,
                url = "https://$gitHubRepositoryPath"
            ),
            developers = listOf(developer),
            license = license,
            scm = sourceControl
        )
    }
}
