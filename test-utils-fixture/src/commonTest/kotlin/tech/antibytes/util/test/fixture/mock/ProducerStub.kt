/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture.mock

import tech.antibytes.util.test.fixture.PublicApi

class ProducerStub<T :Any>(
    var generate: (() -> T)? = null
) : PublicApi.Producer<T> {
    override fun generate(): T {
       return generate?.invoke() ?: throw RuntimeException("Missing sideeffect for generate.")
    }
}
