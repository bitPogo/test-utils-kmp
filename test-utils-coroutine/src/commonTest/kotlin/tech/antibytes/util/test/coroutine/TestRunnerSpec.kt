/*
 * Copyright (c) 2021 D4L data4life gGmbH / All rights reserved.
 *
 * D4L owns all legal rights, title and interest in and to the Software Development Kit ("SDK"),
 * including any intellectual property rights that subsist in the SDK.
 *
 * The SDK and its documentation may be accessed and used for viewing/review purposes only.
 * Any usage of the SDK for other purposes, including usage for the development of
 * applications/third-party applications shall require the conclusion of a license agreement
 * between you and D4L.
 *
 * If you are interested in licensing the SDK for your own applications/third-party
 * applications and/or if you’d like to contribute to the development of the SDK, please
 * contact D4L by email to help@data4life.care.
 */

package tech.antibytes.util.test.coroutine

import com.appmattus.kotlinfixture.kotlinFixture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import tech.antibytes.util.test.mustBe
import kotlin.test.Test

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
        channel.receive() mustBe sample
    }

    @Test
    fun `Given runBlockingTestWithContext is called with a Scope and a Closure and contains Scope, it runs in the given Scope`() =
        runBlockingTestWithContext(GlobalScope.coroutineContext) {
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
            channel.receive() mustBe sample
        }
}
