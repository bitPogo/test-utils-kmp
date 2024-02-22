/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.annotations

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

expect object TestBase64 {
    fun encode(data: ByteArray): String
}

@RobolectricConfig(manifest = "--none")
@RunWithRobolectricTestRunner(RobolectricTestRunner::class)
class RobolectricTestRunnerTest {
    @Test
    @JsName("Given_a_things_dependend_on_Robolectric_it_works_in_common_code")
    fun `Given a things dependend on Robolectric it works in common code`() {
        // Given
        val data = "The quick brown fox jumps over the lazy dog"

        // When
        val result = TestBase64.encode(data.encodeToByteArray())

        // Then
        assertEquals(
            expected = "VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZw==",
            actual = result,
        )
    }
}
