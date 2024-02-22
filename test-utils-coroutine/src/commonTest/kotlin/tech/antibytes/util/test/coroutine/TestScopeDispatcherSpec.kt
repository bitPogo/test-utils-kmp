/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.coroutine

import kotlin.js.JsName
import kotlin.test.Test
import kotlinx.coroutines.Dispatchers
import tech.antibytes.kfixture.fixture
import tech.antibytes.kfixture.kotlinFixture
import tech.antibytes.util.test.annotations.JsOnly
import tech.antibytes.util.test.isNot
import tech.antibytes.util.test.mustBe

class TestScopeDispatcherSpec {
    private val fixture = kotlinFixture()

    @Test
    @JsName("fn1")
    fun `Given dispatch is called with a Scope name it dispatches a new CoroutineScope`() {
        // When
        val scope = TestScopeDispatcher.dispatch(fixture.fixture())

        // Then
        scope isNot Dispatchers.Default
        scope isNot Dispatchers.Main
    }

    @Test
    @JsName("fn2")
    @JsOnly
    fun `Given dispatch is called with a Scope name it dispatches in the MainScope`() {
        // When
        val scope = TestScopeDispatcher.dispatch(fixture.fixture())

        // Then
        scope.toString().contains("Dispatchers.Main") mustBe true
    }
}
