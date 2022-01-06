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

    @Test
    fun `Given listFixture is called, it fails if the Type has no coresponding producer`() {
        // Given
        val expected = 23
        val random = RandomStub()
        val producer = ProducerStub<Int>()
        producer.generate = { expected }
        random.nextIntRanged = { _, _ -> 42 }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(random, emptyMap())

        // Then
        val error = assertFailsWith<RuntimeException> {
            // When
            fixture.listFixture<Int>()
        }


        assertEquals(
            actual = error.message,
            expected = "Missing Producer for ClassID (int)."
        )
    }

    @Test
    fun `Given listFixture is called, it returns a Fixture for the derrived Type`() {
        // Given
        val size = 5
        val expected = 23
        val random = RandomStub()
        val producer = ProducerStub<Int>()

        var capturedMinimum = -1
        var capturedMaximum = -1

        random.nextIntRanged = { givenMinimum, givenMaximum ->
            capturedMinimum = givenMinimum
            capturedMaximum = givenMaximum
            size
        }
        producer.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(random, mapOf("int" to producer))

        // When
        val result = fixture.listFixture<Int>()

        // Then
        assertEquals(
            actual = capturedMinimum,
            expected = 1
        )
        assertEquals(
            actual = capturedMaximum,
            expected = 10
        )
        assertEquals(
            actual = result.size,
            expected = size
        )
        assertEquals(
            actual = result,
            expected = listOf(
                expected,
                expected,
                expected,
                expected,
                expected
            )
        )
    }

    @Test
    fun `Given listFixture is called, it returns a Fixture, while respecting nullability`() {
        // Given
        val size = 5
        val expected = 23
        val random = RandomStub()
        val producer = ProducerStub<Int>()

        var capturedMinimum = -1
        var capturedMaximum = -1

        random.nextIntRanged = { givenMinimum, givenMaximum ->
            capturedMinimum = givenMinimum
            capturedMaximum = givenMaximum
            size
        }
        random.nextBoolean = { true }

        producer.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(random, mapOf("int" to producer))

        // When
        val result = fixture.listFixture<Int?>()

        // Then
        assertEquals(
            actual = capturedMinimum,
            expected = 1
        )
        assertEquals(
            actual = capturedMaximum,
            expected = 10
        )
        assertEquals(
            actual = result.size,
            expected = size
        )
        assertEquals(
            actual = result,
            expected = listOf(
                null,
                null,
                null,
                null,
                null
            )
        )
    }

    @Test
    fun `Given pairFixture is called, it fails if the Type has no coresponding producer`() {
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
            fixture.pairFixture<Int, Int>()
        }


        assertEquals(
            actual = error.message,
            expected = "Missing Producer for ClassID (int)."
        )
    }

    @Test
    fun `Given pairFixture is called, it returns a Fixture for the derrived Type`() {
        // Given
        val expected = 23
        val producer = ProducerStub<Int>()
        producer.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(RandomStub(), mapOf("int" to producer))

        // When
        val result = fixture.pairFixture<Int, Int>()

        // Then
        assertEquals(
            actual = result,
            expected = Pair(expected, expected)
        )
    }

    @Test
    fun `Given pairFixture is called, it returns a Fixture, while respecting nullability`() {
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
        val result = fixture.pairFixture<Int, Int?>()

        // Then
        assertEquals(
            actual = result,
            expected = Pair(expected, null)
        )
    }

    @Test
    fun `Given mapFixture is called, it fails if the Type has no coresponding producer`() {
        // Given
        val expected = 23
        val random = RandomStub()
        val producer = ProducerStub<Int>()
        producer.generate = { expected }
        random.nextIntRanged = { _, _ -> 42 }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(random, emptyMap())

        // Then
        val error = assertFailsWith<RuntimeException> {
            // When
            fixture.mapFixture<Int, Int>()
        }


        assertEquals(
            actual = error.message,
            expected = "Missing Producer for ClassID (int)."
        )
    }

    @Test
    fun `Given mapFixture is called, it returns a Fixture for the derrived Type`() {
        // Given
        val size = 5
        val expected = 23
        val random = RandomStub()
        val producer = ProducerStub<Int>()

        var capturedMinimum = -1
        var capturedMaximum = -1

        random.nextIntRanged = { givenMinimum, givenMaximum ->
            capturedMinimum = givenMinimum
            capturedMaximum = givenMaximum
            size
        }
        producer.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(random, mapOf("int" to producer))

        // When
        val result = fixture.mapFixture<Int, Int>()

        // Then
        assertEquals(
            actual = capturedMinimum,
            expected = 1
        )
        assertEquals(
            actual = capturedMaximum,
            expected = 10
        )
        assertEquals(
            actual = result.size,
            expected = 1
        )

        assertEquals(
            actual = result.keys,
            expected = setOf(expected)
        )
        assertEquals(
            actual = result.values.toList(),
            expected = listOf(expected)
        )
    }

    @Test
    fun `Given mapFixture is called, it returns a Fixture, while respecting nullability`() {
        // Given
        val size = 5
        val expected = 23
        val random = RandomStub()
        val producer = ProducerStub<Int>()

        var capturedMinimum = -1
        var capturedMaximum = -1

        random.nextIntRanged = { givenMinimum, givenMaximum ->
            capturedMinimum = givenMinimum
            capturedMaximum = givenMaximum
            size
        }
        random.nextBoolean = { true }
        producer.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(random, mapOf("int" to producer))

        // When
        val result = fixture.mapFixture<Int, Int?>()

        // Then
        assertEquals(
            actual = capturedMinimum,
            expected = 1
        )
        assertEquals(
            actual = capturedMaximum,
            expected = 10
        )
        assertEquals(
            actual = result.size,
            expected = 1
        )

        assertEquals(
            actual = result.keys,
            expected = setOf(expected)
        )
        assertEquals(
            actual = result.values.toList(),
            expected = listOf(null)
        )
    }
}
