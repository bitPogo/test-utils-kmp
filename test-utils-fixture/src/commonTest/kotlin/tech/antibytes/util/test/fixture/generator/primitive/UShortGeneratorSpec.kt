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

class UShortGeneratorSpec {
    private val random = RandomStub()

    @AfterTest
    fun tearDown() {
        random.clear()
    }

    @Test
    @JsName("It_fulfils_Generator")
    fun `It fulfils Generator`() {
        val generator: Any = UShortGenerator(random)

        assertTrue(generator is PublicApi.Generator<*>)
    }

    @Test
    @JsName("Given_generate_is_called_it_returns_a_UShort")
    fun `Given generate is called it returns a UShort`() {
        // Given
        val expected = 555
        random.nextInt = { expected }

        val generator = UShortGenerator(random)

        // When
        val result = generator.generate()

        // Then
        assertEquals(
            actual = result,
            expected = expected.toUShort()
        )
    }
}
