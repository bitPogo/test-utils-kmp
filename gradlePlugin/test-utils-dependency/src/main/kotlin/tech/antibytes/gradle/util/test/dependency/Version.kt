/*
 * Copyright (c) 2021 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by LGPL v2.1
 */

package tech.antibytes.gradle.util.test.dependency

object Version {

    val gradle = Gradle

    object Gradle {
        /**
         * [AnitBytes GradlePlugins](https://github.com/bitPogo/gradle-plugins)
         */
        const val antibytes = "f1c2060"

        /**
         * [Spotless](https://plugins.gradle.org/plugin/com.diffplug.gradle.spotless)
         */
        const val spotless = "6.0.4"
    }
}
