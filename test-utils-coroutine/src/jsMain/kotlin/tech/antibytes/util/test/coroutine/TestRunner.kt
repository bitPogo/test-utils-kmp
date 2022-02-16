/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.promise
import kotlin.coroutines.CoroutineContext
import kotlin.js.Promise

actual val defaultTestContext: CoroutineContext = MainScope().coroutineContext

object ReturnValuePromise : Promise<Any>(
    executor = { _, _ -> }
)

actual typealias AsyncTestReturnValue = ReturnValuePromise

@OptIn(ExperimentalCoroutinesApi::class)
actual fun runBlockingTest(block: suspend CoroutineScope.() -> Unit): AsyncTestReturnValue {
    val result: dynamic = Promise.all(arrayOf(asyncMultiBlock)).then {
        CoroutineScope(defaultTestContext).promise { block() }
    }

    asyncMultiBlock = result

    return result
}

actual fun runBlockingTestInContext(
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> Unit
): AsyncTestReturnValue {
    val result: dynamic = Promise.all(arrayOf(asyncMultiBlock)).then {
        CoroutineScope(context).promise { block() }
    }

    asyncMultiBlock = result

    multiBlocks.add(result)

    return result
}

private fun initialPromise(): dynamic = Promise.resolve(true)

private var asyncMultiBlock: AsyncTestReturnValue = initialPromise()

private val multiBlocks: MutableList<AsyncTestReturnValue> = mutableListOf(asyncMultiBlock)

actual fun clearBlockingTest() {
    multiBlocks.clear()

    asyncMultiBlock = initialPromise()

    multiBlocks.add(asyncMultiBlock)
}

actual fun resolveMultiBlockCalls(): AsyncTestReturnValue {
    val all: dynamic = Promise.all(multiBlocks.toTypedArray())
    return all
}
