/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture.qualifier

import tech.antibytes.util.test.fixture.PublicApi
import kotlin.reflect.KClass

fun named(value: String): PublicApi.Qualifier = StringQualifier(value)

fun <E : Enum<E>> named(value: E): PublicApi.Qualifier {
    return StringQualifier(
        value.toString().lowercase()
    )
}

internal fun <T : Any> named(value: KClass<T>): PublicApi.Qualifier = TypeQualifier(value)
