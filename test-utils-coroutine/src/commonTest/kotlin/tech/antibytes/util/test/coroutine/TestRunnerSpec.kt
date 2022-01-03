/*
 * Copyright (c) 2021 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.coroutine

import com.appmattus.kotlinfixture.kotlinFixture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlin.test.Test
import kotlin.test.assertEquals

class TestRunnerSpec {
    private val fixture = kotlinFixture()

    @Test
    fun `Given runBlocking is called with a Closure, it runs in the DefaultScope`() = runBlockingTest {
        // Given
        val sample: String = fixture()
        val channel = Channel<String>()

        // When
        launch {
            channel.send(sample)
        }

        // Then
        assertEquals(
            actual = channel.receive(),
            expected = sample
        )
    }

    @Test
    fun `Given runBlockingTestWithContext is called with a Scope and a Closure and contains Scope, it runs in the given Scope`() = runBlockingTestWithContext(GlobalScope.coroutineContext) {
        // Given
        val sample: String = fixture()
        val channel = Channel<String>()

        // When
        launch {
            CoroutineScope(defaultTestContext).launch {
                channel.send(sample)
            }
        }

        // Then
        assertEquals(
            actual = channel.receive(),
            expected = sample
        )
    }
}
