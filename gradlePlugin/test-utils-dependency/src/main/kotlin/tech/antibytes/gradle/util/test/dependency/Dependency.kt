/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.gradle.util.test.dependency

object Dependency {
    val gradle = GradlePlugin

    val test = Test

    object Test {
        const val fixture = "tech.antibytes.kfixture:core:${Version.antibytes.kfixture}"
    }
}
