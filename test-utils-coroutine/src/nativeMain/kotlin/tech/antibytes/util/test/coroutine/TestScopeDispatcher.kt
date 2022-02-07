/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext

actual object TestScopeDispatcher {
    @OptIn(ExperimentalCoroutinesApi::class)
    actual fun dispatch(scopeName: String): CoroutineScope {
        return CoroutineScope(newSingleThreadContext("scopeName"))
    }
}
