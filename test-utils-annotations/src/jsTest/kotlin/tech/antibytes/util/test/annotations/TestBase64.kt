/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

import Buffer
import kotlinx.browser.window

actual object TestBase64 {
    actual fun encode(data: ByteArray): String {
        return if (jsTypeOf(window) != "undefined") {
            window.btoa(data.decodeToString())
        } else {
            Buffer(data.decodeToString()).toString(encoding = "base64")
        }
    }
}
