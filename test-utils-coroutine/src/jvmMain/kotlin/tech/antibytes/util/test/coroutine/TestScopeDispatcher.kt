/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.coroutine

import java.util.concurrent.Executors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher

actual object TestScopeDispatcher {
    actual fun dispatch(scopeName: String): CoroutineScope {
        return CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())
    }
}
