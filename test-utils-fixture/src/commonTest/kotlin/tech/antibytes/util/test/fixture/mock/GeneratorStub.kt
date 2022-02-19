/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.fixture.mock

import co.touchlab.stately.isolate.IsolateState
import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic
import tech.antibytes.util.test.fixture.PublicApi
import kotlin.js.JsName
import kotlin.random.Random

class GeneratorStub<T : Any>(
    @JsName("generateStub")
    var generate: (() -> T)? = null
) : PublicApi.Generator<T> {
    override fun generate(): T {
        return generate?.invoke() ?: throw RuntimeException("Missing sideeffect for generate.")
    }
}

class GeneratorFactoryStub<T : Any>(
    @JsName("generateStub")
    var generate: (() -> T)? = null
) : PublicApi.GeneratorFactory<T> {
    private val _lastRandom: AtomicRef<IsolateState<Random>> = atomic(IsolateState { Random(49) })
    private val _lastInstance: AtomicRef<GeneratorStub<T>?> = atomic(null)

    var lastRandom by _lastRandom
    var lastInstance by _lastInstance

    override fun getInstance(random: IsolateState<Random>): PublicApi.Generator<T> {
        return GeneratorStub(generate).also {
            lastRandom = random
            lastInstance = it
        }
    }
}
