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

class IntegerGeneratorSpec {
    private val random = RandomStub()

    @AfterTest
    fun tearDown() {
        random.clear()
    }

    @Test
    fun `It fulfils Generator`() {
        val Generator: Any = IntegerGenerator(random)

        assertTrue(Generator is PublicApi.Generator<*>)
    }

    @Test
    fun `Given generate is called it returns a Integer`() {
        // Given
        val expected = 23
        random.nextInt = { expected }

        val Generator = IntegerGenerator(random)

        // When
        val result = Generator.generate()

        // Then
        assertEquals(
            actual = result,
            expected = expected
        )
    }
}
