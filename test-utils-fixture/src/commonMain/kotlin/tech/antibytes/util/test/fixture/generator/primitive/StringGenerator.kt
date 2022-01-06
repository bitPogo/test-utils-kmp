/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture.generator.primitive

import com.benasher44.uuid.uuid4
import tech.antibytes.util.test.fixture.PublicApi
import kotlin.random.Random

internal class StringGenerator(
    random: Random
) : PublicApi.Generator<String> {
    override fun generate(): String = uuid4().toString()
}
