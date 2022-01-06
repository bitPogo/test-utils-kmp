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

inline fun <reified T> PublicApi.Fixture.listFixture(): List<T> {
    val size = random.nextInt(1, 10)

    val list = mutableListOf<T>()

    for (idx in 0 until size) {
        list.add(fixture())
    }

    return list
}

inline fun <reified First, reified Second> PublicApi.Fixture.pairFixture(): Pair<First, Second> {
    return Pair(
        fixture(),
        fixture()
    )
}


inline fun <reified Key, reified Value> PublicApi.Fixture.mapFixture(): Map<Key, Value> {
    val size = random.nextInt(1, 10)

    val list = mutableListOf<Pair<Key, Value>>()

    for (idx in 0 until size) {
        list.add(pairFixture())
    }

    return list.toMap()
}
