/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture

import tech.antibytes.util.test.fixture.generator.array.ByteArrayProducer
import tech.antibytes.util.test.fixture.generator.array.UByteArrayProducer
import tech.antibytes.util.test.fixture.generator.primitive.BooleanProducer
import tech.antibytes.util.test.fixture.generator.primitive.CharProducer
import tech.antibytes.util.test.fixture.generator.primitive.DoubleProducer
import tech.antibytes.util.test.fixture.generator.primitive.FloatProducer
import tech.antibytes.util.test.fixture.generator.primitive.IntegerProducer
import tech.antibytes.util.test.fixture.generator.primitive.LongProducer
import tech.antibytes.util.test.fixture.generator.primitive.ShortProducer
import tech.antibytes.util.test.fixture.generator.primitive.StringProducer
import tech.antibytes.util.test.fixture.generator.primitive.UIntegerProducer
import tech.antibytes.util.test.fixture.generator.primitive.ULongProducer
import tech.antibytes.util.test.fixture.generator.primitive.UShortProducer
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ConfigurationSpec {
    @Test
    fun `It fulfils Configuration`() {
        val config: Any = Configuration()

        assertTrue(config is PublicApi.Configuration)
    }

    @Test
    fun `It fulfils InternalConfiguration`() {
        val config: Any = Configuration()

        assertTrue(config is FixtureContract.Configuration)
    }

    @Test
    fun `It has default Seed of 0`() {
        val config = Configuration()

        assertEquals(
            actual = config.seed,
            expected = 0
        )
    }

    @Test
    fun `Given build is called, it delegates a Random Instance with the given Seed to the Fixture`() {
        // Given
        val seed = 23

        // When
        val fixture = Configuration(seed).build()

        // Then
        assertEquals(
            actual = fixture.random.nextDouble(),
            expected = Random(seed).nextDouble()
        )
    }

    @Test
    fun `Given build is called, it delegates the default Generators to the Fixture`() {
        // Given
        val seed = 23
        val mapping = mapOf(
            "boolean" to BooleanProducer::class,
            "short" to ShortProducer::class,
            "int" to IntegerProducer::class,
            "char" to CharProducer::class,
            "float" to FloatProducer::class,
            "long" to LongProducer::class,
            "double" to DoubleProducer::class,
            "java.lang.String" to StringProducer::class,
            "kotlin.UShort" to UShortProducer::class,
            "kotlin.UInt" to UIntegerProducer::class,
            "kotlin.ULong" to ULongProducer::class,
            "[B" to ByteArrayProducer::class,
            "kotlin.UByteArray" to UByteArrayProducer::class,
        )

        // When
        val fixture = Configuration(seed).build()

        // Then
        assertEquals(
            actual = mapping.size,
            expected = fixture.generators.size
        )
        fixture.generators.forEach { (key, producer) ->
            assertTrue(
                mapping.containsKey(key),
                message = "Unknown Key ($key)"
            )
            assertTrue(
                mapping[key]!!.isInstance(producer),
                message = "Unexpected Producer for Key ($key)"
            )
        }
    }
}
