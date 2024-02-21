/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.coroutine

import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

actual typealias AsyncTestReturnValue = Unit
actual fun runBlockingTest(block: suspend TestScope.() -> Unit) = runTest { block() }
actual fun runBlockingTestInContext(
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> Unit,
) {
    return runBlocking(context) { this.block() }
}

actual fun clearBlockingTest() { /* Do nothing */ }

actual fun resolveMultiBlockCalls(): AsyncTestReturnValue = Unit
