/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondOk
import io.ktor.client.features.HttpClientFeature
import io.ktor.client.request.get
import tech.antibytes.util.test.coroutine.AsyncTestReturnValue
import tech.antibytes.util.test.coroutine.clearBlockingTest
import tech.antibytes.util.test.coroutine.resolveMultiBlockCalls
import tech.antibytes.util.test.coroutine.runBlockingTest
import tech.antibytes.util.test.fixture.fixture
import tech.antibytes.util.test.fixture.kotlinFixture
import tech.antibytes.util.test.fulfils
import tech.antibytes.util.test.mustBe
import tech.antibytes.util.test.sameAs
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test

class KtorMockObjectResponseTest {
    private val fixture = kotlinFixture()

    @BeforeTest
    fun setUp() {
        clearBlockingTest()
    }

    @Test
    @JsName("fn1")
    fun `It fulfils HttpClientFeature`() {
        val feature: Any = KtorMockObjectResponse

        feature fulfils HttpClientFeature::class
    }

    @Test
    @JsName("fn2")
    fun `It has a key`() {
        KtorMockObjectResponse.key.name mustBe "KtorMockObjectResponse"
    }

    @Test
    @JsName("fn3")
    fun `Given a response had been set up it overwrites the response with the given one`(): AsyncTestReturnValue {
        // Given
        val objectResponse = Pair(fixture.fixture<String>(), fixture.fixture<String>())
        val client = HttpClient(MockEngine) {
            engine {
                addHandler {
                    respondOk(fixture.fixture())
                }
            }

            install(KtorMockObjectResponse) {
                addResponse(objectResponse)
            }
        }

        // When
        return runBlockingTest {
            val result = client.get<Pair<String, String>>(fixture.fixture<String>())

            // Then
            result sameAs objectResponse
        }
    }

    @Test
    @JsName("fn4")
    fun `Given a response had been set up it overwrites the response with the given one for an arbitrary number of calls`(): AsyncTestReturnValue {
        // Given
        val objectResponse = Pair(fixture.fixture<String>(), fixture.fixture<String>())
        val client = HttpClient(MockEngine) {
            engine {
                addHandler {
                    respondOk(fixture.fixture<String>())
                }
            }

            install(KtorMockObjectResponse) {
                addResponse(objectResponse)
            }
        }

        // When
        return runBlockingTest {
            client.get<Pair<String, String>>(fixture.fixture<String>())
            client.get<Pair<String, String>>(fixture.fixture<String>())
            val result = client.get<Pair<String, String>>(fixture.fixture<String>())

            // Then
            result sameAs objectResponse
        }
    }

    @Test
    @JsName("fn5")
    fun `Given multiple responses set up it overwrites the responses with the given ones`(): AsyncTestReturnValue {
        // Given
        val objectResponses = listOf<Pair<String, String>>(
            Pair(fixture.fixture(), fixture.fixture()),
            Pair(fixture.fixture(), fixture.fixture()),
            Pair(fixture.fixture(), fixture.fixture()),
        )

        val client = HttpClient(MockEngine) {
            engine {
                addHandler {
                    respondOk(fixture.fixture())
                }
            }

            install(KtorMockObjectResponse) {
                addResponses(objectResponses)
            }
        }

        for (objectResponse in objectResponses) {
            // When
            runBlockingTest {
                val result = client.get<Pair<String, String>>(fixture.fixture<String>())

                // Then
                result sameAs objectResponse
            }
        }

        return resolveMultiBlockCalls()
    }

    @Test
    @JsName("fn6")
    fun `Given a response had been installed and set up it overwrites the responses with the given ones and returns the latest response for an arbitrary number of calls`(): AsyncTestReturnValue {
        // Given
        val objectResponses = listOf<Pair<String, String>>(
            Pair(fixture.fixture(), fixture.fixture()),
            Pair(fixture.fixture(), fixture.fixture()),
            Pair(fixture.fixture(), fixture.fixture()),
        )

        val client = HttpClient(MockEngine) {
            engine {
                addHandler {
                    respondOk(fixture.fixture())
                }
            }

            install(KtorMockObjectResponse) {
                addResponses(objectResponses)
            }
        }

        // When
        return runBlockingTest {
            for (objectResponse in objectResponses) {
                client.get<Pair<String, String>>(fixture.fixture<String>())
            }

            client.get<Pair<String, String>>(fixture.fixture<String>())
            client.get<Pair<String, String>>(fixture.fixture<String>())
            val result = client.get<Pair<String, String>>(fixture.fixture<String>())

            // Then
            result sameAs objectResponses.last()
        }
    }
}
