/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture.generator.primitive

import tech.antibytes.util.test.fixture.FixtureContract
import tech.antibytes.util.test.fixture.mock.RandomStub
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DoubleProducerSpec {
    private val random = RandomStub()

    @AfterTest
    fun tearDown() {
        random.clear()
    }

    @Test
    fun `It fulfils Producer`() {
        val producer: Any = DoubleProducer(random)

        assertTrue(producer is FixtureContract.Producer<*>)
    }

    @Test
    fun `Given generate is called it returns a Double`() {
        // Given
        val expected = 23.23
        random.nextDouble = { expected }

        val producer = DoubleProducer(random)

        // When
        val result = producer.generate()

        // Then
        assertEquals(
            actual = result,
            expected = expected
        )
    }
}
