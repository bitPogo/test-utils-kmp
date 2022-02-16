/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

/*
 * see: https://github.com/Kotlin/kotlinx.coroutines/issues/1996
 */

expect val defaultTestContext: CoroutineContext
expect object AsyncTestReturnValue
expect fun runBlockingTest(block: suspend CoroutineScope.() -> Unit): AsyncTestReturnValue
expect fun runBlockingTestInContext(
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> Unit
): AsyncTestReturnValue
expect var asyncMultiBlock: AsyncTestReturnValue
expect fun clearBlockingTest()

expect fun resolveMultiCall(vararg promises: AsyncTestReturnValue): AsyncTestReturnValue
