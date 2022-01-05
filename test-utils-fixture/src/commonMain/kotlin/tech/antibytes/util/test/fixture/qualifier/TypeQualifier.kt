/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture.qualifier

import tech.antibytes.util.test.fixture.FixtureContract
import tech.antibytes.util.test.fixture.resolveClassName
import kotlin.reflect.KClass

internal class TypeQualifier(
    private val type: KClass<out Any>
) : FixtureContract.Qualifier {
    override val value: String
        get() = resolveClassName(type)
}
