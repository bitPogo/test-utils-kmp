/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture

import tech.antibytes.util.test.fixture.qualifier.TypeQualifier
import tech.antibytes.util.test.fixture.qualifier.resolveQualifier
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

@InternalAPI
fun resolveId(
    clazz: KClass<out Any>,
    qualifier: PublicApi.Qualifier? = null
): String {
    return if (qualifier == null) {
        resolveClassName(clazz)
    } else {
        resolveQualifier(qualifier, TypeQualifier(clazz))
    }
}

inline fun <reified T> PublicApi.Fixture.fixture(
    qualifier: PublicApi.Qualifier? = null
): T {
    val returnNull = returnNull<T>(random)

    val id = resolveId(
        T::class as KClass<*>,
        qualifier
    )

    return when {
        !generators.containsKey(id) -> throw RuntimeException("Missing Producer for ClassID ($id).")
        returnNull -> null as T
        else -> generators[id]!!.generate() as T
    }
}

inline fun <reified T> PublicApi.Fixture.listFixture(
    qualifier: PublicApi.Qualifier? = null
): List<T> {
    val size = random.nextInt(1, 10)

    val list = mutableListOf<T>()

    for (idx in 0 until size) {
        list.add(fixture(qualifier))
    }

    return list
}

inline fun <reified First, reified Second> PublicApi.Fixture.pairFixture(
    keyQualifier: PublicApi.Qualifier? = null,
    valueQualifier: PublicApi.Qualifier? = null,
): Pair<First, Second> {
    return Pair(
        fixture(keyQualifier),
        fixture(valueQualifier)
    )
}


inline fun <reified Key, reified Value> PublicApi.Fixture.mapFixture(
    keyQualifier: PublicApi.Qualifier? = null,
    valueQualifier: PublicApi.Qualifier? = null,
): Map<Key, Value> {
    val size = random.nextInt(1, 10)

    val list = mutableListOf<Pair<Key, Value>>()

    for (idx in 0 until size) {
        list.add(pairFixture(keyQualifier, valueQualifier))
    }

    return list.toMap()
}
