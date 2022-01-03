/*
 * Copyright (c) 2021 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by LGPL v2.1
 */

package tech.antibytes.util.test

sealed class MockError(
    message: String
) : RuntimeException(message) {
    class MissingStub(message: String) : MockError(message)
}
