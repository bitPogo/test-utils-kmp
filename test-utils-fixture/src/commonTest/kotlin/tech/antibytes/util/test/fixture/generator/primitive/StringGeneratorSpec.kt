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
import kotlin.test.assertTrue

class StringGeneratorSpec {
    private val random = RandomStub()

    @AfterTest
    fun tearDown() {
        random.clear()
    }

    @Test
    fun `It fulfils Generator`() {
        val Generator: Any = StringGenerator(random)

        assertTrue(Generator is PublicApi.Generator<*>)
    }

    @Test
    fun `Given generate is called it returns a String`() {
        // Given
        val Generator = StringGenerator(random)

        // When
        val result: Any = Generator.generate()

        // Then
        assertTrue(result is String)
        assertTrue(result.contains("-"))
    }
}
