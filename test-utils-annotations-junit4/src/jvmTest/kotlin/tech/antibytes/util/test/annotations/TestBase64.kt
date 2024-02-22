/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

actual object TestBase64 {
    actual fun encode(data: ByteArray): String {
        return java.util.Base64.getEncoder().encodeToString(data)
    }
}
