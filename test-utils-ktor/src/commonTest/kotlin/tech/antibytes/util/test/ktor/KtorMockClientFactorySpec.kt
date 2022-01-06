/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.ktor

import io.ktor.client.engine.mock.respond
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import tech.antibytes.util.test.coroutine.runBlockingTest
import tech.antibytes.util.test.fixture.fixture
import tech.antibytes.util.test.fixture.kotlinFixture
import tech.antibytes.util.test.ktor.KtorMockClientFactory.createObjectMockClient
import tech.antibytes.util.test.ktor.KtorMockClientFactory.createSimpleMockClient
import tech.antibytes.util.test.mustBe
import tech.antibytes.util.test.sameAs
import kotlin.test.Test
import kotlin.test.assertFailsWith

class KtorMockClientFactorySpec {
    private val fixture = kotlinFixture()

    @Test
    fun `Given createSimpleMockClient is called with a String it returns a HttpClient which respondes with the given String`() = runBlockingTest {
        // Given
        val message: String = fixture.fixture()

        // When
        val client = createSimpleMockClient(message)

        val response1: String = client.get(fixture.fixture<String>())
        val response2: String = client.post(fixture.fixture<String>())
        val response3: String = client.put(fixture.fixture<String>())
        val response4: String = client.delete(fixture.fixture<String>())
        val response5: String = client.patch(fixture.fixture<String>())
        val response6: String = client.head(fixture.fixture<String>())

        // Then
        response1 mustBe message
        response2 mustBe message
        response3 mustBe message
        response4 mustBe message
        response5 mustBe message
        response6 mustBe message
    }

    @Test
    fun `Given createSimpleMockClient is called with a String and a StatusCode, which is in 2xx it returns a HttpClient which respondes with the given StatusCode`() = runBlockingTest {
        // Given
        val status = HttpStatusCode.Created

        // When
        val client = createSimpleMockClient(
            fixture.fixture(),
            status = status
        )

        val response1: HttpResponse = client.get(fixture.fixture<String>())
        val response2: HttpResponse = client.post(fixture.fixture<String>())
        val response3: HttpResponse = client.put(fixture.fixture<String>())
        val response4: HttpResponse = client.delete(fixture.fixture<String>())
        val response5: HttpResponse = client.patch(fixture.fixture<String>())
        val response6: HttpResponse = client.head(fixture.fixture<String>())

        // Then
        response1.status mustBe status
        response2.status mustBe status
        response3.status mustBe status
        response4.status mustBe status
        response5.status mustBe status
        response6.status mustBe status
    }

    @Test
    fun `Given createSimpleMockClient is called with a String, a Throwable and a StatusCode, which is not 2xx it returns a HttpClient which throws the given Exception`() = runBlockingTest {
        // Given
        val status = HttpStatusCode.NotFound
        val error = RuntimeException(fixture.fixture<String>())

        // When
        val client = createSimpleMockClient(
            fixture.fixture(),
            error = error,
            status = status
        )

        val exception1 = assertFailsWith<RuntimeException> {
            client.get(fixture.fixture<String>())
        }
        val exception2 = assertFailsWith<RuntimeException> {
            client.get(fixture.fixture<String>())
        }
        val exception3 = assertFailsWith<RuntimeException> {
            client.get(fixture.fixture<String>())
        }
        val exception4 = assertFailsWith<RuntimeException> {
            client.get(fixture.fixture<String>())
        }
        val exception5 = assertFailsWith<RuntimeException> {
            client.get(fixture.fixture<String>())
        }
        val exception6 = assertFailsWith<RuntimeException> {
            client.get(fixture.fixture<String>())
        }

        // Then
        exception1.message!! mustBe error.message!!
        exception2.message!! mustBe error.message!!
        exception3.message!! mustBe error.message!!
        exception4.message!! mustBe error.message!!
        exception5.message!! mustBe error.message!!
        exception6.message!! mustBe error.message!!
    }

    @Test
    fun `Given createObjectMockClient is called with Closure which builds ResponseData it creates a MockClient which utilises the given ResponseData`() = runBlockingTest {
        // Given

        val request: String = fixture.fixture()
        val content: String = fixture.fixture()

        val client = createObjectMockClient { scope, _ ->
            scope.respond(
                content = content
            )
        }

        // When
        val response1: String = client.get(request)
        val response2: String = client.post(request)
        val response3: String = client.put(request)
        val response4: String = client.head(request)
        val response5: String = client.patch(request)
        val response6: String = client.delete(request)

        // Then
        response1 mustBe content
        response2 mustBe content
        response3 mustBe content
        response4 mustBe content
        response5 mustBe content
        response6 mustBe content
    }

    @Test
    fun `Given createObjectMockClient is called with Closure which builds ResponseData it delegates the RequestData to the Closure`() = runBlockingTest {
        // Given
        val url = "example.com"

        val client = createObjectMockClient { scope, request ->
            // Then
            request.url.fullPath.trim('/') mustBe url

            scope.respond(
                content = fixture.fixture<String>()
            )
        }

        // When
        client.post<String>(url)
        client.delete<String>(url)
    }

    @Test
    fun `Given createMockClientWithResponse is called with List of HttpResponseObjects and a Closure it creates a MockClient which utilises the given HttpResponseObjects`() = runBlockingTest {
        // Given
        val referenceObject = emptyList<Any>()
        val objects = listOf(
            referenceObject
        )
        val client = createObjectMockClient(objects) { scope, _ ->
            scope.respond(
                content = fixture.fixture<String>()
            )
        }

        // When
        val response1: List<Any> = client.get(fixture.fixture<String>())
        val response2: List<Any> = client.post(fixture.fixture<String>())
        val response3: List<Any> = client.put(fixture.fixture<String>())
        val response4: List<Any> = client.head(fixture.fixture<String>())
        val response5: List<Any> = client.patch(fixture.fixture<String>())
        val response6: List<Any> = client.delete(fixture.fixture<String>())

        // Then
        response1 sameAs referenceObject
        response2 sameAs referenceObject
        response3 sameAs referenceObject
        response4 sameAs referenceObject
        response5 sameAs referenceObject
        response6 sameAs referenceObject
    }
}
