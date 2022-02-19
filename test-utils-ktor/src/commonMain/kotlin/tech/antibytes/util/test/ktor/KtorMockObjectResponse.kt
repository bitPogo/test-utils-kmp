/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.ktor

import co.touchlab.stately.collections.IsoMutableList
import co.touchlab.stately.collections.sharedMutableListOf
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpClientFeature
import io.ktor.client.statement.HttpResponseContainer
import io.ktor.client.statement.HttpResponsePipeline
import io.ktor.util.AttributeKey

class KtorMockObjectResponse(
    givenResponses: List<Any>
) {
    private val responses: IsoMutableList<Any> = sharedMutableListOf<Any>().also {
        it.addAll(givenResponses)
    }

    private fun isLast(): Boolean {
        return responses.access { it.size == 1 }
    }

    fun pop(): Any {
        return if (!isLast()) {
            responses.access { (it as MutableList<Any>).removeFirst() }
        } else {
            responses.access { it.last() }
        }
    }

    class Config {
        internal val responses: MutableList<Any> = mutableListOf()

        fun addResponse(response: Any) {
            responses.add(response)
        }

        fun addResponses(responses: List<Any>) {
            this.responses.addAll(responses)
        }
    }

    companion object Feature : HttpClientFeature<Config, KtorMockObjectResponse> {
        override val key: AttributeKey<KtorMockObjectResponse> = AttributeKey("KtorMockObjectResponse")

        override fun prepare(block: Config.() -> Unit): KtorMockObjectResponse {
            val config = Config().apply(block)

            with(config) {
                return KtorMockObjectResponse(responses)
            }
        }

        override fun install(feature: KtorMockObjectResponse, scope: HttpClient) {
            scope.responsePipeline.intercept(HttpResponsePipeline.After) { (info, _) ->
                val value = feature.pop()

                proceedWith(
                    HttpResponseContainer(
                        info,
                        value
                    )
                )
            }
        }
    }
}
