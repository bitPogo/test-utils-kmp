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

class CharProducerSpec {
    private val random = RandomStub()

    @AfterTest
    fun tearDown() {
        random.clear()
    }

    @Test
    fun `It fulfils Producer`() {
        val producer: Any = CharProducer(random)

        assertTrue(producer is PublicApi.Producer<*>)
    }

    @Test
    fun `Given generate is called it returns a Char`() {
        // Given
        val expected = 100
        var range: Pair<Int, Int>? = null

        random.nextIntRanged = { from, to ->
            range = Pair(from, to)
            expected
        }

        val producer = CharProducer(random)

        // When
        val result = producer.generate()

        // Then
        assertEquals(
            actual = range,
            expected = Pair(64, 126)
        )
        assertEquals(
            actual = result,
            expected = expected.toChar()
        )
    }
}
