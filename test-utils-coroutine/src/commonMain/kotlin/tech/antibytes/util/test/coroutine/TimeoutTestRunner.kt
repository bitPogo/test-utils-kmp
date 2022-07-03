/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.coroutine

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withTimeout

fun runBlockingTestWithTimeout(
    milliseconds: Long = 2000,
    block: suspend CoroutineScope.() -> Unit,
): AsyncTestReturnValue {
    return runBlockingTest {
        withTimeout(milliseconds, block)
    }
}

fun runBlockingTestWithTimeoutInScope(
    context: CoroutineContext,
    milliseconds: Long = 2000,
    block: suspend CoroutineScope.() -> Unit,
): AsyncTestReturnValue {
    return runBlockingTestInContext(context) {
        withTimeout(milliseconds, block)
    }
}
