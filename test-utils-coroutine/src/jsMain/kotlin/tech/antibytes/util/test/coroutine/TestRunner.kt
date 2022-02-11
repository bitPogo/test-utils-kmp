/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.promise
import kotlin.coroutines.CoroutineContext
import kotlin.js.Promise

actual val defaultTestContext: CoroutineContext = MainScope().coroutineContext

private var internalAsyncError: dynamic = Unit

object ReturnValuePromise : Promise<Any>(
    executor = { _, _ -> }
)

actual typealias AsyncTestReturnValue = ReturnValuePromise

actual fun runBlockingTest(block: suspend CoroutineScope.() -> Unit): AsyncTestReturnValue {
    val returnValue: dynamic = CoroutineScope(defaultTestContext).promise { block() }

    asyncMultiBlock = returnValue

    return returnValue
}

actual fun runBlockingTestInContext(
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> Unit
): AsyncTestReturnValue {
    val returnValue: dynamic = CoroutineScope(context).promise { block() }

    asyncMultiBlock = returnValue

    return returnValue
}

private fun initialPromise(): dynamic = js("Promise.resolve(true)")

actual var asyncMultiBlock: AsyncTestReturnValue = initialPromise()
