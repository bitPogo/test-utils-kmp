/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture

import kotlin.random.Random

interface PublicApi {
    interface Producer<T : Any> {
        fun generate(): T
    }

    interface ProducerFactory<T: Any> {
        fun getInstance(): Producer<T>
    }

    interface Qualifier

    interface Configuration {
        var seed: Int
    }

    interface Fixture
}
