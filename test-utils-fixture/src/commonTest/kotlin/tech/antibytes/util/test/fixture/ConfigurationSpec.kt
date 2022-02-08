/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture

import co.touchlab.stately.concurrency.AtomicReference
import co.touchlab.stately.isFrozen
import tech.antibytes.util.test.fixture.generator.array.ByteArrayGenerator
import tech.antibytes.util.test.fixture.generator.array.UByteArrayGenerator
import tech.antibytes.util.test.fixture.generator.primitive.AnyGenerator
import tech.antibytes.util.test.fixture.generator.primitive.BooleanGenerator
import tech.antibytes.util.test.fixture.generator.primitive.CharGenerator
import tech.antibytes.util.test.fixture.generator.primitive.DoubleGenerator
import tech.antibytes.util.test.fixture.generator.primitive.FloatGenerator
import tech.antibytes.util.test.fixture.generator.primitive.IntegerGenerator
import tech.antibytes.util.test.fixture.generator.primitive.LongGenerator
import tech.antibytes.util.test.fixture.generator.primitive.ShortGenerator
import tech.antibytes.util.test.fixture.generator.primitive.StringGenerator
import tech.antibytes.util.test.fixture.generator.primitive.UIntegerGenerator
import tech.antibytes.util.test.fixture.generator.primitive.ULongGenerator
import tech.antibytes.util.test.fixture.generator.primitive.UShortGenerator
import tech.antibytes.util.test.fixture.generator.primitive.UnitGenerator
import tech.antibytes.util.test.fixture.mock.GeneratorFactoryStub
import tech.antibytes.util.test.fixture.qualifier.named
import kotlin.js.JsName
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ConfigurationSpec {
    @Test
    @JsName("it_fulfils_Configuration")
    fun `It fulfils Configuration`() {
        val config: Any = Configuration()

        assertTrue(config is PublicApi.Configuration)
    }

    @Test
    @JsName("it_fulfils_InternalConfiguration")
    fun `It fulfils InternalConfiguration`() {
        val config: Any = Configuration()

        assertTrue(config is FixtureContract.Configuration)
    }

    @Test
    @JsName("it_has_default_Seed_of_Zero")
    fun `It has default Seed of 0`() {
        val config = Configuration()

        assertEquals(
            actual = config.seed,
            expected = 0
        )
    }

    @Test
    @JsName("Given_build_is_called_it_delegates_a_Random_Instance_with_the_given_Seed_to_the_Fixture")
    fun `Given build is called it delegates a Random Instance with the given Seed to the Fixture`() {
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
    @JsName("Given_build_is_called_it_delegates_the_default_Generators_to_the_Fixture")
    fun `Given build is called it delegates the default Generators to the Fixture`() {
        // Given
        val seed = 23
        val mapping = mapOf(
            boolean to BooleanGenerator::class,
            short to ShortGenerator::class,
            int to IntegerGenerator::class,
            char to CharGenerator::class,
            float to FloatGenerator::class,
            long to LongGenerator::class,
            double to DoubleGenerator::class,
            string to StringGenerator::class,
            uShort to UShortGenerator::class,
            uInt to UIntegerGenerator::class,
            uLong to ULongGenerator::class,
            byteArray to ByteArrayGenerator::class,
            uByteArray to UByteArrayGenerator::class,
            any to AnyGenerator::class,
            unit to UnitGenerator::class
        )

        // When
        val fixture = Configuration(seed).build()

        // Then
        assertEquals(
            actual = mapping.size,
            expected = fixture.generators.size
        )
        fixture.generators.forEach { (key, generator) ->
            assertTrue(
                mapping.containsKey(key),
                message = "Unknown Key ($key)"
            )

            assertTrue(
                mapping[key]!!.isInstance(generator),
                message = "Unexpected Generator for Key ($key)"
            )
        }
    }

    @Test
    @JsName("Given_addGenerator_is_called_with_a_Klass_and_a_GeneratorFactory_it_adds_the_a_custom_Generator")
    fun `Given addGenerator is called with a Klass and a GeneratorFactory it adds the a custom Generator`() {
        // Given
        val klass = TestClass::class
        val generator = TestGenerator
        val seed = 23

        // When
        val config = Configuration(seed)
            .addGenerator(klass, generator)
        val fixture = (config as FixtureContract.Configuration).build()

        // Then
        val generators = fixture.generators

        assertTrue(
            generators.containsKey("tech.antibytes.util.test.fixture.TestClass") || generators.containsKey("TestClass"),
            message = "Missing Key (tech.antibytes.util.test.fixture.TestClass)"
        )
        assertTrue(
            generators["tech.antibytes.util.test.fixture.TestClass"] is TestGenerator ||
                generators["TestClass"] is TestGenerator
        )

        if (!TestGenerator.lastRandom.get()!!.isFrozen) {
            assertEquals(
                actual = TestGenerator.lastRandom.get()!!.nextDouble(),
                expected = Random(seed).nextDouble()
            )
        }
    }

    @Test
    @JsName("Given_addGenerator_is_called_with_a_Klass_and_a_GeneratorFactory_it_prevents_overriding_buildins")
    fun `Given addGenerator is called with a Klass and a GeneratorFactory it prevents overriding buildins`() {
        // Given
        val klass = Int::class
        val generator = GeneratorFactoryStub<Int>()
        val seed = 23

        // When
        val config = Configuration(seed)
            .addGenerator(klass, generator)
        val fixture = (config as FixtureContract.Configuration).build()

        // Then
        val generators = fixture.generators

        assertNotEquals(
            actual = generators["int"],
            illegal = generator.lastInstance
        )
    }

    @Test
    @JsName("Given_addGenerator_is_called_with_a_Klass_a_GeneratorFactory_and_a_Qualifier_it_prevents_overriding_buildins")
    fun `Given addGenerator is called with a Klass a GeneratorFactory and a Qualifier it prevents overriding buildins`() {
        // Given
        val klass = TestClass::class
        val generator = TestGenerator
        val seed = 42
        val qualifier = "test"

        // When
        val config = Configuration(seed)
            .addGenerator(klass, generator, named(qualifier))

        val fixture = (config as FixtureContract.Configuration).build()

        // Then
        val generators = fixture.generators
        assertTrue(
            generators.containsKey("q:$qualifier:tech.antibytes.util.test.fixture.TestClass") ||
                generators.containsKey("q:$qualifier:TestClass"),
            message = "Missing Key (q:$qualifier:tech.antibytes.util.test.fixture.TestClass)"
        )
        assertTrue(
            generators["q:$qualifier:tech.antibytes.util.test.fixture.TestClass"] is TestGenerator ||
                generators["q:$qualifier:TestClass"] is TestGenerator
        )
    }
}

private data class TestClass(val value: String = "test")
private class TestGenerator : PublicApi.Generator<TestClass> {
    override fun generate(): TestClass = TestClass()

    companion object : PublicApi.GeneratorFactory<TestClass> {
        val lastRandom: AtomicReference<Random?> = AtomicReference(null)

        override fun getInstance(random: Random): PublicApi.Generator<TestClass> {
            return TestGenerator().also { lastRandom.set(random) }
        }
    }
}
