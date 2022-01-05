/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture

import tech.antibytes.util.test.fixture.mock.ProducerStub
import tech.antibytes.util.test.fixture.mock.RandomStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FixtureSpec {
    @Test
    fun `It fulfils Fixture`() {
        val fixture: Any = Fixture(RandomStub(), emptyMap())

        assertTrue(fixture is PublicApi.Fixture)
    }

    @Test
    fun `Given fixture is called, it fails if the Type has no coresponding producer`() {
        // Given
        val expected = 23
        val producer = ProducerStub<Int>()
        producer.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(RandomStub(), emptyMap())

        // Then
        val error = assertFailsWith<RuntimeException> {
            // When
            fixture.fixture<Int>()
        }


        assertEquals(
            actual = error.message,
            expected = "Missing Producer for ClassID (int)."
        )
    }

    @Test
    fun `Given fixture is called, it returns a Fixture for the derrived Type`() {
        // Given
        val expected = 23
        val producer = ProducerStub<Int>()
        producer.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(RandomStub(), mapOf("int" to producer))

        // When
        val result: Int = fixture.fixture()

        // Then
        assertEquals(
            actual = result,
            expected = expected
        )
    }

    @Test
    fun `Given fixture is called, it returns a Fixture, while respecting nullability`() {
        // Given
        val expected = 23
        val producer = ProducerStub<Int>()
        val random = RandomStub()

        random.nextBoolean = { true }
        producer.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(random, mapOf("int" to producer))

        // When
        val result: Int? = fixture.fixture()

        // Then
        assertNull(result)
    }
}
