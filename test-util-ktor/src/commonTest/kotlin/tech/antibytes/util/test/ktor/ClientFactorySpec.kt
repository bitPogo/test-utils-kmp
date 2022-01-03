/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.ktor

import com.appmattus.kotlinfixture.kotlinFixture
import io.ktor.client.statement.HttpResponse
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.http.HttpStatusCode
import tech.antibytes.util.test.coroutine.runBlockingTest
import tech.antibytes.util.test.mustBe
import tech.antibytes.util.test.sameAs
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

class ClientFactorySpec {
    private val fixture = kotlinFixture()

    @Test
    fun `Given createSimpleMockClient is called with a String it returns a HttpClient which respondes with the given String`() =
        runBlockingTest {
            // Given
            val message: String = fixture()

            // When
            val client = ClientFactory.createSimpleMockClient(message)

            val response1: String = client.get(fixture<String>())
            val response2: String = client.post(fixture<String>())
            val response3: String = client.put(fixture<String>())
            val response4: String = client.delete(fixture<String>())
            val response5: String = client.patch(fixture<String>())
            val response6: String = client.head(fixture<String>())

            // Then
            response1 mustBe message
            response2 mustBe message
            response3 mustBe message
            response4 mustBe message
            response5 mustBe message
            response6 mustBe message
        }

    @Test
    fun `Given createSimpleMockClient is called with a String and a StatusCode, which is in 2xx it returns a HttpClient which respondes with the given StatusCode`() =
        runBlockingTest {
            // Given
            val status = HttpStatusCode.Created

            // When
            val client = ClientFactory.createSimpleMockClient(
                fixture(),
                status = status
            )

            val response1: HttpResponse = client.get(fixture<String>())
            val response2: HttpResponse = client.post(fixture<String>())
            val response3: HttpResponse = client.put(fixture<String>())
            val response4: HttpResponse = client.delete(fixture<String>())
            val response5: HttpResponse = client.patch(fixture<String>())
            val response6: HttpResponse = client.head(fixture<String>())

            // Then
            response1.status mustBe status
            response2.status mustBe status
            response3.status mustBe status
            response4.status mustBe status
            response5.status mustBe status
            response6.status mustBe status
        }

    @Test
    fun `Given createSimpleMockClient is called with a String, a Throwable and a StatusCode, which is not 2xx it returns a HttpClient which throws the given Exception`() =
        runBlockingTest {
            // Given
            val status = HttpStatusCode.NotFound
            val error = RuntimeException()

            // When
            val client = ClientFactory.createSimpleMockClient(
                fixture(),
                error = error,
                status = status
            )

            val exception1 = assertFailsWith<RuntimeException> {
                client.get(fixture<String>())
            }
            val exception2 = assertFailsWith<RuntimeException> {
                client.get(fixture<String>())
            }
            val exception3 = assertFailsWith<RuntimeException> {
                client.get(fixture<String>())
            }
            val exception4 = assertFailsWith<RuntimeException> {
                client.get(fixture<String>())
            }
            val exception5 = assertFailsWith<RuntimeException> {
                client.get(fixture<String>())
            }
            val exception6 = assertFailsWith<RuntimeException> {
                client.get(fixture<String>())
            }

            // Then
            exception1 sameAs error
            exception2 sameAs error
            exception3 sameAs error
            exception4 sameAs error
            exception5 sameAs error
            exception6 sameAs error
        }
}
