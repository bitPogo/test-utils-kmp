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

class FloatProducerSpec {
    private val random = RandomStub()

    @AfterTest
    fun tearDown() {
        random.clear()
    }

    @Test
    fun `It fulfils Producer`() {
        val producer: Any = FloatProducer(random)

        assertTrue(producer is PublicApi.Producer<*>)
    }

    @Test
    fun `Given generate is called it returns a Float`() {
        // Given
        val expected = 23
        random.nextFloat = { expected.toFloat() }

        val producer = FloatProducer(random)

        // When
        val result = producer.generate()

        // Then
        assertEquals(
            actual = result,
            expected = expected.toFloat()
        )
    }
}
