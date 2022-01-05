/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture.qualifier

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QualifierJvmSpec {
    @Test
    fun `Given named is called with Class it returns a Qualifier`() {
        // Given
        val id = Int::class

        // When
        val result = named(id)

        // Then
        assertTrue(result is TypeQualifier)
        assertEquals(
            actual = result.value,
            expected = id.java.name
        )
    }
}
