/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test

import kotlin.reflect.KClass
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotSame
import kotlin.test.assertSame
import kotlin.test.assertTrue

@Suppress("UNUSED_PARAMETER")
inline infix fun <reified T : Any> Any?.fulfils(type: KClass<T>) {
    assertTrue(this is T)
}

inline infix fun <reified T> T?.mustBe(expected: T) {
    assertEquals(
        actual = this,
        expected = expected,
    )
}

inline infix fun <reified T> T?.isNot(illegal: T) {
    assertNotEquals(
        actual = this,
        illegal = illegal,
    )
}

inline infix fun <reified T> T?.sameAs(expected: T) {
    assertSame(
        actual = this,
        expected = expected,
    )
}

inline infix fun <reified T> T?.notSameAs(illegal: T) {
    assertNotSame(
        actual = this,
        illegal = illegal,
    )
}
