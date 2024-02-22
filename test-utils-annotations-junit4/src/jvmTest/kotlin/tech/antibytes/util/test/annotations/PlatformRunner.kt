/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

actual object PlatformRunner {
    actual fun androidOnly(): String = throw RuntimeException()

    actual fun jvmOnly(): String = "test"

    actual fun jsOnly(): String = throw RuntimeException()

    actual fun nativeOnly(): String = throw RuntimeException()

    actual fun jsAndJvmAnNative(): String = "test"

    actual fun androidAndJvmAndNative(): String = "test"

    actual fun androidAndJsAndNative(): String = throw RuntimeException()

    actual fun androidAndJsAndJvm(): String = "test"
}
