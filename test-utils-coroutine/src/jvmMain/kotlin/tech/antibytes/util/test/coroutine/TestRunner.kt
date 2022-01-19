/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

actual val defaultTestContext: CoroutineContext = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
actual fun runBlockingTest(block: suspend CoroutineScope.() -> Unit) {
    runBlocking(defaultTestContext) { this.block() }
}
actual fun runBlockingTestInContext(
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    runBlocking(context) { this.block() }
}
