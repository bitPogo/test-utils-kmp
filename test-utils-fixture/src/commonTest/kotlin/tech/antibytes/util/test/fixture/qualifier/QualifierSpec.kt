/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture.qualifier

import tech.antibytes.util.test.fixture.FixtureContract
import tech.antibytes.util.test.fixture.int
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QualifierSpec {
    @Test
    @JsName("Given_named_is_called_with_String_it_returns_a_Qualifier")
    fun `Given named is called with String it returns a Qualifier`() {
        // Given
        val id = "id"

        // When
        val result = named(id)

        // Then
        assertTrue(result is StringQualifier)
        assertEquals(
            actual = result.value,
            expected = "${FixtureContract.QUALIFIER_PREFIX}$id"
        )
    }

    @Test
    @JsName("Given_named_is_called_with_Enum_it_returns_a_Qualifier")
    fun `Given named is called with Enum it returns a Qualifier`() {
        // Given
        val id = TestEnum.TEST

        // When
        val result = named(id)

        // Then
        assertTrue(result is StringQualifier)
        assertEquals(
            actual = result.value,
            expected = "${FixtureContract.QUALIFIER_PREFIX}${id.toString().lowercase()}"
        )
    }

    @Test
    @JsName("Given_resolveQualifier_is_called_with_Qualifiers_it_returns_a_String")
    fun `Given resolveQualifier is called with Qualifiers it returns a String`() {
        // Given
        val qualifier1 = StringQualifier("abc")
        val qualifier2 = TypeQualifier(Int::class)

        // When
        val result = resolveQualifier(qualifier1, qualifier2)

        // Then
        assertEquals(
            actual = result,
            expected = "q:abc:$int"
        )
    }
}

private enum class TestEnum {
    TEST
}
