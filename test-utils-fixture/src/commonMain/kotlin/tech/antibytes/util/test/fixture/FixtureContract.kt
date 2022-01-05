/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture

import kotlin.random.Random

internal interface FixtureContract {
    interface InternalQualifier : PublicApi.Qualifier {
        val value: String
    }

    interface InternalFixture : PublicApi.Fixture {
        val random: Random
        val generators: Map<String, PublicApi.Producer<out Any>>
    }

    companion object {
        const val SEPARATOR = ":"
        const val QUALIFIER_PREFIX = "q:"
    }
}
