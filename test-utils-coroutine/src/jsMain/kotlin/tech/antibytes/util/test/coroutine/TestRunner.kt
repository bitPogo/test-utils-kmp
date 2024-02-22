/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.coroutine

import kotlin.coroutines.CoroutineContext
import kotlin.js.Promise
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.promise
import kotlinx.coroutines.test.TestCoroutineScheduler

class ReturnValuePromise(executor: ((Any) -> Unit, (Throwable) -> Unit) -> Unit) : Promise<Any>(executor)

actual typealias AsyncTestReturnValue = ReturnValuePromise

private class JsTestScope : CustomTestScope {
    override val coroutineContext: CoroutineContext = defaultScheduler
    override val backgroundScope: CoroutineScope = CoroutineScope(coroutineContext)
    override val testScheduler: TestCoroutineScheduler = defaultScheduler as TestCoroutineScheduler
}

actual fun runBlockingTest(block: suspend CustomTestScope.() -> Unit): AsyncTestReturnValue {
    val result: dynamic = Promise.all(arrayOf(lastPromise)).then {
        val scope = JsTestScope()
        scope.promise {
            block(scope)
        }
    }

    defaultScheduler.advanceUntilIdle()

    lastPromise = result
    multiBlocks.add(result)

    return result
}

actual fun runBlockingTestInContext(
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> Unit,
): AsyncTestReturnValue {
    val result: dynamic = Promise.all(arrayOf(lastPromise)).then {
        CoroutineScope(context).promise { block() }
    }

    lastPromise = result
    multiBlocks.add(result)

    return result
}

private fun initialPromise(): dynamic = Promise.resolve(true)

private var lastPromise: AsyncTestReturnValue = initialPromise()

private val multiBlocks: MutableList<AsyncTestReturnValue> = mutableListOf(lastPromise)

actual fun clearBlockingTest() {
    multiBlocks.clear()

    lastPromise = initialPromise()

    multiBlocks.add(lastPromise)
}

actual fun resolveMultiBlockCalls(): AsyncTestReturnValue {
    multiBlocks.add(lastPromise)
    val all: dynamic = Promise.all(multiBlocks.toTypedArray())
    return all
}
