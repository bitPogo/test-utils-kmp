/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.ktor

import com.appmattus.kotlinfixture.kotlinFixture
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondOk
import io.ktor.client.features.HttpClientFeature
import io.ktor.client.request.get
import tech.antibytes.util.test.coroutine.runBlockingTest
import tech.antibytes.util.test.fulfils
import tech.antibytes.util.test.mustBe
import tech.antibytes.util.test.sameAs
import kotlin.test.Test

class KtorMockObjectResponseTest {
    private val fixture = kotlinFixture()

    @Test
    fun `It fulfils HttpClientFeature`() {
        val feature: Any = KtorMockObjectResponse

        feature fulfils HttpClientFeature::class
    }

    @Test
    fun `It has a key`() {
        KtorMockObjectResponse.key.name mustBe "KtorMockObjectResponse"
    }

    @Test
    fun `Given a response had been set up, it overwrites the response with the given one`() = runBlockingTest {
        // Given
        val objectResponse = Pair(fixture<String>(), fixture<String>())
        val client = HttpClient(MockEngine) {
            engine {
                addHandler {
                    respondOk(fixture<String>())
                }
            }

            install(KtorMockObjectResponse) {
                addResponse(objectResponse)
            }
        }

        // When
        val result = client.get<Pair<String, String>>(fixture<String>())

        // Then
        result sameAs objectResponse
    }

    @Test
    fun `Given a response had been set up, it overwrites the response with the given one for an arbitrary number of calls`() = runBlockingTest {
        // Given
        val objectResponse = Pair(fixture<String>(), fixture<String>())
        val client = HttpClient(MockEngine) {
            engine {
                addHandler {
                    respondOk(fixture<String>())
                }
            }

            install(KtorMockObjectResponse) {
                addResponse(objectResponse)
            }
        }

        // When
        client.get<Pair<String, String>>(fixture<String>())
        client.get<Pair<String, String>>(fixture<String>())
        val result = client.get<Pair<String, String>>(fixture<String>())

        // Then
        result sameAs objectResponse
    }

    @Test
    fun `Given multiple responses set up, it overwrites the responses with the given ones`() = runBlockingTest {
        // Given
        val objectResponses = listOf<Pair<String, String>>(
            Pair(fixture<String>(), fixture<String>()),
            Pair(fixture<String>(), fixture<String>()),
            Pair(fixture<String>(), fixture<String>()),
        )

        val client = HttpClient(MockEngine) {
            engine {
                addHandler {
                    respondOk(fixture<String>())
                }
            }

            install(KtorMockObjectResponse) {
                addResponses(objectResponses)
            }
        }

        for (objectResponse in objectResponses) {
            // When
            val result = client.get<Pair<String, String>>(fixture<String>())

            // Then
            result sameAs objectResponse
        }
    }

    @Test
    fun `Given a response had been installed and set up, it overwrites the responses with the given ones and returns the latest response for an arbitrary number of calls`() = runBlockingTest {
        // Given
        val objectResponses = listOf<Pair<String, String>>(
            Pair(fixture<String>(), fixture<String>()),
            Pair(fixture<String>(), fixture<String>()),
            Pair(fixture<String>(), fixture<String>()),
        )

        val client = HttpClient(MockEngine) {
            engine {
                addHandler {
                    respondOk(fixture<String>())
                }
            }

            install(KtorMockObjectResponse) {
                addResponses(objectResponses)
            }
        }

        // When
        for (objectResponse in objectResponses) {
            client.get<Pair<String, String>>(fixture<String>())
        }

        client.get<Pair<String, String>>(fixture<String>())
        client.get<Pair<String, String>>(fixture<String>())
        val result = client.get<Pair<String, String>>(fixture<String>())

        // Then
        result sameAs objectResponses.last()
    }
}
