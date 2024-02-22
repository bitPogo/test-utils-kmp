/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.gradle.util.test.config.publishing

import org.gradle.api.Project
import tech.antibytes.gradle.configuration.BasePublishingConfiguration

open class TestUtilsPublishingConfiguration(
    project: Project,
) : BasePublishingConfiguration(project, "test-utils-kmp")
