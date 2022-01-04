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
import kotlin.test.assertSame
import kotlin.test.assertTrue

class ByteArrayProducerSpec {
    private val random = RandomStub()

    @AfterTest
    fun tearDown() {
        random.clear()
    }

    @Test
    fun `It fulfils Producer`() {
        val producer: Any = ByteArrayProducer(random)

        assertTrue(producer is FixtureContract.Producer<*>)
    }

    @Test
    fun `Given generate is called it returns a ByteArray`() {
        // Given
        val size = 23
        val expected = ByteArray(size)
        val producer = ByteArrayProducer(random)
        var range: Pair<Int, Int>? = null

        random.nextIntRanged = { from, to ->
            range = Pair(from, to)
            size
        }

        random.nextByteArray = { arraySize -> ByteArray(arraySize) }

        // When
        val result = producer.generate()

        // Then
        assertEquals(
            actual = Pair(1, 100),
            expected = range
        )
        assertTrue(
            expected.contentEquals(result)
        )
    }
}
