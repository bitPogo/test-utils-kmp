/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

actual object PlatformRunner {
    actual fun androidOnly(): String = throw RuntimeException()

    actual fun jvmOnly(): String = throw RuntimeException()

    actual fun jsOnly(): String = "test"

    actual fun jsAndJvm(): String = "test"

    actual fun androidAndJvm(): String = throw RuntimeException()

    actual fun androidAndJs(): String = "test"
}
