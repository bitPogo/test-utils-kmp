/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test.santity

import tech.antibytes.util.test.coroutine.runBlockingTest
import tech.antibytes.util.test.fixture.fixture
import tech.antibytes.util.test.fixture.kotlinFixture
import tech.antibytes.util.test.fixture.listFixture
import tech.antibytes.util.test.fixture.mapFixture
import tech.antibytes.util.test.fixture.pairFixture
import tech.antibytes.util.test.fixture.qualifier.named
import tech.antibytes.util.test.mustBe
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertFailsWith

class FixtureSpec {
    private val fixture = kotlinFixture()

    @Test
    @JsName("fn0")
    fun `fixture respects Threading`() = runBlockingTest {
        fixture.fixture<Any>()
        fixture.fixture<Boolean>()
        fixture.fixture<Char>()
        fixture.fixture<Float>()
        fixture.fixture<Int>()
        fixture.fixture<Short>()
        fixture.fixture<Long>()
        fixture.fixture<String>()
        fixture.fixture<UInt>()
        fixture.fixture<UShort>()
        fixture.fixture<ULong>()
        fixture.fixture<ByteArray>()
        fixture.fixture<UByteArray>()
    }

    @Test
    @JsName("fn1")
    fun `listFixture respects Threading`() = runBlockingTest {
        fixture.listFixture<Any>(size = 5)
        fixture.listFixture<Boolean>(size = 5)
        fixture.listFixture<Char>(size = 5)
        fixture.listFixture<Float>(size = 5)
        fixture.listFixture<Int>(size = 5)
        fixture.listFixture<Short>(size = 5)
        fixture.listFixture<Long>(size = 5)
        fixture.listFixture<String>(size = 5)
        fixture.listFixture<UInt>(size = 5)
        fixture.listFixture<UShort>(size = 5)
        fixture.listFixture<ULong>(size = 5)
        fixture.listFixture<ByteArray>(size = 5)
        fixture.listFixture<UByteArray>(size = 5)
    }

    @Test
    @JsName("fn2")
    fun `pairFixture respects Threading`() = runBlockingTest {
        fixture.pairFixture<Any, Int>()
        fixture.pairFixture<Boolean, Int>()
        fixture.pairFixture<Char, Int>()
        fixture.pairFixture<Float, Int>()
        fixture.pairFixture<Int, Int>()
        fixture.pairFixture<Short, Int>()
        fixture.pairFixture<Long, Int>()
        fixture.pairFixture<String, Int>()
        fixture.pairFixture<UInt, Int>()
        fixture.pairFixture<UShort, Int>()
        fixture.pairFixture<ULong, Int>()
        fixture.pairFixture<ByteArray, Int>()
        fixture.pairFixture<UByteArray, Int>()
    }

    @Test
    @JsName("fn3")
    fun `mapFixture respects Threading`() = runBlockingTest {
        fixture.mapFixture<Any, Int>(size = 5)
        fixture.mapFixture<Boolean, Int>(size = 5)
        fixture.mapFixture<Char, Int>(size = 5)
        fixture.mapFixture<Float, Int>(size = 5)
        fixture.mapFixture<Int, Int>(size = 5)
        fixture.mapFixture<Short, Int>(size = 5)
        fixture.mapFixture<Long, Int>(size = 5)
        fixture.mapFixture<String, Int>(size = 5)
        fixture.mapFixture<UInt, Int>(size = 5)
        fixture.mapFixture<UShort, Int>(size = 5)
        fixture.mapFixture<ULong, Int>(size = 5)
        fixture.mapFixture<ByteArray, Int>(size = 5)
        fixture.mapFixture<UByteArray, Int>(size = 5)
    }

    @Test
    @JsName("fn4")
    fun `qualifier access respects Threading`() = runBlockingTest {
        val error = assertFailsWith<RuntimeException> {
            fixture.fixture<Any>(named("test"))
        }

        error.message?.startsWith("Missing Generator for ClassID (q:test") mustBe true
    }
}
