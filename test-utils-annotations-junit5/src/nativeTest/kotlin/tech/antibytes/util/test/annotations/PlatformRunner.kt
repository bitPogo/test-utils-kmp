/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

actual object PlatformRunner {
    actual fun androidOnly(): String = throw RuntimeException()

    actual fun jvmOnly(): String = throw RuntimeException()

    actual fun jsOnly(): String = throw RuntimeException()

    actual fun nativeOnly(): String = "test"

    actual fun jsAndJvmAnNative(): String = "test"

    actual fun androidAndJvmAndNative(): String = "test"

    actual fun androidAndJsAndNative(): String = "test"

    actual fun androidAndJsAndJvm(): String = throw RuntimeException()
}
