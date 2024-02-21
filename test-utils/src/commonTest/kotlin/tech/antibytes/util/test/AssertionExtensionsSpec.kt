/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package tech.antibytes.util.test

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertFailsWith
import tech.antibytes.kfixture.fixture
import tech.antibytes.kfixture.kotlinFixture

class AssertionExtensionsSpec {
    private val fixture = kotlinFixture()

    @Test
    @JsName("fn0")
    fun `Given fulfils is called it fails if the lefthand is not an instance of the righthand`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string fulfils Int::class
        }
    }

    @Test
    @JsName("fn0a")
    fun `Fulfils accepts nullable values`() {
        val string = null

        assertFailsWith<AssertionError> {
            string fulfils Int::class
        }
    }

    @Test
    @JsName("fn1")
    fun `Given fulfils is called it just runs if lefthand is a instance of the righthand`() {
        val string: String = fixture.fixture()

        string fulfils String::class
    }

    @Test
    @JsName("fn2")
    fun `Given mustBe is called it fails if the lefthand equals not righthand`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string mustBe fixture.fixture()
        }
    }

    @Test
    @JsName("fn2a")
    fun `mustBe accepts nullable values`() {
        val string = null

        assertFailsWith<AssertionError> {
            string mustBe fixture.fixture<String>()
        }
    }

    @Test
    @JsName("fn3")
    fun `Given mustBe is called it runs if the lefthand equals righthand`() {
        val string: String = fixture.fixture()

        string mustBe string
    }

    @Test
    @JsName("fn4")
    fun `Given mustBe is called it respects nullability`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string mustBe null
        }
    }

    @Test
    @JsName("fn5")
    fun `Given sameAs is called it fails if the lefthand is not the very same as righthand`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string sameAs fixture.fixture()
        }
    }

    @Test
    @JsName("fn5a")
    fun `sameAs accepts nullable values`() {
        val string = null

        assertFailsWith<AssertionError> {
            string sameAs fixture.fixture<String>()
        }
    }

    @Test
    @JsName("fn6")
    fun `Given sameAs is called it runs if the lefthand is the very same as righthand`() {
        val string: String = fixture.fixture()

        string sameAs string
    }

    @Test
    @JsName("fn7")
    fun `Given sameAs is called it runs it respects nullability`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string sameAs null
        }
    }

    @Test
    @JsName("fn8")
    fun `Given sameAs is called it fails if the lefthand is the very same as righthand`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string notSameAs string
        }
    }

    @Test
    @JsName("fn9")
    fun `Given notSameAs is called it runs if the lefthand is not the very same as righthand`() {
        val string: String = fixture.fixture()

        string notSameAs fixture.fixture()
    }

    @Test
    @JsName("fn10")
    fun `Given notSameAs is called it runs it respects nullability`() {
        val string: String = fixture.fixture()

        string notSameAs null
    }

    @Test
    @JsName("fn10a")
    fun `notSameAs accepts nullable values`() {
        val string: String = fixture.fixture()

        null notSameAs string
    }

    @Test
    @JsName("fn11")
    fun `Given isNot is called it fails if the lefthand is equal to righthand`() {
        val string: String = fixture.fixture()

        assertFailsWith<AssertionError> {
            string isNot string
        }
    }

    @Test
    @JsName("fn12")
    fun `Given isNot is called it runs if the lefthand is not equal righthand`() {
        val string: String = fixture.fixture()

        string isNot fixture.fixture()
    }

    @Test
    @JsName("fn13")
    fun `Given isNot is called it respects nullability`() {
        val string: String = fixture.fixture()

        string isNot null
    }
}
