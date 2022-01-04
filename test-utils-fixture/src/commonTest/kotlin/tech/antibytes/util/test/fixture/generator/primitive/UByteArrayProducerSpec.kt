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

class UByteArrayProducerSpec {
    private val random = RandomStub()

    @AfterTest
    fun tearDown() {
        random.clear()
    }

    @Test
    fun `It fulfils Producer`() {
        val producer: Any = UByteArrayProducer(random)

        assertTrue(producer is FixtureContract.Producer<*>)
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
    fun `Given generate is called it returns a UByteArray`() {
        // Given
        val size = 23
        val expected = UByteArray(size)
        val producer = UByteArrayProducer(random)
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
