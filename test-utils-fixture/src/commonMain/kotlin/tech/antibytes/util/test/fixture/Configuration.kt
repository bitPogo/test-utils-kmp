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

internal class Configuration(
    override var seed: Int = 0
) : FixtureContract.Configuration {
    private fun initializeDefaultsProducers(random: Random): Map<String, PublicApi.Producer<out Any>> {
        return mapOf(
            resolveClassName(Boolean::class) to BooleanProducer(random),
            resolveClassName(Short::class) to ShortProducer(random),
            resolveClassName(Int::class) to IntegerProducer(random),
            resolveClassName(Float::class) to FloatProducer(random),
            resolveClassName(Char::class) to CharProducer(random),
            resolveClassName(Long::class) to LongProducer(random),
            resolveClassName(Double::class) to DoubleProducer(random),
            resolveClassName(String::class) to StringProducer(random),
            resolveClassName(ByteArray::class) to ByteArrayProducer(random),
            resolveClassName(UShort::class) to UShortProducer(random),
            resolveClassName(UInt::class) to UIntegerProducer(random),
            resolveClassName(ULong::class) to ULongProducer(random),
            resolveClassName(UByteArray::class) to UByteArrayProducer(random),
        )
    }

    override fun build(): PublicApi.Fixture {
        val random = Random(seed)

        return Fixture(
            random,
            initializeDefaultsProducers(random)
        )
    }
}
