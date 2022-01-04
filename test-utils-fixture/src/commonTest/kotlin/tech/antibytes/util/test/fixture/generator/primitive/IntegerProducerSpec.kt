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

class IntegerProducerSpec {
    private val random = RandomStub()

    @AfterTest
    fun tearDown() {
        random.clear()
    }

    @Test
    fun `It fulfils Producer`() {
        val producer: Any = IntegerProducer(random)

        assertTrue(producer is FixtureContract.Producer<*>)
    }

    @Test
    fun `Given generate is called it returns a Integer`() {
        // Given
        val expected = 23
        random.nextInt = { expected }

        val producer = IntegerProducer(random)

        // When
        val result = producer.generate()

        // Then
        assertEquals(
            actual = result,
            expected = expected
        )
    }
}
