/*
 * Copyright (c) 2021 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by LGPL v2.1
 */

package tech.antibytes.util.test.annotations

actual object TestBase64 {
    actual fun encode(data: ByteArray): String {
        return java.util.Base64.getEncoder().encodeToString(data)
    }
}
