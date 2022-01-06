/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture.generator.primitive

import tech.antibytes.util.test.fixture.PublicApi
import tech.antibytes.util.test.fixture.mock.RandomStub
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LongGeneratorSpec {
    private val random = RandomStub()

    @AfterTest
    fun tearDown() {
        random.clear()
    }

    @Test
    fun `It fulfils Generator`() {
        val Generator: Any = LongGenerator(random)

        assertTrue(Generator is PublicApi.Generator<*>)
    }

    @Test
    fun `Given generate is called it returns a Long`() {
        // Given
        val expected: Long = 23
        random.nextLong = { expected }

        val Generator = LongGenerator(random)

        // When
        val result = Generator.generate()

        // Then
        assertEquals(
            actual = result,
            expected = expected
        )
    }
}
