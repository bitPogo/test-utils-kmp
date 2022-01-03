/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test

import kotlin.reflect.KClass
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

inline infix fun <reified T : Any> Any.fulfils(type: KClass<T>) {
    assertTrue(this is T)
}

inline infix fun <reified T : Any> Any.mustBe(value: T) {
    assertEquals(
        actual = this,
        expected = value
    )
}

inline infix fun <reified T : Any> Any.sameAs(value: T) {
    assertSame(
        actual = this,
        expected = value
    )
}
