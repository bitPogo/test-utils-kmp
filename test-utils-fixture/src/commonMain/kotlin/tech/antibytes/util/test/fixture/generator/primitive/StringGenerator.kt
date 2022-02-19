/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture.generator.primitive

import tech.antibytes.util.test.fixture.PublicApi
import kotlin.random.Random

internal class StringGenerator(
    private val random: Random
) : PublicApi.Generator<String> {
    override fun generate(): String {
        val length = random.nextInt(1, 10)
        val chars = ByteArray(length)

        for (idx in 0 until length) {
            chars[idx] = random.nextInt(33, 126).toByte()
        }

        return chars.decodeToString()
    }
}
