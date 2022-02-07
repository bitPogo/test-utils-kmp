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

actual val defaultTestContext: CoroutineContext = MainScope().coroutineContext
actual fun runBlockingTest(block: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(defaultTestContext).promise { this.block() }
}
actual fun runBlockingTestInContext(
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    CoroutineScope(context).promise { this.block() }
}
