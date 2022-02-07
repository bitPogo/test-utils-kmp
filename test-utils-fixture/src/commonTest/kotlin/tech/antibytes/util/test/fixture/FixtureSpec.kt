/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture

import tech.antibytes.util.test.fixture.mock.GeneratorStub
import tech.antibytes.util.test.fixture.mock.RandomStub
import tech.antibytes.util.test.fixture.qualifier.StringQualifier
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FixtureSpec {
    @Test
    @JsName("It_fulfils_Fixture")
    fun `It fulfils Fixture`() {
        val fixture: Any = Fixture(RandomStub(), emptyMap())

        assertTrue(fixture is PublicApi.Fixture)
    }

    @Test
    @JsName("Given_fixture_is_called_it_fails_if_the_Type_has_no_corresponding_Generator")
    fun `Given fixture is called it fails if the Type has no corresponding Generator`() {
        // Given
        val expected = 23
        val generator = GeneratorStub<Int>()
        generator.generate = { expected }

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
            expected = "Missing Generator for ClassID ($int)."
        )
    }

    @Test
    @JsName("Given_fixture_is_called_it_returns_a_Fixture_for_the_derived_Type")
    fun `Given fixture is called it returns a Fixture for the derived Type`() {
        // Given
        val expected = 23
        val generator = GeneratorStub<Int>()
        generator.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(RandomStub(), mapOf(int to generator))

        // When
        val result: Int = fixture.fixture()

        // Then
        assertEquals(
            actual = result,
            expected = expected
        )
    }

    @Test
    @JsName("Given_fixture_is_called_it_returns_a_Fixture_while_respecting_nullability")
    fun `Given fixture is called it returns a Fixture while respecting nullability`() {
        // Given
        val expected = 23
        val generator = GeneratorStub<Int>()
        val random = RandomStub()

        random.nextBoolean = { true }
        generator.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(random, mapOf(int to generator))

        // When
        val result: Int? = fixture.fixture()

        // Then
        assertNull(result)
    }

    @Test
    @JsName("Given_fixture_is_called_with_a_qualifier_it_returns_a_Fixture_for_the_derived_Type")
    fun `Given fixture is called with a qualifier it returns a Fixture for the derrived Type`() {
        // Given
        val expected = 23
        val qualifier = "test"
        val generator = GeneratorStub<Int>()
        generator.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(RandomStub(), mapOf("q:$qualifier:$int" to generator))

        // When
        val result: Int = fixture.fixture(StringQualifier(qualifier))

        // Then
        assertEquals(
            actual = result,
            expected = expected
        )
    }

    @Test
    @JsName("Given_listFixture_is_called_it_fails_if_the_Type_has_no_corresponding_Generator")
    fun `Given listFixture is called it fails if the Type has no corresponding Generator`() {
        // Given
        val expected = 23
        val random = RandomStub()
        val generator = GeneratorStub<Int>()
        generator.generate = { expected }
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
            expected = "Missing Generator for ClassID ($int)."
        )
    }

    @Test
    @JsName("Given_listFixture_is_called_it_returns_a_Fixture_for_the_derived_Type")
    fun `Given listFixture is called it returns a Fixture for the derrived Type`() {
        // Given
        val size = 5
        val expected = 23
        val random = RandomStub()
        val generator = GeneratorStub<Int>()

        var capturedMinimum = -1
        var capturedMaximum = -1

        random.nextIntRanged = { givenMinimum, givenMaximum ->
            capturedMinimum = givenMinimum
            capturedMaximum = givenMaximum
            size
        }
        generator.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(random, mapOf(int to generator))

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
    @JsName("Given_listFixture_is_called_it_returns_a_Fixture_while_respecting_nullability")
    fun `Given listFixture is called it returns a Fixture while respecting nullability`() {
        // Given
        val size = 5
        val expected = 23
        val random = RandomStub()
        val generator = GeneratorStub<Int>()

        var capturedMinimum = -1
        var capturedMaximum = -1

        random.nextIntRanged = { givenMinimum, givenMaximum ->
            capturedMinimum = givenMinimum
            capturedMaximum = givenMaximum
            size
        }
        random.nextBoolean = { true }

        generator.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(random, mapOf(int to generator))

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
    @JsName("Given_listFixture_is_called_with_a_qualifier_it_returns_a_Fixture_for_the_derived_Type")
    fun `Given listFixture is called with a qualifier it returns a Fixture for the derrived Type`() {
        // Given
        val size = 5
        val expected = 23
        val qualifier = "test"
        val random = RandomStub()
        val generator = GeneratorStub<Int>()

        random.nextIntRanged = { _, _ -> size }
        generator.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(random, mapOf("q:$qualifier:$int" to generator))

        // When
        val result = fixture.listFixture<Int>(StringQualifier(qualifier))

        // Then
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
    @JsName("Given_listFixture_is_called_with_a_size_it_returns_a_Fixture_for_the_derived_Type_in_the_given_size")
    fun `Given listFixture is called with a size it returns a Fixture for the derived Type in the given size`() {
        // Given
        val size = 5
        val expected = 23
        val random = RandomStub()
        val generator = GeneratorStub<Int>()

        random.nextIntRanged = { _, _ -> 23 }
        generator.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(random, mapOf(int to generator))

        // When
        val result = fixture.listFixture<Int>(size = size)

        // Then
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
    @JsName("Given_pairFixture_is_called_it_fails_if_the_Type_has_no_corresponding_Generator")
    fun `Given pairFixture is called it fails if the Type has no corresponding Generator`() {
        // Given
        val expected = 23
        val generator = GeneratorStub<Int>()
        generator.generate = { expected }

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
            expected = "Missing Generator for ClassID ($int)."
        )
    }

    @Test
    @JsName("Given_pairFixture_is_called_it_returns_a_Fixture_for_the_derived_Type")
    fun `Given pairFixture is called it returns a Fixture for the derrived Type`() {
        // Given
        val expected = 23
        val generator = GeneratorStub<Int>()
        generator.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(RandomStub(), mapOf(int to generator))

        // When
        val result = fixture.pairFixture<Int, Int>()

        // Then
        assertEquals(
            actual = result,
            expected = Pair(expected, expected)
        )
    }

    @Test
    @JsName("Given_pairFixture_is_called_it_returns_a_Fixture_while_respecting_nullability")
    fun `Given pairFixture is called it returns a Fixture while respecting nullability`() {
        // Given
        val expected = 23
        val generator = GeneratorStub<Int>()
        val random = RandomStub()

        random.nextBoolean = { true }
        generator.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(random, mapOf(int to generator))

        // When
        val result = fixture.pairFixture<Int, Int?>()

        // Then
        assertEquals(
            actual = result,
            expected = Pair(expected, null)
        )
    }

    @Test
    @JsName("Given_pairFixture_is_called_with_qualifiers_it_returns_a_Fixture_for_the_derived_Type")
    fun `Given pairFixture is called with qualifiers it returns a Fixture for the derived Type`() {
        // Given
        val expected = 23
        val keyQualifier = "testKey"
        val valueQualifier = "testValue"
        val generator = GeneratorStub<Int>()
        generator.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(
            RandomStub(),
            mapOf(
                "q:$keyQualifier:$int" to generator,
                "q:$valueQualifier:$int" to generator,
            )
        )

        // When
        val result = fixture.pairFixture<Int, Int>(
            StringQualifier(keyQualifier),
            StringQualifier(valueQualifier)
        )

        // Then
        assertEquals(
            actual = result,
            expected = Pair(expected, expected)
        )
    }

    @Test
    @JsName("Given_mapFixture_is_called_it_fails_if_the_Type_has_no_corresponding_Generator")
    fun `Given mapFixture is called it fails if the Type has no corresponding Generator`() {
        // Given
        val expected = 23
        val random = RandomStub()
        val generator = GeneratorStub<Int>()
        generator.generate = { expected }
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
            expected = "Missing Generator for ClassID ($int)."
        )
    }

    @Test
    @JsName("Given_mapFixture_is_called_it_returns_a_Fixture_for_the_derived_Type")
    fun `Given mapFixture is called it returns a Fixture for the derived Type`() {
        // Given
        val size = 5
        val expected = 23
        val random = RandomStub()
        val generator = GeneratorStub<Int>()

        var capturedMinimum = -1
        var capturedMaximum = -1

        random.nextIntRanged = { givenMinimum, givenMaximum ->
            capturedMinimum = givenMinimum
            capturedMaximum = givenMaximum
            size
        }
        generator.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(random, mapOf(int to generator))

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
    @JsName("Given_mapFixture_is_called_it_returns_a_Fixture_while_respecting_nullability")
    fun `Given mapFixture is called it returns a Fixture while respecting nullability`() {
        // Given
        val size = 5
        val expected = 23
        val random = RandomStub()
        val generator = GeneratorStub<Int>()

        var capturedMinimum = -1
        var capturedMaximum = -1

        random.nextIntRanged = { givenMinimum, givenMaximum ->
            capturedMinimum = givenMinimum
            capturedMaximum = givenMaximum
            size
        }
        random.nextBoolean = { true }
        generator.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(random, mapOf(int to generator))

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

    @Test
    @JsName("Given_mapFixture_is_called_with_a_Key_and_ValueQualifier_it_returns_a_Fixture_for_the_derived_Type")
    fun `Given mapFixture is called with a Key and ValueQualifier it returns a Fixture for the derived Type`() {
        // Given
        val size = 5
        val expected = 23
        val keyQualifier = "testKey"
        val valueQualifier = "testValue"
        val random = RandomStub()
        val generator = GeneratorStub<Int>()

        random.nextIntRanged = { _, _ -> size }
        generator.generate = { expected }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(
            random,
            mapOf(
                "q:$keyQualifier:$int" to generator,
                "q:$valueQualifier:$int" to generator,
            )
        )

        // When
        val result = fixture.mapFixture<Int, Int>(
            StringQualifier(keyQualifier),
            StringQualifier(valueQualifier),
        )

        // Then
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
    @JsName("Given_mapFixture_is_called_with_a_size_it_returns_a_Fixture_for_the_derived_Type_for_the_given_Size")
    fun `Given mapFixture is called with a size it returns a Fixture for the derived Type for the given Size`() {
        // Given
        val size = 5
        val random = RandomStub()
        val generator = GeneratorStub<Int>()

        val randomValues = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).toMutableList()

        random.nextIntRanged = { _, _ -> 23 }
        generator.generate = { randomValues.removeFirst() }

        // Ensure stable names since reified is in play
        resolveClassName(Int::class)

        val fixture = Fixture(
            random,
            mapOf(int to generator)
        )

        // When
        val result = fixture.mapFixture<Int, Int>(size = size)

        // Then
        assertEquals(
            actual = result.keys.size,
            expected = size
        )
    }
}
