/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

expect object PlatformRunner {
    fun androidOnly(): String
    fun jvmOnly(): String
    fun jsOnly(): String
    fun jsAndJvm(): String
    fun androidAndJvm(): String
    fun androidAndJs(): String
}
