/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test

import kotlin.reflect.KClass
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

inline infix fun <reified T : Any> Any.fulfils(type: KClass<T>) {
    assertTrue(this is T)
}

infix fun Any?.mustBe(value: Any?) {
    assertEquals(
        actual = this,
        expected = value
    )
}

infix fun Any?.isNot(value: Any?) {
    assertNotEquals(
        actual = this,
        illegal = value
    )
}

inline infix fun Any?.sameAs(value: Any?) {
    assertSame(
        actual = this,
        expected = value
    )
}
