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

class LongProducerSpec {
    private val random = RandomStub()

    @AfterTest
    fun tearDown() {
        random.clear()
    }

    @Test
    fun `It fulfils Producer`() {
        val producer: Any = LongProducer(random)

        assertTrue(producer is FixtureContract.Producer<*>)
    }

    @Test
    fun `Given generate is called it returns a Long`() {
        // Given
        val expected: Long = 23
        random.nextLong = { expected }

        val producer = LongProducer(random)

        // When
        val result = producer.generate()

        // Then
        assertEquals(
            actual = result,
            expected = expected
        )
    }
}
