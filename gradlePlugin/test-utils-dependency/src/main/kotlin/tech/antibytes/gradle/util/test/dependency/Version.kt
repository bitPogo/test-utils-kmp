/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.gradle.util.test.dependency

object Version {

    val gradle = Gradle
    const val kotlin = "1.7.10"

    object Gradle {
        /**
         * [AnitBytes GradlePlugins](https://github.com/bitPogo/gradle-plugins)
         */
        const val antibytes = "009e2b6"

        /**
         * [Spotless](https://plugins.gradle.org/plugin/com.diffplug.gradle.spotless)
         */
        const val spotless = "6.9.0"
    }

    val antibytes = AntiBytes

    object AntiBytes {
        /**
         * [KFixture](https://github.com/bitPogo/kfixture)
         */
        const val kfixture = "0.3.0-SNAPSHOT"
    }
}
