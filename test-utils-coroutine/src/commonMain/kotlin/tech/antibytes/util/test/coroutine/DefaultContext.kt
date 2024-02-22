/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.coroutine

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope

val defaultScheduler: TestCoroutineScheduler = TestCoroutineScheduler()

interface CustomTestScope : CoroutineScope {
    override val coroutineContext: CoroutineContext
    val backgroundScope: CoroutineScope
    val testScheduler: TestCoroutineScheduler
}

internal class TestScopeWrapper(scope: TestScope) : CustomTestScope {
    override val coroutineContext: CoroutineContext = scope.coroutineContext
    override val backgroundScope: CoroutineScope = scope.backgroundScope
    override val testScheduler: TestCoroutineScheduler = scope.testScheduler
}
