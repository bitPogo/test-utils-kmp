/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture.qualifier

import tech.antibytes.util.test.fixture.FixtureContract.Companion.SEPARATOR
import tech.antibytes.util.test.fixture.PublicApi

fun named(value: String): PublicApi.Qualifier = StringQualifier(value)

fun <E : Enum<E>> named(value: E): PublicApi.Qualifier {
    return StringQualifier(
        value.toString().lowercase()
    )
}

internal fun resolveQualifier(vararg qualifiers: PublicApi.Qualifier): String {
    return qualifiers
        .map { qualifier -> qualifier.value }
        .joinToString(SEPARATOR)
}

