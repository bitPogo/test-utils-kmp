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
    fun nativeOnly(): String

    fun jsAndJvmAnNative(): String
    fun androidAndJvmAndNative(): String
    fun androidAndJsAndNative(): String
    fun androidAndJsAndJvm(): String
}
