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

class StringProducerSpec {
    private val random = RandomStub()

    @AfterTest
    fun tearDown() {
        random.clear()
    }

    @Test
    fun `It fulfils Producer`() {
        val producer: Any = StringProducer(random)

        assertTrue(producer is FixtureContract.Producer<*>)
    }

    @Test
    fun `Given generate is called it returns a String`() {
        // Given
        val producer = StringProducer(random)

        // When
        val result: Any = producer.generate()

        // Then
        assertTrue(result is String)
        assertTrue(result.contains("-"))
    }
}
