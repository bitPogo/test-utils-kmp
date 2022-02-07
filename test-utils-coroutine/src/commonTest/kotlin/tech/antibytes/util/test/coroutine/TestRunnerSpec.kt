/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import tech.antibytes.util.test.annotations.IgnoreJs
import tech.antibytes.util.test.fixture.fixture
import tech.antibytes.util.test.fixture.kotlinFixture
import tech.antibytes.util.test.mustBe
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertFailsWith

class TestRunnerSpec {
    private val fixture = kotlinFixture()

    @Test
    @JsName("Given_runBlocking_is_called_with_a_Closure_it_runs_in_the_DefaultScope")
    fun `Given runBlocking is called with a Closure it runs in the DefaultScope`() = runBlockingTest {
        // Given
        val sample: String = fixture.fixture()
        val channel = Channel<String>()

        // When
        launch {
            channel.send(sample)
        }

        // Then
        channel.receive() mustBe sample
    }

    @Test
    @JsName("Given_runBlockingTestInContext_is_called_with_a_Scope_and_a_Closure_and_contains_Scope_it_runs_in_the_given_Scope")
    fun `Given runBlockingTestInContext is called with a Scope and a Closure and contains Scope it runs in the given Scope`() = runBlockingTestInContext(GlobalScope.coroutineContext) {
        // Given
        val sample: String = fixture.fixture()
        val channel = Channel<String>()

        // When
        launch {
            CoroutineScope(defaultTestContext).launch {
                channel.send(sample)
            }
        }

        // Then
        channel.receive() mustBe sample
    }

    @Test
    @IgnoreJs
    @JsName("Given_runBlockingTestWithTimeout_is_called_with_a_Long_and_Closure_it_run_the_given_Closure_and_fails_if_the_Timeout_is_reached")
    fun `Given runBlockingTestWithTimeout is called with a Long and Closure it run the given Closure and fails if the Timeout is reached`() {
        // Given
        val channel = Channel<String>()

        // Then
        assertFailsWith<TimeoutCancellationException> {
            runBlockingTestWithTimeout(20) {
                channel.receive()
            }
        }
    }

    @Test
    @IgnoreJs
    @JsName("Given_runBlockingTestWithTimeoutInScope_is_called_with_a_Long_Scope_and_a_Closure_it_run_the_given_Closure_and_fails_if_the_Timeout_is_reached")
    fun `Given runBlockingTestWithTimeoutInScope is called with a Long Scope and a Closure it run the given Closure and fails if the Timeout is reached`() {
        // Given
        val channel = Channel<String>()

        // Then
        assertFailsWith<TimeoutCancellationException> {
            runBlockingTestWithTimeoutInScope(GlobalScope.coroutineContext, 20) {
                channel.receive()
            }
        }
    }
}
