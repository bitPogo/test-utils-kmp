/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture.generator.primitive

import tech.antibytes.util.test.fixture.PublicApi
import tech.antibytes.util.test.fixture.mock.RandomStub
import kotlin.js.JsName
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StringGeneratorSpec {
    private val random = RandomStub()

    @AfterTest
    fun tearDown() {
        random.clear()
    }

    @Test
    @JsName("fn0")
    fun `It fulfils Generator`() {
        val generator: Any = StringGenerator(random)

        assertTrue(generator is PublicApi.Generator<*>)
    }

    @Test
    @JsName("fn1")
    fun `Given generate is called it returns a String`() {
        // Given
        val capturedRanges = mutableListOf<Pair<Int, Int>>()
        val randomValues = mutableListOf(
            3,
            'a'.code,
            'b'.code,
            'c'.code
        )

        random.nextIntRanged = { from, until ->
            capturedRanges.add(Pair(from, until))
            randomValues.removeAt(0)
        }

        val generator = StringGenerator(random)

        // When
        val result: Any = generator.generate()

        // Then
        assertTrue(result is String)
        assertEquals(
            expected = "abc",
            actual = result
        )

        assertEquals(
            actual = capturedRanges[0],
            expected = Pair(1, 10)
        )
        assertEquals(
            actual = capturedRanges[1],
            expected = Pair(33, 126)
        )
        assertEquals(
            actual = capturedRanges[2],
            expected = Pair(33, 126)
        )
        assertEquals(
            actual = capturedRanges[3],
            expected = Pair(33, 126)
        )
    }
}
