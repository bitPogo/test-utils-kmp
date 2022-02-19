/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.coroutine

import co.touchlab.stately.concurrency.AtomicReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tech.antibytes.util.test.annotations.IgnoreJs
import tech.antibytes.util.test.fixture.fixture
import tech.antibytes.util.test.fixture.kotlinFixture
import tech.antibytes.util.test.mustBe
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TestRunnerSpec {
    private val fixture = kotlinFixture()

    @BeforeTest
    fun setUp() {
        clearBlockingTest()
    }

    @Test
    @JsName("fn0")
    fun `Given runBlocking is called with a Closure it runs in the DefaultScope`(): AsyncTestReturnValue {
        // Given
        val sample: String = fixture.fixture()
        val channel = Channel<String>()

        // When
        return runBlockingTest {
            launch {
                channel.send(sample)
            }

            // Then
            channel.receive() mustBe sample
        }
    }

    @Test
    @JsName("fn1")
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
    @JsName("fn2")
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
    @JsName("fn3")
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

    @Test
    @Ignore
    @JsName("fn4")
    fun `Given runBlocking is called it propagtes errors`(): AsyncTestReturnValue {
        return runBlockingTest {
            assertTrue(false)
        }
    }

    @Test
    @Ignore
    @JsName("fn5")
    fun `Given runBlockingTestInContext is called it propagtes errors`(): AsyncTestReturnValue {
        return runBlockingTestInContext(GlobalScope.coroutineContext) {
            assertTrue(false)
        }
    }

    @Test
    @Ignore
    @JsName("fn6")
    fun `Given runBlocking is called it propagtes chained errors`(): AsyncTestReturnValue {
        runBlockingTest {
            delay(400)
            assertTrue(false)
        }

        runBlockingTest {
            assertFalse(false)
        }

        return resolveMultiBlockCalls()
    }

    @Test
    @Ignore
    @JsName("fn7")
    fun `Given runBlockingTestInContext is called it propagtes chained errors`(): AsyncTestReturnValue {
        runBlockingTestInContext(GlobalScope.coroutineContext) {
            delay(400)
            assertTrue(false)
        }

        runBlockingTestInContext(defaultTestContext) {
            assertFalse(false)
        }

        return resolveMultiBlockCalls()
    }

    @Test
    @JsName("fn8")
    fun `Given runBlocking is called it allows chaining`(): AsyncTestReturnValue {
        val actual = AtomicReference(23)

        runBlockingTest {
            delay(400)
            actual.set(42)
        }

        runBlockingTest {
            assertEquals(
                42,
                actual.get()
            )
        }

        return resolveMultiBlockCalls()
    }

    @Test
    @JsName("fn9")
    fun `Given runBlockingTestInContext is called it allows chaining`(): AsyncTestReturnValue {
        val actual = AtomicReference(23)

        runBlockingTestInContext(GlobalScope.coroutineContext) {
            delay(400)
            actual.set(42)
        }

        runBlockingTestInContext(defaultTestContext) {
            assertEquals(
                42,
                actual.get()
            )
        }

        return resolveMultiBlockCalls()
    }

    @Test
    @Ignore
    @JsName("fn10")
    fun `Given mixed runBlocking and runBlockingTestInContext is called it propagtes chained errors`(): AsyncTestReturnValue {
        runBlockingTestInContext(GlobalScope.coroutineContext) {
            delay(400)
            assertTrue(false)
        }

        runBlockingTest {
            assertFalse(false)
        }

        return resolveMultiBlockCalls()
    }

    @Test
    @JsName("fn11")
    fun `Given mixed runBlocking and runBlockingTestInContext is called it allows chaining`(): AsyncTestReturnValue {
        val actual = AtomicReference(23)

        runBlockingTest {
            delay(400)
            actual.set(42)
        }

        runBlockingTestInContext(GlobalScope.coroutineContext) {
            assertEquals(
                42,
                actual.get()
            )
        }

        return resolveMultiBlockCalls()
    }
}
