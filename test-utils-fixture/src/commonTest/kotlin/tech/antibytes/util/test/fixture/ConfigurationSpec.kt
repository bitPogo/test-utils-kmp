/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture

import tech.antibytes.util.test.fixture.generator.array.ByteArrayGenerator
import tech.antibytes.util.test.fixture.generator.array.UByteArrayGenerator
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
import tech.antibytes.util.test.fixture.mock.GeneratorFactoryStub
import tech.antibytes.util.test.fixture.qualifier.named
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
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
            "boolean" to BooleanGenerator::class,
            "short" to ShortGenerator::class,
            "int" to IntegerGenerator::class,
            "char" to CharGenerator::class,
            "float" to FloatGenerator::class,
            "long" to LongGenerator::class,
            "double" to DoubleGenerator::class,
            "java.lang.String" to StringGenerator::class,
            "kotlin.UShort" to UShortGenerator::class,
            "kotlin.UInt" to UIntegerGenerator::class,
            "kotlin.ULong" to ULongGenerator::class,
            "[B" to ByteArrayGenerator::class,
            "kotlin.UByteArray" to UByteArrayGenerator::class,
        )

        // When
        val fixture = Configuration(seed).build()

        // Then
        assertEquals(
            actual = mapping.size,
            expected = fixture.generators.size
        )
        fixture.generators.forEach { (key, Generator) ->
            assertTrue(
                mapping.containsKey(key),
                message = "Unknown Key ($key)"
            )
            assertTrue(
                mapping[key]!!.isInstance(Generator),
                message = "Unexpected Generator for Key ($key)"
            )
        }
    }

    @Test
    fun `Given addGenerator is called with a Klass and a GeneratorFactory, it adds the a custom Generator`() {
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
            generators.containsKey("tech.antibytes.util.test.fixture.TestClass")
        )
        assertTrue(
            generators["tech.antibytes.util.test.fixture.TestClass"] is TestGenerator
        )
        assertEquals(
            actual = TestGenerator.lastRandom.nextDouble(),
            expected = Random(seed).nextDouble()
        )
    }

    @Test
    fun `Given addGenerator is called with a Klass and a GeneratorFactory, it prevents overriding buildins`() {
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
    fun `Given addGenerator is called with a Klass, a GeneratorFactory and a Qualifier, it prevents overriding buildins`() {
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
            generators.containsKey("q:$qualifier:tech.antibytes.util.test.fixture.TestClass")
        )
        assertTrue(
            generators["q:$qualifier:tech.antibytes.util.test.fixture.TestClass"] is TestGenerator
        )
    }
}

private data class TestClass(val value: String = "test")
private class TestGenerator : PublicApi.Generator<TestClass> {
    override fun generate(): TestClass = TestClass()

    companion object : PublicApi.GeneratorFactory<TestClass> {
        lateinit var lastRandom: Random

        override fun getInstance(random: Random): PublicApi.Generator<TestClass> {
            return TestGenerator().also { lastRandom = random }
        }
    }
}
