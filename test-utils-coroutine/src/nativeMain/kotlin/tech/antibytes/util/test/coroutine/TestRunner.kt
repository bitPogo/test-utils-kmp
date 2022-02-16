/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
actual val defaultTestContext: CoroutineContext = newSingleThreadContext("testRunner")

actual typealias AsyncTestReturnValue = Unit
actual fun runBlockingTest(block: suspend CoroutineScope.() -> Unit) {
    return runBlocking(defaultTestContext) { block() }
}

actual fun runBlockingTestInContext(
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    return runBlocking(context) { block() }
}

actual fun clearBlockingTest() { /* Do nothing */ }

actual fun resolveMultiBlockCalls(): AsyncTestReturnValue = Unit
