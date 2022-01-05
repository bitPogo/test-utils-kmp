/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture.qualifier

import tech.antibytes.util.test.fixture.FixtureContract
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QualifierSpec {
    @Test
    fun `Given named is called with String it returns a Qualifier`() {
        // Given
        val id = "id"

        // When
        val result = named(id)

        // Then
        assertTrue(result is StringQualifier)
        assertEquals(
            actual = result.value,
            expected = "${FixtureContract.QUALIFIER_PREFIX}${id}"
        )
    }

    @Test
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
}

private enum class TestEnum {
    TEST
}
