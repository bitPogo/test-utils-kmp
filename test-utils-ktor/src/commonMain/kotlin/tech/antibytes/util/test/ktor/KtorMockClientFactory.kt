/*
 * Copyright (c) 2024 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers
import io.ktor.http.isSuccess

data class Content(
    val content: String,
    val headers: Map<String, List<String>> = emptyMap(),
)

object KtorMockClientFactory {
    private fun addResponse(
        scope: MockRequestHandleScope,
        response: String,
        headers: Map<String, List<String>>,
        status: HttpStatusCode,
    ): HttpResponseData {
        val requestHeaders = headers {
            headers.forEach { (name, value) ->
                appendAll(name, value)
            }
        }

        return if (status.isSuccess()) {
            scope.respond(
                content = response,
                status = status,
                headers = requestHeaders,
            )
        } else {
            scope.respondError(status = status)
        }
    }

    fun createSimpleMockClient(
        response: Content,
        error: Throwable? = null,
        status: HttpStatusCode = HttpStatusCode.OK,
        assert: HttpRequestData.() -> Unit = {},
    ): HttpClient {
        return HttpClient(MockEngine) {
            engine {
                addHandler {
                    assert(it)
                    addResponse(
                        this,
                        response = response.content,
                        status = status,
                        headers = response.headers,
                    )
                }
            }

            if (!status.isSuccess()) {
                expectSuccess = true
                HttpResponseValidator {
                    handleResponseExceptionWithRequest { _, _ ->
                        throw error!!
                    }
                }
            }
        }
    }

    fun createSimpleMockClient(
        response: String,
        error: Throwable? = null,
        status: HttpStatusCode = HttpStatusCode.OK,
        assert: HttpRequestData.() -> Unit = {},
    ): HttpClient = createSimpleMockClient(Content(response), error, status, assert)

    fun createObjectMockClient(
        responseObjects: List<Any>? = null,
        response: (scope: MockRequestHandleScope, HttpRequestData) -> HttpResponseData,
    ): HttpClient {
        return HttpClient(MockEngine) {
            if (responseObjects != null) {
                install(KtorMockObjectResponse) {
                    addResponses(responseObjects)
                }
            }

            engine {
                addHandler {
                    response(this, it)
                }
            }
        }
    }
}
