/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture.generator.primitive

import tech.antibytes.util.test.fixture.FixtureContract
import kotlin.random.Random
import kotlin.random.nextULong

class ULongProducer(
    private val random: Random
) : FixtureContract.Producer<ULong> {
    override fun generate(): ULong = random.nextULong()
}
