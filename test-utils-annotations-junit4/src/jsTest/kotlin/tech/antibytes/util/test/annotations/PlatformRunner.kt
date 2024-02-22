/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

actual object PlatformRunner {
    actual fun androidOnly(): String = throw RuntimeException()

    actual fun jvmOnly(): String = throw RuntimeException()

    actual fun jsOnly(): String = "test"

    actual fun nativeOnly(): String = throw RuntimeException()

    actual fun jsAndJvmAnNative(): String = "test"

    actual fun androidAndJvmAndNative(): String = throw RuntimeException()

    actual fun androidAndJsAndNative(): String = "test"

    actual fun androidAndJsAndJvm(): String = "test"
}
