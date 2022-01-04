/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.features.HttpResponseValidator
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.http.isSuccess

object KtorMockClientFactory {
    private fun addResponse(
        scope: MockRequestHandleScope,
        response: String,
        status: HttpStatusCode
    ): HttpResponseData {
        val headers = headersOf(
            "Content-Type" to listOf(
                ContentType.Text.Plain.toString()
            )
        )

        return if (status.isSuccess()) {
            scope.respond(
                content = response,
                status = status,
                headers = headers
            )
        } else {
            scope.respondError(status = status)
        }
    }

    fun createSimpleMockClient(
        response: String,
        error: Throwable? = null,
        status: HttpStatusCode = HttpStatusCode.OK
    ): HttpClient {
        return HttpClient(MockEngine) {
            engine {
                addHandler {
                    addResponse(this, response, status)
                }
            }

            if (!status.isSuccess()) {
                HttpResponseValidator {
                    handleResponseException {
                        throw error!!
                    }
                }
            }
        }
    }
}
