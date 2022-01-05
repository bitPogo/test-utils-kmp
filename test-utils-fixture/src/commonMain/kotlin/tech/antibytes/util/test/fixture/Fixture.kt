/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture

import kotlin.random.Random
import kotlin.reflect.KClass

internal data class Fixture(
    override val random: Random,
    override val generators: Map<String, PublicApi.Producer<out Any>>
) : PublicApi.Fixture

@InternalAPI
inline fun <reified T> isNullable(): Boolean = null is T

@InternalAPI
inline fun <reified T> returnNull(random: Random): Boolean {
    return if (isNullable<T>()) {
        random.nextBoolean()
    } else {
        false
    }
}

inline fun <reified T> PublicApi.Fixture.fixture(): T {
    val returnNull = returnNull<T>(random)

    val id = resolveClassName(T::class as KClass<*>)

    return when {
        !generators.containsKey(id) -> throw RuntimeException("Missing Producer for ClassID ($id).")
        returnNull -> null as T
        else -> generators[id]!!.generate() as T
    }
}
