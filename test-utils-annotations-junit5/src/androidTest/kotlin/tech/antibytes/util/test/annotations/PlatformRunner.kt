/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

actual object PlatformRunner {
    actual fun androidOnly(): String = "test"

    actual fun jvmOnly(): String = throw RuntimeException()

    actual fun jsOnly(): String = throw RuntimeException()

    actual fun nativeOnly(): String = throw RuntimeException()

    actual fun jsAndJvmAnNative(): String = throw RuntimeException()

    actual fun androidAndJvmAndNative(): String = "test"

    actual fun androidAndJsAndNative(): String = "test"

    actual fun androidAndJsAndJvm(): String = "test"
}
